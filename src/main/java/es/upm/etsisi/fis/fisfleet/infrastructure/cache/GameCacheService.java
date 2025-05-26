package es.upm.etsisi.fis.fisfleet.infrastructure.cache;

import es.upm.etsisi.fis.model.Partida;

import java.util.Optional;
import java.util.UUID;

public interface GameCacheService {
    void savePartida(UUID gameId, Partida partida);
    Optional<Partida> getPartida(UUID gameId);
    void removePartida(UUID gameId);

    void savePlayerSession(Long playerId, String sessionId);
    Optional<String> getPlayerSession(Long playerId);
    void removePlayerSession(Long playerId);
}
