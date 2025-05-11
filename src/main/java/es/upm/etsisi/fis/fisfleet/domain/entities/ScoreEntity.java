package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "puntuacion")
public class ScoreEntity implements Serializable {
    @Id
    @Column(name = "jugador_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador_id", nullable = false)
    private PlayerEntity player;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "puntos", nullable = false)
    private Integer points;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant lastUpdateDate;
}