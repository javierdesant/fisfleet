package es.upm.etsisi.fis.fisfleet.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class UserEntity implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_id_gen")
    @SequenceGenerator(name = "usuarios_id_gen", sequenceName = "usuarios_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

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
    private PlayerEntity player;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_registro", nullable = false)
    private Instant registrationDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * This system does not store or use passwords directly, as authentication
     * is managed through the UPM's LDAP service.
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