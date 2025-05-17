package es.upm.etsisi.fis.fisfleet.domain.repositories;

import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameResultEntity, Long> {
}
