package es.upm.etsisi.fis.fisfleet.domain.entities;

import es.upm.etsisi.fis.fisfleet.infrastructure.core.HumanPlayer;
import es.upm.etsisi.fis.fisfleet.utils.Role;
import es.upm.etsisi.fis.fisfleet.utils.RoleMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import servidor.UPMUsers;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "usuarios")
public class UserEntity extends PlayerEntity implements UserDetails {

    @Size(max = 64)
    @NotNull
    @Column(name = "username_hash", nullable = false, length = 64)
    private String usernameHash;

    @Size(max = 50)
    @NotNull
    @Column(name = "alias", nullable = false, length = 50)
    private String alias;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant registrationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 10)
    private UPMUsers UPMUserType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = RoleMapper.getRoleForUPMUser(this.UPMUserType);

        if (role == null) {
            return List.of();
        }

        return Stream.concat(
                role.getPermissions().stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.name())),
                Stream.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
        ).toList();
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

    @Override
    protected void init() {
        this.setPlayer(new HumanPlayer(this.getId()));
    }
}
