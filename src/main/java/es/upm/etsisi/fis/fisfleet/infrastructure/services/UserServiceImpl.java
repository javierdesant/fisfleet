package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.time.Instant;
import java.util.HashSet;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity create(UserRequest request) {
        String username = request.getUsername();
        log.info("Creating user with username: {}", username);

        if (!ObtencionDeRol.get_UPM_AccountRol(username).equals(UPMUsers.ALUMNO)) {
            log.warn("Registration is restricted to students." +
                    "Administrators must be registered manually by a system administrator.");
            throw new IllegalArgumentException("Registration is restricted to students.");
        }

        String usernameHash = LDAPAuthenticator.authenticate(username);

        UserEntity newUser = UserEntity.builder()
                .usernameHash(usernameHash)
                .alias(request.getAlias())
                .registrationDate(Instant.now())
                .UPMUserType(UPMUsers.ALUMNO)
                .moves(new HashSet<>())
                .scores(new HashSet<>())
                .gamesAsPlayer1(new HashSet<>())
                .gamesAsPlayer2(new HashSet<>())
                .gamesWon(new HashSet<>())
                .build();

        return userRepository.save(newUser);
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

        existingUser.setAlias(request.getAlias());
        existingUser.setUPMUserType(ObtencionDeRol.get_UPM_AccountRol(request.getUsername()));

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