package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.domain.GameSession;
import es.upm.etsisi.fis.fisfleet.domain.entities.MoveEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.PlayerEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.ScoreEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.GameResultRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.adapters.GameManager;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.AuthenticationService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
import es.upm.etsisi.fis.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;


// TODO important class!


@AllArgsConstructor
@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameResultRepository gameResultRepository;
    //    private final MachineMapper machineMapper;
    private final GameCacheService gameCacheService;
    private final AuthenticationService authenticationService;
    private final GameManager gameManager;

    @Override
    public GameViewDTO createPvEMatch(TDificultad difficulty) {
        Maquina maquina = FactoriaMaquina.creaMaquina(difficulty.name());
        assert maquina != null;

//        FIXME
//         MachineEntity machine = machineMapper.mapToMachineEntity(maquina);

//        return this.createMatch(this.getLoggedInUser(), machine);
        return null;    // FIXME
    }

    private UserEntity getLoggedInUser() {
        return authenticationService.findLoggedInUser();
    }

//    public GameStateDTO createPvPMatch() {
//        return this.createMatch(this.getLoggedInUser(), ...);
//    }

    private GameViewDTO createMatch(PlayerEntity player1, PlayerEntity player2) {
        IPuntuacion puntuacion = new ScoreEntity();
        IMovimiento movimiento = new MoveEntity();

        // Create game in controller
        gameManager.crearPartida(player1, player2, puntuacion, movimiento);

        // Create game state
        GameSession gameState = GameSession.builder()
                .player1Id(player1.getId())
                .player2Id(player2.getId())
                .turnOfPlayer(new Random().nextInt(2) + 1) // 1 or 2
                .player1Board(new char[10][10])
                .player2Board(new char[10][10])
                .startDate(Instant.now())
                .build();

        // FIXME:
//        // Associate players with game
//        gameCacheService.addPlayerGame(player1.getId(), gameId);
//        gameCacheService.addPlayerGame(player2.getId(), gameId);
//
//        // Create and cache player view for player1
//        GameViewDTO player1View = createPlayerView(gameState, player1.getId());
//        gameCacheService.savePlayerView(gameId, player1.getId(), player1View);
//
//        log.info("Created new game with ID: {}", gameId);

        return null;    // FIXME
//                player1View;
    }

    private GameViewDTO createPlayerView(GameSession gameState, Long playerId) {
        boolean isPlayer1 = playerId.equals(gameState.getPlayer1Id());

        GameViewDTO view = new GameViewDTO();
        view.setGameId(gameState.getGameId());

        // Set the player's own board
        if (isPlayer1) {
            view.setOwnBoard(gameState.getPlayer1Board());
            view.setEnemyBoardMasked(maskBoard(gameState.getPlayer2Board()));
        } else {
            view.setOwnBoard(gameState.getPlayer2Board());
            view.setEnemyBoardMasked(maskBoard(gameState.getPlayer1Board()));
        }

        // Set turn information
        view.setYourTurn((isPlayer1 && gameState.getTurnOfPlayer() == 1) || 
                         (!isPlayer1 && gameState.getTurnOfPlayer() == 2));

//         TODO: Set ability information
//          view.setAvailableAbility();

        return view;
    }

    private char[][] maskBoard(char[][] board) {
        if (board == null) {
            return new char[10][10];
        }

        char[][] maskedBoard = new char[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Only show hits and misses, hide ships
                char cell = board[i][j];
                if (cell == 'X' || cell == 'O') { // Assuming X is hit, O is miss
                    maskedBoard[i][j] = cell;
                } else {
                    maskedBoard[i][j] = ' '; // Hide ships and empty cells
                }
            }
        }

        return maskedBoard;
    }

    @Override
    public GameSession getGameById(Long gameId) {
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }

        return gameCacheService.getGameState(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found in cache: " + gameId));
    }

    @Override
    public GameViewDTO getGameViewForPlayer(Long gameId, Long playerId) {
        if (gameId == null || playerId == null) {
            throw new IllegalArgumentException("Game ID and player ID cannot be null");
        }

        // Check if the view is already cached
        Optional<GameViewDTO> cachedView = gameCacheService.getPlayerView(gameId, playerId);
        if (cachedView.isPresent()) {
            return cachedView.get();
        }

        // If not cached, get the game state and create a view
        GameSession gameState = getGameById(gameId);

        // Verify the player is part of the game
        if (!playerId.equals(gameState.getPlayer1Id()) && !playerId.equals(gameState.getPlayer2Id())) {
            throw new IllegalArgumentException("Player is not part of the game: " + playerId);
        }

        // Create and cache the view
        GameViewDTO view = createPlayerView(gameState, playerId);
        gameCacheService.savePlayerView(gameId, playerId, view);

        return view;
    }

    @Override
    public GameSession performAttack(MoveRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Move request cannot be null");
        }

        Long gameId = 0L;
