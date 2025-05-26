package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
import es.upm.etsisi.fis.model.Partida;
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
        Long playerId = this.extractPlayerId(session);
        disconnectIfExists(playerId);
        registerSession(playerId, session);
        log.info("New session established: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        Long playerId = this.extractPlayerId(session);
        UUID gameId = this.extractGameId(session);
        Partida partida = gameService.getPartidaOrThrow(gameId);

        if (!gameService.isPlayerTurn(partida, playerId)) {
            session.sendMessage(new TextMessage("No es tu turno"));
            return;
        }

        HashMap<String, Object> moveResult = gameService.applyTurn(partida);
        gameService.handlePartidaState(partida, gameId);

        String gameType = String.valueOf(session.getAttributes().get("gameType"));
        switch (gameType) {
            case "pve" -> {
                partida.aplicaTurno();
                gameService.handlePartidaState(partida, gameId);
                gameService.sendPartidaView(partida, Long.valueOf(partida.getTurnoName()), moveResult, sessions);
            }
            case "pvp" -> gameService.sendPartidaView(partida, playerId, moveResult, sessions);
            default -> throw new IllegalStateException("Invalid game type");
        }
    }

    private UUID extractGameId(WebSocketSession session) {
        return UUID.fromString(String.valueOf(session.getAttributes().get("gameId")));
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

    private void disconnectIfExists(Long playerId) {
        gameCacheService.getPlayerSession(playerId).ifPresent(sessionId -> {
            findSessionById(sessionId).ifPresent(this::closeAndRemoveSession);
            gameCacheService.removePlayerSession(playerId);
        });
    }

    private void closeAndRemoveSession(WebSocketSession session) {
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

    private Optional<WebSocketSession> findSessionById(String sessionId) {
        return sessions.stream()
                .filter(s -> s.getId().equals(sessionId))
                .findFirst();
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
