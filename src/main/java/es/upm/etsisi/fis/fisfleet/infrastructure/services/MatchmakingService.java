package es.upm.etsisi.fis.fisfleet.infrastructure.services;

public interface MatchmakingService {

    String createPveMatch(Long playerId);

    String queuePvpMatch(Long playerId);
}
