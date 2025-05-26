package es.upm.etsisi.fis.fisfleet.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import es.upm.etsisi.fis.model.Partida;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameCacheServiceImpl implements GameCacheService {
    private final Cache<Long, Partida> partidaCache;
    private final Cache<Long, String> playerSessionCache;

    @Override
    public void savePartida(Long gameId, Partida partida) {
        if (gameId == null || partida == null) {
            log.warn("Cannot cache null partida or null ID");
            return;
        }
        partidaCache.put(gameId, partida);
        log.debug("Cached Partida ID: {}", gameId);
    }

    @Override
    public Optional<Partida> getPartida(Long gameId) {
        if (gameId == null) return Optional.empty();
        return Optional.ofNullable(partidaCache.getIfPresent(gameId));
    }

    @Override
    public void removePartida(Long gameId) {
        if (gameId == null) return;
        partidaCache.invalidate(gameId);
        log.debug("Removed Partida ID: {}", gameId);
    }

    @Override
    public void savePlayerSession(Long playerId, String sessionId) {
        if (playerId == null || sessionId == null || sessionId.isEmpty()) return;
        playerSessionCache.put(playerId, sessionId);
        log.debug("Stored session {} for player {}", sessionId, playerId);
    }

    @Override
    public Optional<String> getPlayerSession(Long playerId) {
        if (playerId == null) return Optional.empty();
        return Optional.ofNullable(playerSessionCache.getIfPresent(playerId));
    }

    @Override
    public void removePlayerSession(Long playerId) {
        if (playerId == null) return;
        playerSessionCache.invalidate(playerId);
        log.debug("Removed session for player {}", playerId);
    }
}