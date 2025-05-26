package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.SpecialAbility;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.impl.GameResultService;
import es.upm.etsisi.fis.model.Nave;
import es.upm.etsisi.fis.model.Partida;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GameCacheService gameCacheService;
    private final GameResultService gameResultService;

    private Set<WebSocketSession> sessions;

    @PostConstruct
    public void init() {
        this.sessions = new CopyOnWriteArraySet<>();
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        Long playerId = this.extractPlayerId(session);
        this.disconnectIfExists(playerId);
        this.registerSession(playerId, session);
        log.info("New session established: {}", session.getId());
    }

    private Long extractPlayerId(WebSocketSession session) {
        return getPlayerId(session.getPrincipal());
    }

    private static Long getPlayerId(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            return Long.parseLong(principal.getName());
        }
        log.error("Invalid principal: {}", principal);
        throw new IllegalStateException("Invalid principal");
    }

    private void disconnectIfExists(Long playerId) {
        gameCacheService.getPlayerSession(playerId).ifPresent(sessionId -> {
            this.findSessionById(sessionId).ifPresent(this::disconnectSession);
            gameCacheService.removePlayerSession(playerId);
        });
    }

    private void disconnectSession(WebSocketSession session) {
        try {
            String msg = "You have been disconnected because you logged in from another device";
            session.sendMessage(new TextMessage(msg));
            session.close(CloseStatus.NORMAL.withReason(msg));
            log.warn("Previous session closed: {}", session.getId());
        } catch (IOException e) {
            log.error("Error closing previous session: {}", session.getId(), e);
        } finally {
            sessions.remove(session);
            log.warn("Previous session removed: {}", session.getId());
        }
    }

    private void registerSession(Long playerId, WebSocketSession session) {
        sessions.add(session);
        gameCacheService.savePlayerSession(playerId, session.getId());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {

        // FIXME: integrate with HumanPlayer and MoveRequestWaiter classes

        MoveRequest request = objectMapper.readValue(message.getPayload(), MoveRequest.class);
        Long playerId = this.extractPlayerId(session);
        Partida partida = this.getPartidaOrThrow(playerId);

        if (!partida.getTurnoName().equals(playerId.toString())) {
            session.sendMessage(new TextMessage("No es tu turno"));
            return;
        }

        HashMap<String, Object> salida = partida.aplicaTurno();

//        TODO: Procesar habilidad especial para jugador humano
//         this.procesarHabilidadEspecial(partida, request, salidaHash);

        gestionarEstadoPartida(partida, playerId);

        String gameType = String.valueOf(session.getAttributes().get("gameType"));
        switch (gameType) {
            case "pve" -> {
                partida.aplicaTurno();
                gestionarEstadoPartida(partida, playerId);
                enviarVista(partida, Long.valueOf(partida.getTurnoName()), salida);
            }
            case "pvp" -> enviarVista(partida, playerId, salida);
            default -> throw new IllegalStateException("Invalid game type");
        }
    }

    private Partida getPartidaOrThrow(Long playerId) {
        return gameCacheService.getPartida(playerId)
                .orElseThrow(() -> new IllegalStateException("Game not found"));
    }

    private void gestionarEstadoPartida(Partida partida, Long playerId) {
        if (partida.fin()) {
            partida.setfin();
            gameResultService.persistFinished(partida);
            gameCacheService.removePartida(playerId);
        } else {
            partida.swapTurn();
            gameCacheService.savePartida(playerId, partida);
        }
    }

    private void enviarVista(Partida partida, Long lastPlayerId, HashMap<String, Object> salida) {
        Long opponentId = Long.valueOf(partida.getTurnoName());
        if (opponentId.equals(lastPlayerId)) return;

        Nave nave = (Nave) salida.get("Nave");
        GameViewDTO view = GameViewDTO.builder()
                .availableAbility(SpecialAbility.fromShipName(nave.getName()))
                .ownBoard(partida.getTableros().get(0))
                .enemyBoardMasked(partida.getTableros().get(1))
                .build();

        this.sendView(opponentId, view);
    }

    private void sendView(Long userId, GameViewDTO view) {
        gameCacheService.getPlayerSession(userId)
                .flatMap(this::findSessionById)
                .ifPresent(session -> sendMessage(session, view));
    }

    private void sendMessage(WebSocketSession session, GameViewDTO view) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(view)));
        } catch (IOException ex) {
            log.error("Failed to send view to player {}", session.getId(), ex);
        }
    }

    private Optional<WebSocketSession> findSessionById(String sessionId) {
        return sessions.stream()
                .filter(s -> s.getId().equals(sessionId))
                .findFirst();
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
        Long playerId = this.extractPlayerId(session);
        gameCacheService.removePlayerSession(playerId);
        log.info("Disconnected session {} for player {}", session.getId(), playerId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, @NonNull Throwable exception) {
        log.error("Error in session {}: {}", session.getId(), exception.getMessage(), exception);
        try {
            if (session.isOpen()) {
                session.close(CloseStatus.SERVER_ERROR);
            }
        } catch (IOException e) {
            log.error("Error closing session {}: {}", session.getId(), e.getMessage(), e);
        } finally {
            sessions.remove(session);
        }
        this.sendErrorMessage(session, exception.getMessage());
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage("{\"error\": \"" + errorMessage + "\"}"));
            }
        } catch (IOException e) {
            log.error("Error sending error message: {}", e.getMessage(), e);
            throw new RuntimeException("Error sending error message", e);
        }
    }
}