//         FIXME       request.getGameId();
        Long playerId = 0L;
//         FIXME       request.getPlayerId();
        int x = request.getCoordinateX();
        int y = request.getCoordinateY();

        if (gameId == null || playerId == null) {
            throw new IllegalArgumentException("Game ID and player ID cannot be null");
        }

        // Get the game state from cache
        GameSession gameState = getGameById(gameId);

        // Verify it's the player's turn
        boolean isPlayer1 = playerId.equals(gameState.getPlayer1Id());
        boolean isPlayer2 = playerId.equals(gameState.getPlayer2Id());

        if (!isPlayer1 && !isPlayer2) {
            throw new IllegalArgumentException("Player is not part of the game: " + playerId);
        }

        if ((gameState.getTurnOfPlayer() == 1 && !isPlayer1) || 
            (gameState.getTurnOfPlayer() == 2 && !isPlayer2)) {
            throw new IllegalStateException("It's not the player's turn");
        }

        // Perform the attack
        char[][] targetBoard = isPlayer1 ? gameState.getPlayer2Board() : gameState.getPlayer1Board();

        // Check if the attack is valid (within bounds)
        if (x < 0 || x >= targetBoard.length || y < 0 || y >= targetBoard[0].length) {
            throw new IllegalArgumentException("Attack coordinates out of bounds: (" + x + ", " + y + ")");
        }

        // Check if the cell has already been attacked
        if (targetBoard[x][y] == 'X' || targetBoard[x][y] == 'O') {
            throw new IllegalArgumentException("Cell has already been attacked: (" + x + ", " + y + ")");
        }

        // Determine if it's a hit or miss
        boolean isHit = targetBoard[x][y] != ' ' && targetBoard[x][y] != 0;
        targetBoard[x][y] = isHit ? 'X' : 'O'; // X for hit, O for miss

        // Update the board in the game state
        if (isPlayer1) {
            gameState.setPlayer2Board(targetBoard);
        } else {
            gameState.setPlayer1Board(targetBoard);
        }

        // Switch turns
        gameState.setTurnOfPlayer(gameState.getTurnOfPlayer() == 1 ? 2 : 1);

        // Save the updated game state
        gameCacheService.saveGameState(gameState);

        // Update player views
        GameViewDTO player1View = createPlayerView(gameState, gameState.getPlayer1Id());
        GameViewDTO player2View = createPlayerView(gameState, gameState.getPlayer2Id());

        gameCacheService.savePlayerView(gameId, gameState.getPlayer1Id(), player1View);
        gameCacheService.savePlayerView(gameId, gameState.getPlayer2Id(), player2View);

        log.debug("Attack performed at position ({}, {}) by player {}: {}", 
                 x, y, playerId, isHit ? "HIT" : "MISS");

        return gameState;
    }

    // TODO
    @Override
    public GameSession performCounterAttack(MoveRequest request) {
        return null;
    }

    @Override
    public GameSession launchArtilleryAttack(MoveRequest request) {
        return null;
    }

    @Override
    public GameSession repairSubmarine(Long gameId, Long playerId) {
        return null;
    }

    @Override
    public GameSession revealRow(Long gameId, Long playerId, int y) {
        return null;
    }


    // TODO!
    // FIXME

//    @Override
//    public GameStateDTO performSpecialAttack(MoveRequest request) {
//        if (request == null) {
//            throw new IllegalArgumentException("Move request cannot be null");
//        }
//
////        FIXME:
//        Long gameId = 0L;
////                request.getGameId();
//        Long playerId = 0L;

    /// /                request.getPlayerId();
