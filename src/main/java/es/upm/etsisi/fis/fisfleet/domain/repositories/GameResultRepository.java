package es.upm.etsisi.fis.fisfleet.domain.repositories;

import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.PlayerEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameResultRepository extends JpaRepository<GameResultEntity, Long> {
    /**
     * Top 10 puntuaciones de un jugador
     */
    List<GameResultEntity> findTop10ByPlayer1IdOrderByWinner_ScoresDesc(Long playerId);

    /**
     * Todos los resultados de una partida, ordenados por fecha de inicio ascendente
     */
    List<GameResultEntity> findByIdOrderByStartDateAsc(Long gameId);
}
