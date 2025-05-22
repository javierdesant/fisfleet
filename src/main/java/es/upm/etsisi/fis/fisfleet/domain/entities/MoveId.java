package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class MoveId implements Serializable {
    @Serial
    private static final long serialVersionUID = -1558542939078575025L;
    @NotNull
    @Column(name = "partida_id", nullable = false)
    private Long gameId;

    @NotNull
    @Column(name = "jugador_id", nullable = false)
    private Long playerId;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha", nullable = false)
    private Instant date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MoveId that = (MoveId) o;
        return Objects.equals(this.playerId, that.playerId) &&
                Objects.equals(this.date, that.date) &&
                Objects.equals(this.gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, date, gameId);
    }

}