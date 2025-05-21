package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.model.IPuntuacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "puntuacion")
public class ScoreEntity implements Serializable, IPuntuacion {
    @EmbeddedId
    private ScoreId id;

    @MapsId("gameId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partida_id", nullable = false)
    private GameResultEntity game;

    @MapsId("playerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador_id", nullable = false)
    private PlayerEntity player;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "puntos", nullable = false)
    private Integer points;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant updateDate;

    @Override
    public long getPuntos() {
        return this.getPoints();
    }

    @Override
    public IPuntuacion clonePuntuacion() {
        ScoreId newId = new ScoreId(this.game.getId(), this.player.getId());

        return ScoreEntity.builder()
                .id(newId)
                .game(this.game)
                .player(this.player)
                .points(this.points)
                .updateDate(this.updateDate)
                .build();
    }

    @Override
    public void setPuntuacion(long l) {
        this.setPoints(Math.toIntExact(l));
    }

    @Override
    public void setPartidaId(Long aLong) {
        this.id.setGameId(aLong);
    }
}