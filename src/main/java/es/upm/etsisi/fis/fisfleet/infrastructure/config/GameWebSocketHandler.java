package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.AuthenticationService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RequiredArgsConstructor
@Slf4j
@Component
public class GameWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final GameService gameService;
    private final GameCacheService gameCacheService;
    private final AuthenticationService authenticationService;

    // Simple cache for quick access
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
        String sessionId = session.getId();

        Long playerId = this.getLoggedInUserId();
        if (playerId == null) {
            throw new SecurityException("User is not logged in for session " + sessionId);
        }

        gameCacheService.savePlayerSession(playerId, sessionId);
        log.info("New connection: {} for player: {}", sessionId, playerId);
    }

    private Long getLoggedInUserId() {
        UserEntity loggedInUser = authenticationService.findLoggedInUser();
        return loggedInUser.getId();
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
    public void handleTransportError(WebSocketSession session, @NonNull Throwable exception) throws Exception {
        log.error("Error in the connection {}: {}", session.getId(), exception.getMessage(), exception);

        if (session.isOpen()) {
            try {
                session.close(CloseStatus.SERVER_ERROR);
            } catch (IOException ex) {
                log.error("Failed to close session {} after transport error: {}", session.getId(), ex.getMessage(), ex);
            }
        }

        this.sendErrorMessage(session, exception.getMessage());
    }
}
