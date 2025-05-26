package es.upm.etsisi.fis.fisfleet.infrastructure.adapters;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MoveRequestRegistry {

    private static final Map<String, CompletableFuture<MoveRequest>> pendingMoves = new ConcurrentHashMap<>();

    public CompletableFuture<MoveRequest> waitForMove(Long gameId, Long playerId) {
        String key = this.buildKey(gameId, playerId);
        CompletableFuture<MoveRequest> future = new CompletableFuture<>();
        pendingMoves.put(key, future);
        return future;
    }

    public void completeMove(Long gameId, Long playerId, MoveRequest moveRequest) {
        String key = this.buildKey(gameId, playerId);
        CompletableFuture<MoveRequest> future = pendingMoves.remove(key);
        if (future != null) {
            future.complete(moveRequest);
        }
    }

    private String buildKey(Long gameId, Long playerId) {
        return gameId + ":" + playerId;
    }
}
