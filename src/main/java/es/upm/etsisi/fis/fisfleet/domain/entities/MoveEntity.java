package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.IMovimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @EmbeddedId
    private MoveId id;

    @MapsId("partidaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partida_id", nullable = false)
    private GameEntity game;

    @MapsId("jugadorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador_id", nullable = false)
    private PlayerEntity player;

    @NotNull
    @Column(name = "coordenada_x", nullable = false)
    private Integer coordinateX;

    @NotNull
    @Column(name = "coordenada_y", nullable = false)
    private Integer coordinateY;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "resultado", nullable = false, length = 10)
    private Result result;

    @Override
    public IMovimiento cloneMovimiento() {
        return MoveEntity.builder()
                .id(this.id)
                .game(this.game)
                .player(this.player)
                .coordinateX(this.coordinateX)
                .coordinateY(this.coordinateY)
                .result(this.result)
                .build();
    }

    @Override
    public void setPartidaId(@NonNull Long aLong) {
        this.id.setGameId(aLong);
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
        this.id.setDate(Instant.ofEpochMilli(l));
    }

    public enum Result {
        HUNDIDO, TOCADO, AGUA
    }

}