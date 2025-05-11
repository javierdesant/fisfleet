package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import utilidades.Cifrado;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id_gen")
    @SequenceGenerator(name = "usuarios_id_gen", sequenceName = "usuarios_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "username_hash", nullable = false, length = 64)
    private String usernameHash;

    @Size(max = 50)
    @NotNull
    @Column(name = "alias", nullable = false, length = 50)
    private String alias;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador_id")
    private JugadorEntity jugador;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_registro", nullable = false)
    private Instant fechaRegistro;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO
        return null;
    }

    /**
     * Este sistema no almacena ni utiliza contraseñas directamente, ya que la autenticación
     * se gestiona a través del servicio LDAP de la UPM.
     */
    @Override
    public String getPassword() {
        return "{noop}none";
    }

    @Override
    public String getUsername() {
        return this.getUsernameHash();
    }
}