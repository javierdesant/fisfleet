package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
public class PartidaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partida_id_gen")
    @SequenceGenerator(name = "partida_id_gen", sequenceName = "partida_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador1_id", nullable = false)
    private JugadorEntity jugador1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador2_id", nullable = false)
    private JugadorEntity jugador2;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private Instant fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private Instant fechaFin;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ganador_id", nullable = false)
    private JugadorEntity ganador;

    @OneToMany(mappedBy = "partida")
    private Set<MovimientoEntity> movimientos = new LinkedHashSet<>();

}