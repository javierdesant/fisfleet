package es.upm.etsisi.fis.fisfleet.domain.repositories;

import es.upm.etsisi.fis.fisfleet.domain.entities.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {
    /**
     * Todas las puntuaciones, ordenadas por puntos descendentes
     */
    List<ScoreEntity> findAllByOrderByPointsDesc();
}
