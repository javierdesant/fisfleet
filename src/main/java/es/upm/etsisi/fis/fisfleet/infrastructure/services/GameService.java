package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.GameStateDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.model.TDificultad;

public interface GameService {  // TODO: fix GameViewDTO vs GameStateDTO

    GameViewDTO createPvEMatch(TDificultad difficulty);

    GameStateDTO getGameById(Long gameId);

    GameViewDTO getGameViewForPlayer(Long gameId, Long playerId);

    GameStateDTO performAttack(MoveRequest request);

    GameStateDTO performCounterAttack(MoveRequest request);

    GameStateDTO launchArtilleryAttack(MoveRequest request);


    // TODO: turnOfPlayer vs playerId?
    GameStateDTO repairSubmarine(Long gameId, Long playerId);

    GameStateDTO revealRow(Long gameId, Long playerId, int y);

    boolean canPlayerPerformAction(Long gameId, Long playerId);

    boolean isGameOver(Long gameId);

    boolean validateMove(MoveRequest request);
}

//    GameStateDTO createPvPMatch();
//    GameStateDTO getGameById(Long gameId);
