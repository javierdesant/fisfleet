package es.upm.etsisi.fis.fisfleet.infrastructure.cache;

import es.upm.etsisi.fis.fisfleet.api.dto.GameSession;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;

import java.util.List;
import java.util.Optional;

public interface GameCacheService {

    void saveGameState(GameSession gameState);

    Optional<GameSession> getGameState(Long gameId);

    void removeGameState(Long gameId);

    void addPlayerGame(Long playerId, Long gameId);

    List<Long> getPlayerGames(Long playerId);

    void removePlayerGame(Long playerId, Long gameId);

    void savePlayerView(Long gameId, Long playerId, GameViewDTO gameView);

    Optional<GameViewDTO> getPlayerView(Long gameId, Long playerId);

    void removePlayerView(Long gameId, Long playerId);

    void savePlayerSession(Long playerId, String sessionId);

    Optional<String> getPlayerSession(Long playerId);

    void removePlayerSession(Long playerId);
}