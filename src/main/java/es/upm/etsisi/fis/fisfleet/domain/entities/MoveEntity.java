package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.IMovimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "movimiento")
public class MoveEntity implements Serializable, IMovimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_id_gen")
    @SequenceGenerator(name = "movimiento_id_gen", sequenceName = "movimiento_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partida_id", nullable = false)
    private GameEntity game;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador_id", nullable = false)
    private PlayerEntity player;

    @NotNull
    @Column(name = "coordenada_x", nullable = false)
    private Integer coordinateX;

    @NotNull
    @Column(name = "coordenada_y", nullable = false)
    private Integer coordinateY;

    @Size(max = 10)
    @NotNull
    @Column(name = "resultado", nullable = false, length = 10)
    private String result;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant date;

    @Override
    public IMovimiento cloneMovimiento() {
        return MoveEntity.builder()
                .game(this.game)
                .player(this.player)
                .coordinateX(this.coordinateX)
                .coordinateY(this.coordinateY)
                .result(this.result)
                .date(this.date)
                .build();
    }

    /**
     * Note: A GameEntity is assigned with only the id initialized, while the rest of the object is not loaded
     * due to the use of LAZY proxies and LAZY developers. This could lead to issues such as LazyInitializationException
     * if attempting to access other properties outside the context of an active JPA session. Make sure to consider this
     * behavior in other parts of the system.
     */
    @Override
    public void setPartidaId(@NonNull Long aLong) {
        this.game = GameEntity.builder()
                .id(aLong)
                .build();
    }

    @Override
    public void setFila(int i) {
        this.setCoordinateY(i);
    }

    @Override
    public void setColumna(int i) {
        this.setCoordinateX(i);
    }

    @Override
    public void setTime(long l) {
        this.setDate(Instant.ofEpochMilli(l));
    }
}