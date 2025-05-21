package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.GameStateDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.model.TDificultad;

public interface GameService {  // TODO: fix GameViewDTO vs GameStateDTO

    GameViewDTO createPvEMatch(TDificultad difficulty);

    GameStateDTO performAttack(MoveRequest request);

    GameStateDTO performSpecialAttack(MoveRequest request);

    boolean canPlayerPerformAction(Long gameId, Long playerId);
}

//    GameStateDTO createPvPMatch();
//    GameStateDTO getGameById(Long gameId);