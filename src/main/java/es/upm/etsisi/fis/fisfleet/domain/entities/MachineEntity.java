package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
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
    private Difficulty difficulty;

    public enum Difficulty {
        FACIL, MEDIO, DIFICIL
    }
}
