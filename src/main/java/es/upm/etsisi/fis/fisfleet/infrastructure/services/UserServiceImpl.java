package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import servidor.ObtencionDeRol;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity create(UserRequest request) {
        String username = request.getUsername();
        log.info("Creating user with username: {}", username);

        String usernameHash = LDAPAuthenticator.authenticate(username);

        UserEntity newUser = UserEntity.builder()
                .usernameHash(usernameHash)
                .alias(request.getAlias())
                .registrationDate(Instant.now())
                .UPMUserType(ObtencionDeRol.get_UPM_AccountRol(username))
                .moves(new HashSet<>())
                .scores(new HashSet<>())
                .gamesAsPlayer1(new HashSet<>())
                .gamesAsPlayer2(new HashSet<>())
                .gamesWon(new HashSet<>())
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public UserEntity read(String s) {
        return null;    // FIXME
    }

    @Override
    public UserEntity update(UserRequest request, String s) {
        return null;    // FIXME
    }

    @Override
    public void delete(String s) {
        // TODO
    }
}
