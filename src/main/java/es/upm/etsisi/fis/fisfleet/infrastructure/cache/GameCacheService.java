package es.upm.etsisi.fis.fisfleet.infrastructure.cache;

import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.domain.GameSession;
import es.upm.etsisi.fis.model.Partida;

import java.util.List;
import java.util.Optional;

public interface GameCacheService {
    void savePartida(Long gameId, Partida partida);
    Optional<Partida> getPartida(Long gameId);
    void removePartida(Long gameId);

    void addPlayerGame(Long playerId, Long gameId);
    List<Long> getPlayerGames(Long playerId);
    void removePlayerGame(Long playerId, Long gameId);

    void savePlayerSession(Long playerId, String sessionId);
    Optional<String> getPlayerSession(Long playerId);
    void removePlayerSession(Long playerId);
}
