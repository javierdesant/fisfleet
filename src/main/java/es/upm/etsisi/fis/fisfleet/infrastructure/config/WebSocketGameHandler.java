package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.core.LastHitCache;
import es.upm.etsisi.fis.fisfleet.infrastructure.core.MoveRequestWaiter;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
import es.upm.etsisi.fis.model.Nave;
import es.upm.etsisi.fis.model.Partida;
import es.upm.etsisi.fis.model.Patrullero;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketGameHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GameCacheService gameCacheService;
    private final GameService gameService;

    private Set<WebSocketSession> sessions;

    @PostConstruct
    public void init() {
        this.sessions = new CopyOnWriteArraySet<>();
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        Long playerId = extractPlayerId(session);
        disconnectPreviousSessionIfExists(playerId);
        registerSession(playerId, session);
        log.info("New session established: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        Long playerId = extractPlayerId(session);
        UUID gameId = extractGameId(session);
        Partida partida = gameService.getPartidaOrThrow(gameId);

        if (!gameService.isPlayerTurn(partida, playerId)) {
            this.sendText(session, "No es tu turno");
            return;
        }

        processPlayerTurn(partida, playerId, gameId, session);
    }

    private void processPlayerTurn(Partida partida, Long playerId, UUID gameId, WebSocketSession session) {
        HashMap<String, Object> moveResult = gameService.applyTurn(partida);
        handleLastHitAndAbility(partida, playerId, moveResult);

        gameService.handlePartidaState(partida, gameId);

        String gameType = String.valueOf(session.getAttributes().get("gameType"));
        switch (gameType) {
            case "pve" -> {
                partida.aplicaTurno();
                gameService.handlePartidaState(partida, gameId);
                gameService.sendPartidaView(partida, Long.valueOf(partida.getTurnoName()), moveResult, sessions);
            }
            case "pvp" -> gameService.sendPartidaView(partida, playerId, moveResult, sessions);
            default -> throw new IllegalStateException("Invalid game type: " + gameType);
        }
    }

    private void handleLastHitAndAbility(Partida partida, Long playerId, HashMap<String, Object> moveResult) {
        Nave nave = (Nave) moveResult.get("Nave");
        if (nave != null) {
            LastHitCache.save(playerId, nave);
        }

        Nave lastHit = LastHitCache.get(playerId);
        if (lastHit != null) {
            if ("patrullero".equalsIgnoreCase(lastHit.getName())) {
                gameService.sendRevealedRow(playerId, partida, sessions);
            }
            lastHit.accionComplementaria(partida);
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        sessions.remove(session);
        Long playerId = extractPlayerId(session);
        gameCacheService.removePlayerSession(playerId);
        log.info("Disconnected session {} for player {}", session.getId(), playerId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, @NonNull Throwable exception) {
        log.error("Transport error in session {}: {}", session.getId(), exception.getMessage(), exception);
        try {
            if (session.isOpen()) {
                session.close(CloseStatus.SERVER_ERROR);
            }
        } catch (IOException e) {
            log.error("Failed to close session {}: {}", session.getId(), e.getMessage(), e);
        } finally {
            sessions.remove(session);
        }
        sendErrorMessage(session, exception.getMessage());
    }

    private void sendText(WebSocketSession session, String text) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(text));
            }
        } catch (IOException e) {
            log.error("Failed to send message to session {}: {}", session.getId(), e.getMessage(), e);
        }
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        this.sendText(session, "{\"error\": \"" + errorMessage + "\"}");
    }

    private UUID extractGameId(WebSocketSession session) {
        return UUID.fromString(String.valueOf(session.getAttributes().get("gameId")));
    }

    private Long extractPlayerId(WebSocketSession session) {
        return getPlayerIdFromPrincipal(session.getPrincipal());
    }

    private static Long getPlayerIdFromPrincipal(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            return Long.parseLong(principal.getName());
        }
        log.error("Invalid principal: {}", principal);
        throw new IllegalStateException("Invalid principal");
    }

    private void disconnectPreviousSessionIfExists(Long playerId) {
        gameCacheService.getPlayerSession(playerId).ifPresent(sessionId -> {
            findSessionById(sessionId).ifPresent(this::closeAndRemoveSession);
            gameCacheService.removePlayerSession(playerId);
        });
    }

    private void registerSession(Long playerId, WebSocketSession session) {
        sessions.add(session);
        gameCacheService.savePlayerSession(playerId, session.getId());
    }

    private Optional<WebSocketSession> findSessionById(String sessionId) {
        return sessions.stream().filter(s -> s.getId().equals(sessionId)).findFirst();
    }

    private void closeAndRemoveSession(WebSocketSession session) {
        try {
            String msg = "You have been disconnected because you logged in from another device";
            session.sendMessage(new TextMessage(msg));
            session.close(CloseStatus.NORMAL.withReason(msg));
            log.warn("Closed previous session: {}", session.getId());
        } catch (IOException e) {
            log.error("Failed to close previous session: {}", session.getId(), e);
        } finally {
            sessions.remove(session);
        }
    }
}
