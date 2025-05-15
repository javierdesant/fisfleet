// MachineMapper.java
package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.fisfleet.domain.entities.MachineEntity;
import es.upm.etsisi.fis.model.Maquina;
import es.upm.etsisi.fis.model.TDificultad;

import java.util.HashMap;

public class MachineMapper {

    public MachineEntity toEntity(Maquina maquina) {
        MachineEntity entity = new MachineEntity();
        entity.setGeneratedName(maquina.getNombre());
        entity.setDifficulty(TDificultad.valueOf(maquina.getNombre().split("_")[1].split("_")[0]));
        entity.setAlgorithm(findAlgorithm(maquina.getNombre().split("_")[1].split("_")[0]));
        return entity;
    }

    private String findAlgorithm(String difficulty) {
        HashMap<String, String> algorithms = new HashMap<>();
        algorithms.put("FACIL", "BasicAlgoritmo");
        algorithms.put("NORMAL", "NormalAlgoritmo");
        algorithms.put("DIFICIL", "ExpertAlgoritmo");

        return algorithms.get(difficulty);
    }
}
