package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.FactoriaMaquina;
import es.upm.etsisi.fis.model.Maquina;
import es.upm.etsisi.fis.model.TDificultad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "maquina")
public class MachineEntity extends PlayerEntity {

    public MachineEntity(TDificultad difficulty) {
        super();
        this.difficulty = difficulty;
        this.algorithm = switch (difficulty) {  // refactor me
            case FACIL:
                yield "BASIC";
            case NORMAL:
                yield "NORMAL";
            case DIFICIL:
                yield "EXPERT";
        };
        this.initPlayer();
        this.generatedName = this.getPlayer().getNombre();
    }

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
    protected void initPlayer() {
        Maquina ai = FactoriaMaquina.creaMaquina(difficulty.toString());
        this.setPlayer(ai);
    }
}
