package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "jugador")
public abstract class PlayerEntity implements Serializable, IJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "player")
    private Set<MoveEntity> moves = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "player1")
    private Set<GameResultEntity> gamesAsPlayer1 = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "player2")
    private Set<GameResultEntity> gamesAsPlayer2 = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "winner")
    private Set<GameResultEntity> gamesWon = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "player")
    private Set<ScoreEntity> scores = new LinkedHashSet<>();

    @Builder.Default
    @Transient
    private int moveIndex = 0;

    @Override
    public void addMovimiento(IMovimiento movIn) {
        if (movIn instanceof MoveEntity) {
            moves.add((MoveEntity) movIn);
        }
    }

    @Override
    public void addPuntuacion(IPuntuacion puntos) {
        if (puntos instanceof ScoreEntity) {
            scores.add((ScoreEntity) puntos);
        }
    }

    @Override
    public List<IMovimiento> getMovimientos() {
        return new ArrayList<>(moves);
    }

    @Override
    public List<IPuntuacion> getPuntuaciones() {
        return new ArrayList<>(scores);
    }

    @Override
    public String getNombre() {
        return this.getId().toString();
    }
}
