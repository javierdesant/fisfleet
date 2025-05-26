package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.model.TDificultad;

public interface MatchmakingService {

    String createPveMatch(Long playerId, TDificultad difficulty);

    String queuePvpMatch(Long playerId);
}
