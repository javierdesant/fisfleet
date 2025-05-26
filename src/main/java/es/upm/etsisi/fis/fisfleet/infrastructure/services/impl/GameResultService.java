package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.api.mappers.PartidaMapper;
import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.MachineEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.PlayerEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.ScoreEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.GameResultRepository;
import es.upm.etsisi.fis.fisfleet.domain.repositories.MachineRepository;
import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.Partida;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class GameResultService {

    private final GameResultRepository gameResultRepository;
    private final PartidaMapper partidaMapper;
    private final MachineRepository machineRepository;

    public void persistFinished(Partida partida) {
        if (!partida.fin()) {
            throw new IllegalArgumentException("Game isn't over: " + partida.getId());
        }

        GameResultEntity gameResult = partidaMapper.partidaToGameResultEntity(partida);
        gameResult.setEndDate(Instant.now());

        IJugador player1 = partida.getTurno().getPertenece();
        partida.swapTurn();
        IJugador player2 = partida.getTurno().getPertenece();

        this.setPlayerData(gameResult, player1, true);
        this.setPlayerData(gameResult, player2, false);

        this.persistMachineIfApplicable(player1);
        this.persistMachineIfApplicable(player2);

        long baseScore = calculateScore(partida.getTableros());
        long winnerScore = baseScore + 20;

        player1.addPuntuacion(buildScore(gameResult, player1, winnerScore));
        player2.addPuntuacion(buildScore(gameResult, player2, baseScore));

        gameResultRepository.save(gameResult);
    }

    private long calculateScore(List<char[][]> boards) {
        long score = 0L;
        char[][] board = boards.get(1);

        for (char[] row : board) {
            for (char cell : row) {
                switch (cell) {
                    case 'A' -> score -= 1;
                    case 'H' -> score += 5;
                    case 'I' -> score += 2;
                    default -> {
                        // No op
                    }
                }
            }
        }
        return score;
    }

    private ScoreEntity buildScore(GameResultEntity game, IJugador player, long points) {
        return ScoreEntity.builder()
                .game(game)
                .player((PlayerEntity) player)
                .points((int) points)
                .build();
    }

    private void setPlayerData(GameResultEntity gameResult, IJugador player, boolean isPlayer1) {
        if (player instanceof PlayerEntity p) {
            if (isPlayer1) {
                gameResult.setPlayer1(p);
                p.getGamesAsPlayer1().add(gameResult);
                gameResult.setWinner(p);
                p.getGamesWon().add(gameResult);
            } else {
                gameResult.setPlayer2(p);
                p.getGamesAsPlayer2().add(gameResult);
            }
        }
    }

    private void persistMachineIfApplicable(IJugador player) {
        if (player instanceof MachineEntity machine) {
            machineRepository.save(machine);
        }
    }

    @Transactional(readOnly = true)
    public GameResultEntity read(Long gameId) {
        return gameResultRepository.findById(gameId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long gameId) {
        if (!gameResultRepository.existsById(gameId)) {
            throw new EntityNotFoundException();
        }
        gameResultRepository.deleteById(gameId);
    }
}
