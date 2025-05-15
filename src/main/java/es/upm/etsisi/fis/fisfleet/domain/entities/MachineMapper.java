// MachineMapper.java
package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.fisfleet.domain.entities.MachineEntity;
import es.upm.etsisi.fis.model.Maquina;

public class MachineMapper {

    public MachineEntity toEntity(Maquina maquina) {
        MachineEntity entity = new MachineEntity();
        entity.setGeneratedName(maquina.getNombre());
        entity.setAlgorithm(maquina.getAlgoritmo().getClass().getSimpleName());
        entity.setDifficulty(MachineEntity.Difficulty.valueOf(maquina.getTdificultad().name()));
        return entity;
    }

}
