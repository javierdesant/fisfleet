package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.SpecialAbility;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameResultService;
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

    private static Long getPlayerId(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            return Long.parseLong(principal.getName());
        } else {
            log.error("Invalid principal: {}", principal);
            throw new IllegalStateException("Invalid principal");
        }
    }

    @PostConstruct
    public void init() {
        this.sessions = new CopyOnWriteArraySet<>();
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession newSession) {
        Long playerId = getPlayerId(newSession.getPrincipal());

        this.disconnectExistingSessionIfPresent(playerId);

        this.registerNewSession(playerId, newSession);
        log.info("New session established: {}", newSession.getId());
    }

    private void disconnectExistingSessionIfPresent(Long playerId) {
        gameCacheService.getPlayerSession(playerId).ifPresent(existingSessionId -> {
            this.findOpenSession(existingSessionId).ifPresent(this::disconnectSession);
            gameCacheService.removePlayerSession(playerId);
        });
    }

    private Optional<WebSocketSession> findOpenSession(String sessionId) {
        return sessions.stream()
                .filter(s -> s.getId().equals(sessionId) && s.isOpen())
                .findFirst();
    }

    private void disconnectSession(WebSocketSession session) {
        try {
            String DISCONNECT_MESSAGE = "You have been disconnected because you logged in from another device";

            session.sendMessage(new TextMessage(DISCONNECT_MESSAGE));
            session.close(CloseStatus.NORMAL.withReason(DISCONNECT_MESSAGE));
            sessions.remove(session);
            log.warn("Previous session closed: {}", session.getId());
        } catch (IOException e) {
            log.error("Error closing previous session: {}", session.getId(), e);
        }
    }

    private void registerNewSession(Long playerId, WebSocketSession newSession) {
        sessions.add(newSession);
        gameCacheService.savePlayerSession(playerId, newSession.getId());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {

        // FIXME: integrate with HumanPlayer and MoveRequestWaiter classes

        MoveRequest request = objectMapper.readValue(message.getPayload(), MoveRequest.class);
        Long playerId = getPlayerId(session.getPrincipal());

        Partida partida = gameCacheService.getPartida(playerId)
                .orElseThrow(() -> new IllegalStateException("Game not found"));

        if (!partida.getTurnoName().equals(playerId.toString())) {
            session.sendMessage(new TextMessage("No es tu turno"));
            return;
        }

        // Procesar movimiento principal
        HashMap<String, Object> salidaHash = partida.aplicaTurno();

//        TODO: Procesar habilidad especial para jugador humano
//         this.procesarHabilidadEspecial(partida, request, salidaHash);

        // Gestionar fin de partida y turnos
        this.gestionarEstadoPartida(partida, playerId);


        if (session.getAttributes().get("gameType").equals("pve")) {
//            this.procesarHabilidadEspecial(partida, request, salidaHash);
            partida.aplicaTurno();
            this.gestionarEstadoPartida(partida, playerId);
            this.enviarVista(partida, Long.valueOf(partida.getTurnoName()), salidaHash);
        } else if (session.getAttributes().get("gameType").equals("pvp")) {
            this.enviarVista(partida, playerId, salidaHash);
        } else {
            throw new IllegalStateException("Invalid game type");
        }

    }

//    private void procesarHabilidadEspecial(Partida partida, MoveRequest request, HashMap<String, Object> salidaHash) {
//        if (request.isSpecialAbility()) {
//            Nave ultimaTocada = (Nave) salidaHash.get("Nave");
//            if (ultimaTocada != null) {
//                ultimaTocada.accionComplementaria(partida);
//                salidaHash.put("HabilidadEspecial", true);
//            }
//        }
//    }

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

    private void enviarVista(Partida partida, Long lastPlayerId, HashMap<String, Object> salidaHash) {
        Long opponentId = Long.valueOf(partida.getTurnoName());
        assert !opponentId.equals(lastPlayerId);

        Nave ultimaTocada = (Nave) salidaHash.get("Nave");
        SpecialAbility availableAbility = SpecialAbility.fromShipName(ultimaTocada.getName());
        char[][] board = partida.getTableros().get(0);
        char[][] boardMasked = partida.getTableros().get(1);

        GameViewDTO opponentView = GameViewDTO.builder()
                        .availableAbility(availableAbility)
                        .ownBoard(board)
                        .enemyBoardMasked(boardMasked)
                        .build();

        this.sendView(opponentId, opponentView);
    }

    private void sendView(Long userId, GameViewDTO gameView) {
        gameCacheService.getPlayerSession(userId).flatMap(this::findSessionById).ifPresent(session -> {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(gameView)));
            } catch (IOException ex) {
                log.error("Failed to send view to player {}", userId, ex);
            }
        });
    }

    private Optional<WebSocketSession> findSessionById(String sessionId) {
        return sessions.stream().filter(session -> session.getId().equals(sessionId)).findFirst();
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
        Long playerId = getPlayerId(session.getPrincipal());
        gameCacheService.removePlayerSession(playerId);
        log.info("Disconnected session {} for player {}", session.getId(), playerId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, @NonNull Throwable exception) {
        log.error("Error in the connection {}: {}", session.getId(), exception.getMessage(), exception);

        if (session.isOpen()) {
            try {
                session.close(CloseStatus.SERVER_ERROR);
                sessions.remove(session);
            } catch (IOException ex) {
                log.error("Failed to close session {} after transport error: {}", session.getId(), ex.getMessage(), ex);
            }
        }

        this.sendErrorMessage(session, exception.getMessage());
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage("{\"error\": \"" + errorMessage + "\"}"));
            }
        } catch (Exception ex) {
            log.error("Error sending error message: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error sending error message", ex);
        }
    }
}
