package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "jugador")
public class JugadorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('jugador_id_seq')")
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(mappedBy = "jugador")
    private MaquinaEntity maquina;

    @OneToMany(mappedBy = "jugador")
    private Set<MovimientoEntity> movimientos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "jugador1")
    private Set<PartidaEntity> partidasComoJugador1 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "jugador2")
    private Set<PartidaEntity> partidasComoJugador2 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "ganador")
    private Set<PartidaEntity> partidasGanadas = new LinkedHashSet<>();

    @OneToOne(mappedBy = "jugador")
    private PuntuacionEntity puntuacion;

    @OneToOne(mappedBy = "jugador")
    private UsuarioEntity usuario;

}