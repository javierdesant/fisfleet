package es.upm.etsisi.fis.fisfleet.domain.entities;

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
public class MovimientoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_id_gen")
    @SequenceGenerator(name = "movimiento_id_gen", sequenceName = "movimiento_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partida_id", nullable = false)
    private PartidaEntity partida;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador_id", nullable = false)
    private JugadorEntity jugador;

    @NotNull
    @Column(name = "coordenada_x", nullable = false)
    private Integer coordenadaX;

    @NotNull
    @Column(name = "coordenada_y", nullable = false)
    private Integer coordenadaY;

    @Size(max = 10)
    @NotNull
    @Column(name = "resultado", nullable = false, length = 10)
    private String resultado;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

}