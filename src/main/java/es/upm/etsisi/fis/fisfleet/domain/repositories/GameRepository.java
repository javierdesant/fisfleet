package es.upm.etsisi.fis.fisfleet.domain.repositories;

import es.upm.etsisi.fis.fisfleet.domain.entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
}
