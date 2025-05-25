package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.AuthenticationService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameResultService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
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
    private final AuthenticationService authenticationService;
    private final GameCacheService gameCacheService;
    private final GameService gameService;
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
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        try {
            MoveRequest request = objectMapper.readValue(message.getPayload(), MoveRequest.class);

            UserDetails loggedInUser = authenticationService.findLoggedInUser();
            assert loggedInUser.getUsername() != null;

            if (loggedInUser.equals(session.getPrincipal())) {
                this.processMoveRequest(session, request);
            } else {
                throw new SecurityException("User is not in the game or session does not belong to the logged-in user.");
            }

        } catch (Exception e) {
            log.error("Error processing message", e);
            this.sendErrorMessage(session, "Error processing your request: " + e.getMessage());
        }
    }


    private void processMoveRequest(WebSocketSession session, MoveRequest request) {
        if (!gameService.validateMove(request)) {
            this.sendErrorMessage(session, "Invalid move. Please check the game rules and try again.");
        }

//       TODO!: implement a Partida.class cache in GameCacheService
//        GameStateDTO updatedState = switch (request.getSpecialAbility()) {
//            case COUNTER_ATTACK -> gameService.performCounterAttack(request);
//            case ARTILLERY_ATTACK -> gameService.launchArtilleryAttack(request);
//            case REPAIR -> gameService.repairSubmarine(request.getGameId(), request.getPlayerId());
//            case REVEAL_ROW ->
//                    gameService.revealRow(request.getGameId(), request.getPlayerId(), request.getCoordinateY());
//            case NONE -> gameService.performAttack(request);
//        };
//        this.sendGameViewToPlayer(updatedState.getPlayer1Id(), gameId);
//        this.sendGameViewToPlayer(updatedState.getPlayer2Id(), gameId);
//        log.debug("Processed move for game: {} by player: {}", gameId, playerId);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        sessions.remove(session);

        Long playerId = this.getLoggedInUserId();

        if (playerId != null) {
            gameCacheService.removePlayerSession(playerId);
            log.info("Closed connection: {} for player: {}", session.getId(), playerId);
        } else {
            log.info("Closed connection: {} (player ID not available)", session.getId());
        }
    }

    private void sendGameViewToPlayer(Long playerId, Long gameId) {
        String sessionId = gameCacheService.getPlayerSession(playerId)
                .orElseThrow(() -> {
                    log.warn("No session found for player: {}", playerId);
                    return new IllegalStateException("No session found for player: " + playerId);
                });

        Optional<WebSocketSession> optionalSession = this.findSessionById(sessionId);

        optionalSession.ifPresent(session -> {
            try {
                GameViewDTO gameView = gameService.getGameViewForPlayer(gameId, playerId);

                String jsonView = objectMapper.writeValueAsString(gameView);
                session.sendMessage(new TextMessage(jsonView));

                log.debug("Sent game view to player: {}", playerId);
            } catch (IOException ex) {
                log.error("Error sending game view to player: {}. SessionId: {}", playerId, sessionId, ex);
            }
        });
    }

    private Optional<WebSocketSession> findSessionById(String sessionId) {
        return sessions.stream()
                .filter(session -> session.getId().equals(sessionId))
                .findFirst();
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage("{\"error\": \"" + errorMessage + "\"}"));
            }
        } catch (Exception ex) {
            log.error("Error sending error message: {}", ex.getMessage() , ex);
            throw new RuntimeException("Error sending error message", ex);
        }
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
