package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.GameResultRepository;
import es.upm.etsisi.fis.model.Partida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class GameResultService {
    private final GameResultRepository repo;

    public void persistFinished(Partida partida) {
        assert partida.fin();

        // TODO: Partida to GameResult mapper
        //  Long winner = Long.valueOf(partida.getTurnoName());
        GameResultEntity ent = new GameResultEntity();

        repo.save(ent);
    }

    // TODO
    public GameResultEntity read(Long gameId) {
        return null;
    }

    // TODO
    public void delete(Long gameId) {

    }
}
