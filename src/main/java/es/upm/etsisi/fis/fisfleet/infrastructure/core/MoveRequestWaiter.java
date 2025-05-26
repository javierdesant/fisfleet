package es.upm.etsisi.fis.fisfleet.infrastructure.core;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MoveRequestWaiter {
    private static final Map<Long, CompletableFuture<MoveRequest>> pendingMoves = new ConcurrentHashMap<>();

    public static CompletableFuture<MoveRequest> waitForMove(Long userId) {
        cancelMove(userId);
        CompletableFuture<MoveRequest> future = new CompletableFuture<>();
        pendingMoves.put(userId, future);
        return future;
    }

    public static void submitMove(Long userId, MoveRequest move) {
        CompletableFuture<MoveRequest> future = pendingMoves.remove(userId);
        if (future != null) {
            future.complete(move);
        }
    }

    public static void cancelMove(Long userId) {
        CompletableFuture<MoveRequest> future = pendingMoves.remove(userId);
        if (future != null) {
            future.cancel(true);
        }
    }
}
