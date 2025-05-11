package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "jugador")
public class PlayerEntity implements Serializable {
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
}
