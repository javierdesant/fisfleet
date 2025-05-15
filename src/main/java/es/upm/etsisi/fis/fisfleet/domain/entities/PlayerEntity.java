package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "jugador")
public class PlayerEntity implements Serializable, IJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "player")
    private MachineEntity machine;

    @OneToMany(mappedBy = "player")
    private Set<MoveEntity> moves = new LinkedHashSet<>();

    @OneToMany(mappedBy = "player1")
    private Set<GameEntity> gamesAsPlayer1 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "player2")
    private Set<GameEntity> gamesAsPlayer2 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "winner")
    private Set<GameEntity> gamesWon = new LinkedHashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<ScoreEntity> scores = new LinkedHashSet<>();

    @OneToOne(mappedBy = "player")
    private UserEntity user;

    @Override
    public boolean aceptarAccionComplementaria(TBarcoAccionComplementaria tBarcoAccionComplementaria, int cantidadDisponible) {
        return cantidadDisponible>0;
    }

    @Override
    public int[] realizaTurno(char[][] chars) {
        if (!moves.isEmpty()) {
            MoveEntity move = moves.iterator().next();
            return new int[] { move.getCoordinateX(), move.getCoordinateY() };
        }
        return new int[] { 0, 0 };
    }

    @Override
    public void addMovimiento(IMovimiento movIn) {
        if (movIn instanceof MoveEntity) {
            moves.add((MoveEntity) movIn);
        }
    }

    @Override
    public String getNombre() {
        return (user != null && user.getAlias() != null) ? user.getAlias() : "";
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
}
