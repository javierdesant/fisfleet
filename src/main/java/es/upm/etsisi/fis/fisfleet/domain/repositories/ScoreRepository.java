package es.upm.etsisi.fis.fisfleet.domain.repositories;

import es.upm.etsisi.fis.fisfleet.domain.entities.ScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {
}
