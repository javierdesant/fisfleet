package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.api.mappers.PartidaMapper;
import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.MachineEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.PlayerEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.GameResultRepository;
import es.upm.etsisi.fis.fisfleet.domain.repositories.MachineRepository;
import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.Partida;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Transactional
@Service
@RequiredArgsConstructor
public class GameResultService {

    private final GameResultRepository repo;
    private final PartidaMapper partidaMapper;
    private final MachineRepository machineRepository;

    public void persistFinished(Partida partida) {
        if (!partida.fin()) {
            throw new IllegalArgumentException("The game isn't over: " + partida.getId());
        }

        GameResultEntity entity = partidaMapper.partidaToGameResultEntity(partida);
        entity.setEndDate(Instant.now());

        IJugador jugador1 = partida.getTurno().getPertenece();
        partida.swapTurn();
        IJugador jugador2 = partida.getTurno().getPertenece();

        setPlayerData(entity, jugador1, true);
        setPlayerData(entity, jugador2, false);
        persistMachineIfApplicable(jugador1);
        persistMachineIfApplicable(jugador2);

        repo.save(entity);
    }

    private void setPlayerData(GameResultEntity entity, IJugador jugador, boolean isPlayer1) {
        if (jugador instanceof PlayerEntity player) {
            if (isPlayer1) {
                entity.setPlayer1(player);
                player.getGamesAsPlayer1().add(entity);
                entity.setWinner(player);
                player.getGamesWon().add(entity);
            } else {
                entity.setPlayer2(player);
                player.getGamesAsPlayer2().add(entity);
            }
        }
    }

    private void persistMachineIfApplicable(IJugador jugador) {
        if (jugador instanceof MachineEntity machine) {
            machineRepository.save(machine);
        }
    }

    @Transactional(readOnly = true)
    public GameResultEntity read(Long gameId) {
        return repo.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la partida con ID " + gameId));
    }

    public void delete(Long gameId) {
        if (!repo.existsById(gameId)) {
            throw new EntityNotFoundException("No se puede eliminar: no existe la partida con ID " + gameId);
        }
        repo.deleteById(gameId);
    }
}
