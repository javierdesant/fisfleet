package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "jugador")
public abstract class PlayerEntity implements Serializable, IJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "player1")
    private Set<GameResultEntity> gamesAsPlayer1;

    @OneToMany(mappedBy = "player2")
    private Set<GameResultEntity> gamesAsPlayer2;

    @OneToMany(mappedBy = "winner")
    private Set<GameResultEntity> gamesWon;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MoveEntity> moves;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScoreEntity> scores;

    @Transient
    private IJugador player;

    public PlayerEntity() {
        this.moves = new LinkedHashSet<>();
        this.gamesAsPlayer1 = new LinkedHashSet<>();
        this.gamesAsPlayer2 = new LinkedHashSet<>();
        this.gamesWon = new LinkedHashSet<>();
        this.scores = new LinkedHashSet<>();
    }

    protected void setPlayer(IJugador player) {
        this.player = player;
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    protected abstract void initPlayer();

    @Override
    public boolean aceptarAccionComplementaria(TBarcoAccionComplementaria tBarcoAccionComplementaria, int cantidadDisponible) {
        return player.aceptarAccionComplementaria(tBarcoAccionComplementaria, cantidadDisponible);
    }

    @Override
    public int[] realizaTurno(char[][] chars) {
        return player.realizaTurno(chars);
    }

    @Override
    public void addMovimiento(IMovimiento movIn) {
        if (movIn instanceof MoveEntity move) {
            move.setPlayer(this);
            moves.add(move);
        }
    }

    @Override
    public void addPuntuacion(IPuntuacion puntIn) {
        if (puntIn instanceof ScoreEntity score) {
            score.setPlayer(this); // <- Esto es CLAVE
            scores.add(score);
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