//        SpecialAbility ability = request.getSpecialAbility();
//
//        if (gameId == null || playerId == null) {
//            throw new IllegalArgumentException("Game ID and player ID cannot be null");
//        }
//
//        if (ability == null || ability == SpecialAbility.NONE) {
//            throw new IllegalArgumentException("Special ability cannot be null or NONE");
//        }
//
//        // Get the game state from cache
//        GameStateDTO gameState = getGameById(gameId);
//
//        // Verify it's the player's turn
//        boolean isPlayer1 = playerId.equals(gameState.getPlayer1Id());
//        boolean isPlayer2 = playerId.equals(gameState.getPlayer2Id());
//
//        if (!isPlayer1 && !isPlayer2) {
//            throw new IllegalArgumentException("Player is not part of the game: " + playerId);
//        }
//
//        if ((gameState.getTurnOfPlayer() == 1 && !isPlayer1) ||
//            (gameState.getTurnOfPlayer() == 2 && !isPlayer2)) {
//            throw new IllegalStateException("It's not the player's turn");
//        }
//
//        // Verify the player can use the ability
//        if (gameState.getCanUseAbility() == SpecialAbility.NONE) {
//            throw new IllegalStateException("No special ability available");
//        }
//
//        boolean canUseAbility = (isPlayer1 && gameState.getCanUseAbility().toString().contains("PLAYER1")) ||
//                               (!isPlayer1 && gameState.getCanUseAbility().toString().contains("PLAYER2"));
//
//        if (!canUseAbility) {
//            throw new IllegalStateException("Player cannot use special ability");
//        }
//
//        // Perform the special attack based on the ability type
//        int x = request.getCoordinateX();
//        int y = request.getCoordinateY();
//
//        // TODO: Implement special attack logic based on ability type
//        // This is a simplified implementation
//        char[][] targetBoard = isPlayer1 ? gameState.getPlayer2Board() : gameState.getPlayer1Board();
//
//        // Check if the attack is valid (within bounds)
//        if (x < 0 || x >= targetBoard.length || y < 0 || y >= targetBoard[0].length) {
//            throw new IllegalArgumentException("Attack coordinates out of bounds: (" + x + ", " + y + ")");
//        }
//
//        // Special attack effect (simplified)
//        // For example, a special attack might hit multiple cells
//        for (int i = Math.max(0, x - 1); i <= Math.min(targetBoard.length - 1, x + 1); i++) {
//            for (int j = Math.max(0, y - 1); j <= Math.min(targetBoard[0].length - 1, y + 1); j++) {
//                // Skip already attacked cells
//                if (targetBoard[i][j] == 'X' || targetBoard[i][j] == 'O') {
//                    continue;
//                }
//
//                // Determine if it's a hit or miss
//                boolean isHit = targetBoard[i][j] != ' ' && targetBoard[i][j] != 0;
//                targetBoard[i][j] = isHit ? 'X' : 'O'; // X for hit, O for miss
//            }
//        }
//
//        // Update the board in the game state
//        if (isPlayer1) {
//            gameState.setPlayer2Board(targetBoard);
//        } else {
//            gameState.setPlayer1Board(targetBoard);
//        }
//
//        // Reset the special ability
//        gameState.setCanUseAbility(SpecialAbility.NONE);
//
//        // Switch turns
//        gameState.setTurnOfPlayer(gameState.getTurnOfPlayer() == 1 ? 2 : 1);
//
//        // Save the updated game state
//        gameCacheService.saveGameState(gameState);
//
//        // Update player views
//        GameViewDTO player1View = createPlayerView(gameState, gameState.getPlayer1Id());
//        GameViewDTO player2View = createPlayerView(gameState, gameState.getPlayer2Id());
//
//        gameCacheService.savePlayerView(gameId, gameState.getPlayer1Id(), player1View);
//        gameCacheService.savePlayerView(gameId, gameState.getPlayer2Id(), player2View);
//
//        log.debug("Special attack performed at position ({}, {}) by player {} using ability {}",
//                 x, y, playerId, ability);
//
//        return gameState;
//    }

    @Override
    public boolean canPlayerPerformAction(Long gameId, Long playerId) {
        if (gameId == null || playerId == null) {
            return false;
        }

        try {
            // Get the game state from cache
            Optional<GameSession> optionalGameState = gameCacheService.getGameState(gameId);

            if (optionalGameState.isEmpty()) {
                return false;
            }

            GameSession gameState = optionalGameState.get();

            // Check if the player is part of the game
            boolean isPlayer1 = playerId.equals(gameState.getPlayer1Id());
            boolean isPlayer2 = playerId.equals(gameState.getPlayer2Id());

            if (!isPlayer1 && !isPlayer2) {
                return false;
            }

            // Check if it's the player's turn
            return (gameState.getTurnOfPlayer() == 1 && isPlayer1) || 
                   (gameState.getTurnOfPlayer() == 2 && isPlayer2);
        } catch (Exception e) {
            log.error("Error checking if player can perform action", e);
            return false;
        }
    }

    // TODO

    @Override
    public boolean isGameOver(Long gameId) {
        return false;
    }

    @Override
    public boolean validateMove(MoveRequest request) {
        return false;
    }

}
