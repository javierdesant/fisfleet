package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.TDificultad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "maquina")
public class MachineEntity extends PlayerEntity {

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre_generado", nullable = false, length = 50)
    private String generatedName;

    @Size(max = 50)
    @NotNull
    @Column(name = "algoritmo", nullable = false, length = 50)
    private String algorithm;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad", nullable = false, length = 20)
    private TDificultad difficulty;

    @Override
    public String getNombre() {
        return this.getGeneratedName();
    }
}
