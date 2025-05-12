package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "maquina")
public class MachineEntity implements Serializable {

    // TODO: Consider the need to extend or map `Maquina` within `MachineEntity`, or create a dedicated mapper class to
    //   handle transitions between the business domain (`Maquina`) and persistence layer (`MachineEntity`).
    //  Maquina has an `algoritmo` field, which is private and encapsulated, making it difficult to directly access or manipulate.
    //  A mapping strategy may also help maintain separation of concerns and ease future maintenance or modifications.

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maquina_id_gen")
    @SequenceGenerator(name = "maquina_id_gen", sequenceName = "maquina_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador_id")
    private PlayerEntity player;

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