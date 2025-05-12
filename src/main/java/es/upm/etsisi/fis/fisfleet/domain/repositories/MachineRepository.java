package es.upm.etsisi.fis.fisfleet.domain.repositories;

import es.upm.etsisi.fis.fisfleet.domain.entities.MachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<MachineEntity, Long> {
}
