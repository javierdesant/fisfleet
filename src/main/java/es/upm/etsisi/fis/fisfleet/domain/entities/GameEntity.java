package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "partida")
public class GameEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partida_id_gen")
    @SequenceGenerator(name = "partida_id_gen", sequenceName = "partida_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador1_id", nullable = false)
    private PlayerEntity player1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador2_id", nullable = false)
    private PlayerEntity player2;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private Instant endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ganador_id", nullable = false)
    private PlayerEntity winner;

    @OneToMany(mappedBy = "game")
    private Set<MoveEntity> moves = new LinkedHashSet<>();
}