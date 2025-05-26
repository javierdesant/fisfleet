package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator;
import es.upm.etsisi.fis.fisfleet.infrastructure.exceptions.RegistrationException;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.UserService;
import es.upm.etsisi.fis.fisfleet.utils.RolePermission;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.time.Instant;

@Transactional
@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity create(UserRequest request) {
        String username = request.getUsername();
        log.info("Creating user with username: {}", username);

        validateEmail(username);
        String usernameHash = LDAPAuthenticator.authenticate(username);

        UserEntity newUser = new UserEntity(usernameHash, request.getAlias());
        assert newUser.getAuthorities().contains(new SimpleGrantedAuthority(RolePermission.REGISTER.name()));

        return userRepository.save(newUser);
    }

    private static void validateEmail(String username) {
        if (!ObtencionDeRol.get_UPM_AccountRol(username).equals(UPMUsers.ALUMNO)) {
            log.warn("Registration is restricted to students." +
                    "Administrators must be registered manually by a system administrator.");
            throw new RegistrationException("Registration is restricted to students.");
        }
    }

    @Override
    public UserEntity read(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserEntity update(UserRequest request, Long id) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        log.info("Updating user with id: {}", id);

        String username = request.getUsername();
        validateEmail(username);

        existingUser.setAlias(request.getAlias());
        existingUser.setUsernameHash(LDAPAuthenticator.authenticate(username));

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Long id) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        log.info("Deleting user with id: {}", id);

        userRepository.delete(existingUser);
    }
}