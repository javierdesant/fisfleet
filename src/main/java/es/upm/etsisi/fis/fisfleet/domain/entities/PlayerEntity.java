package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
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

    @OneToMany(mappedBy = "player")
    private Set<MoveEntity> moves = new LinkedHashSet<>();

    @OneToMany(mappedBy = "player1")
    private Set<GameResultEntity> gamesAsPlayer1 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "player2")
    private Set<GameResultEntity> gamesAsPlayer2 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "winner")
    private Set<GameResultEntity> gamesWon = new LinkedHashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<ScoreEntity> scores = new LinkedHashSet<>();

    @Override
    public boolean aceptarAccionComplementaria(TBarcoAccionComplementaria tBarcoAccionComplementaria, int i) {
        return false; // TODO
    }

    @Override
    public int[] realizaTurno(char[][] chars) {
        return new int[0]; // TODO
    }

    @Override
    public void addMovimiento(IMovimiento iMovimiento) {
        // TODO
    }

    @Override
    public String getNombre() {
        return ""; // TODO
    }

    @Override
    public void addPuntuacion(IPuntuacion iPuntuacion) {
        // TODO
    }

    @Override
    public List<IMovimiento> getMovimientos() {
        return List.of(); // TODO
    }

    @Override
    public List<IPuntuacion> getPuntuaciones() {
        return List.of(); // TODO
    }
}
