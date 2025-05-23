package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.domain.GameSession;
import es.upm.etsisi.fis.model.TDificultad;

public interface GameService {  // TODO: fix GameViewDTO vs GameStateDTO

    GameViewDTO createPvEMatch(TDificultad difficulty);

    GameSession getGameById(Long gameId);

    GameViewDTO getGameViewForPlayer(Long gameId, Long playerId);

    GameSession performAttack(MoveRequest request);

    GameSession performCounterAttack(MoveRequest request);

    GameSession launchArtilleryAttack(MoveRequest request);


    // TODO: turnOfPlayer vs playerId?
    GameSession repairSubmarine(Long gameId, Long playerId);

    GameSession revealRow(Long gameId, Long playerId, int y);

    boolean canPlayerPerformAction(Long gameId, Long playerId);

    boolean isGameOver(Long gameId);

    boolean validateMove(MoveRequest request);
}

//    GameStateDTO createPvPMatch();
//    GameStateDTO getGameById(Long gameId);
