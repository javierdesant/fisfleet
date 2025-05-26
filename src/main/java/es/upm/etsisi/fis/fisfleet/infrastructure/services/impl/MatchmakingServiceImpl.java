package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.MachineEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.MoveEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.AuthenticationService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.MatchmakingService;
import es.upm.etsisi.fis.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchmakingServiceImpl implements MatchmakingService {

    private final GameCacheService gameCacheService;
    private final AuthenticationService authenticationService;

    @Override
    public String createPveMatch(Long playerId, TDificultad difficulty) {
        IJugador player1 = authenticationService.findLoggedInUser();
        assert player1.getNombre().equals(playerId.toString());
        IJugador ai = new MachineEntity();
        IMovimiento movimientoIn = new MoveEntity();

        Partida partida = new Partida(player1, ai, movimientoIn);

        UUID gameId = UUID.randomUUID();    // temporal id just for cache queries
        gameCacheService.savePartida(gameId, partida);
        return gameId.toString();
    }

    @Override
    public String queuePvpMatch(Long playerId) {
        // TODO: Implement PvP matchmaking queue
        throw new UnsupportedOperationException("PvP matchmaking not implemented yet");
    }

    private String generateUniqueGameId() {
        return UUID.randomUUID().toString();
    }
}