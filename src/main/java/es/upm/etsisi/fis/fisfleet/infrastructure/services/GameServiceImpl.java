package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.GameStateDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.api.mappers.MachineMapper;
import es.upm.etsisi.fis.fisfleet.domain.entities.*;
import es.upm.etsisi.fis.fisfleet.domain.repositories.GameResultRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.adapters.GameController;
import es.upm.etsisi.fis.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@AllArgsConstructor
@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameResultRepository gameResultRepository;
    private final MachineMapper machineMapper;
    //    private final GameCacheService gameCacheService;
    private final AuthenticationService authenticationService;
    private final GameController gameController;

    @Override
    public GameViewDTO createPvEMatch(TDificultad difficulty) {
        Maquina maquina = FactoriaMaquina.creaMaquina(difficulty.name());
        assert maquina != null;

        MachineEntity machine = machineMapper.mapToMachineEntity(maquina);

        return this.createMatch(this.getLoggedInUser(), machine);
    }

    private UserEntity getLoggedInUser() {
        return authenticationService.findLoggedInUser();
    }

//    public GameStateDTO createPvPMatch() {
//        return this.createMatch(this.getLoggedInUser(), ...);
//    }

    private GameViewDTO createMatch(PlayerEntity player1, PlayerEntity player2) { // TODO: plantearse gameResult to gameState
        IPuntuacion puntuacion = new ScoreEntity();
        IMovimiento movimiento = new MoveEntity();

        GameStateDTO gameState = GameStateDTO.builder()
                .player1Id(player1.getId())
                .player2Id(player2.getId())
                .turnOfPlayer(new Random().nextInt(2))
                .player1Board(new char[10][10])
                .player2Board(new char[10][10])
                .startDate(Instant.now())
                .build();

        gameController.crearPartida(player1, player2, puntuacion, movimiento);

//      TODO:  gameCacheService.saveGameState(gameResult.getId(), gameState);

        return null;
//                FIXME: gameResult.getId();
    }

//    @Override
//    public GameStateDTO getGameById(Long gameId) {
//        GameStateDTO gameState = gameCacheService.getGameState(gameId);
//        if (gameState == null) {
//            throw new IllegalArgumentException("Game not found in cache: " + gameId);
//        }
//        return gameState;
//    }

    @Override   // TODO
    public GameStateDTO performAttack(MoveRequest request) {
//        GameStateDTO gameState = getGameById(gameId);
//
//        // Verificar el turno actual
//        if ((gameState.getCurrentTurn() == 1 && !playerId.equals(gameState.getPlayer1Id())) ||
//                (gameState.getCurrentTurn() == 2 && !playerId.equals(gameState.getPlayer2Id()))) {
//            throw new IllegalStateException("No es el turno del jugador");
//        }

        // TODO: Implementar lógica de ataque aquí
        //  Logica de controladorPartida o similar
        // TODO: implementar movimientos

        // TODO: Implementar controladorPartida.realizaContrataque();
//        log.debug("Ataque realizado en posición ({}, {}) por jugador {}", x, y, playerId);
//
//        gameState.setCurrentTurn(gameState.getCurrentTurn() == 1 ? 2 : 1);
//        gameCacheService.saveGameState(gameId, gameState);

        return null; // TODO: devolver el resultado del ataque
    }


    // TODO!

    @Override
    public GameStateDTO performSpecialAttack(MoveRequest request) {
        return null;
    }

    @Override
    public boolean canPlayerPerformAction(Long gameId, Long playerId) {
        return false;
    }

}
