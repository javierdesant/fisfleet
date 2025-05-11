package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class ScoreId implements Serializable {
    @Serial
    private static final long serialVersionUID = 7283862205487258500L;

    @NotNull
    @Column(name = "partida_id", nullable = false)
    private Long gameId;

    @NotNull
    @Column(name = "jugador_id", nullable = false)
    private Long playerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ScoreId entity = (ScoreId) o;
        return Objects.equals(this.playerId, entity.playerId) &&
                Objects.equals(this.gameId, entity.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, gameId);
    }
}
