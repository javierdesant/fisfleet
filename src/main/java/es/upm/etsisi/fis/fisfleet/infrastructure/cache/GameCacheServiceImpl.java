package es.upm.etsisi.fis.fisfleet.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import es.upm.etsisi.fis.fisfleet.api.dto.GameSession;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
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

    private final Cache<Long, GameSession> gameStateCache;
    private final Cache<Long, List<Long>> playerGamesCache;
    private final Cache<String, GameViewDTO> playerViewsCache;
    private final Cache<Long, String> activeSessionsCache;

    @Override
    public void saveGameState(GameSession gameState) {
        if (gameState == null || gameState.getGameId() == null) {
            log.warn("Attempted to cache null game state or game state with null ID");
            return;
        }
        gameStateCache.put(gameState.getGameId(), gameState);
        log.debug("Cached game state for game ID: {}", gameState.getGameId());
    }

    @Override
    public Optional<GameSession> getGameState(Long gameId) {
        if (gameId == null) {
            log.warn("Attempted to retrieve game state with null ID");
            return Optional.empty();
        }
        return Optional.ofNullable(gameStateCache.getIfPresent(gameId));
    }

    @Override
    public void removeGameState(Long gameId) {
        if (gameId == null) {
            log.warn("Attempted to remove game state with null ID");
            return;
        }
        gameStateCache.invalidate(gameId);
        log.debug("Removed game state for game ID: {}", gameId);
    }

    @Override
    public void addPlayerGame(Long playerId, Long gameId) {
        if (playerId == null || gameId == null) {
            log.warn("Attempted to associate player with game using null IDs");
            return;
        }
        
        List<Long> games = playerGamesCache.getIfPresent(playerId);
        if (games == null) {
            games = new ArrayList<>();
        }
        
        if (!games.contains(gameId)) {
            games.add(gameId);
            playerGamesCache.put(playerId, games);
            log.debug("Associated player ID: {} with game ID: {}", playerId, gameId);
        }
    }

    @Override
    public List<Long> getPlayerGames(Long playerId) {
        if (playerId == null) {
            log.warn("Attempted to retrieve games for player with null ID");
            return new ArrayList<>();
        }
        
        List<Long> games = playerGamesCache.getIfPresent(playerId);
        return games != null ? games : new ArrayList<>();
    }

    @Override
    public void removePlayerGame(Long playerId, Long gameId) {
        if (playerId == null || gameId == null) {
            log.warn("Attempted to remove player-game association with null IDs");
            return;
        }
        
        List<Long> games = playerGamesCache.getIfPresent(playerId);
        if (games != null) {
            games.remove(gameId);
            if (games.isEmpty()) {
                playerGamesCache.invalidate(playerId);
            } else {
                playerGamesCache.put(playerId, games);
            }
            log.debug("Removed association between player ID: {} and game ID: {}", playerId, gameId);
        }
    }

    @Override
    public void savePlayerView(Long gameId, Long playerId, GameViewDTO gameView) {
        if (gameId == null || playerId == null || gameView == null) {
            log.warn("Attempted to cache player view with null parameters");
            return;
        }
        
        String key = createPlayerViewKey(gameId, playerId);
        playerViewsCache.put(key, gameView);
        log.debug("Cached view for player ID: {} in game ID: {}", playerId, gameId);
    }

    @Override
    public Optional<GameViewDTO> getPlayerView(Long gameId, Long playerId) {
        if (gameId == null || playerId == null) {
            log.warn("Attempted to retrieve player view with null parameters");
            return Optional.empty();
        }
        
        String key = createPlayerViewKey(gameId, playerId);
        return Optional.ofNullable(playerViewsCache.getIfPresent(key));
    }

    @Override
    public void removePlayerView(Long gameId, Long playerId) {
        if (gameId == null || playerId == null) {
            log.warn("Attempted to remove player view with null parameters");
            return;
        }
        
        String key = createPlayerViewKey(gameId, playerId);
        playerViewsCache.invalidate(key);
        log.debug("Removed view for player ID: {} in game ID: {}", playerId, gameId);
    }

    @Override
    public void savePlayerSession(Long playerId, String sessionId) {
        if (playerId == null || sessionId == null || sessionId.isEmpty()) {
            log.warn("Attempted to cache player session with null or empty parameters");
            return;
        }
        
        activeSessionsCache.put(playerId, sessionId);
        log.debug("Cached session ID: {} for player ID: {}", sessionId, playerId);
    }

    @Override
    public Optional<String> getPlayerSession(Long playerId) {
        if (playerId == null) {
            log.warn("Attempted to retrieve session for player with null ID");
            return Optional.empty();
        }
        
        return Optional.ofNullable(activeSessionsCache.getIfPresent(playerId));
    }

    @Override
    public void removePlayerSession(Long playerId) {
        if (playerId == null) {
            log.warn("Attempted to remove session for player with null ID");
            return;
        }
        
        activeSessionsCache.invalidate(playerId);
        log.debug("Removed session for player ID: {}", playerId);
    }

    private String createPlayerViewKey(Long gameId, Long playerId) {
        return gameId + ":" + playerId;
    }
}