package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.AuthenticationRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.AuthenticationResponse;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.UserResponse;
import es.upm.etsisi.fis.fisfleet.api.mappers.UserMapper;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.AuthenticationService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.TokenService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.UserService;
import es.upm.etsisi.fis.fisfleet.utils.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class LDAPAuthService implements AuthenticationService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;


    @Override
    public UserResponse register(UserRequest request) {
        UserEntity user = userService.create(request);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        String usernameHash = LDAPAuthenticator.authenticate(request.getUsername());
        UserEntity user = this.findUserByUsernameHash(usernameHash);

        this.authenticateUser(usernameHash, user.getPassword(), user.getAuthorities());

        String generatedToken = tokenService.generateToken(user, buildExtraClaims(user));

        return new AuthenticationResponse(generatedToken);
    }

    private UserEntity findUserByUsernameHash(String usernameHash) {
        return userRepository.findByUsernameHash(usernameHash)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void authenticateUser(String usernameHash, String password, Collection<? extends GrantedAuthority> authorities) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(usernameHash, password, authorities);

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private Map<String, Object> buildExtraClaims(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("usernameHash", user.getUsernameHash());
        extraClaims.put("authorities", user.getAuthorities());
        extraClaims.put("role", RoleMapper.getRoleForUPMUser(user.getUPMUserType()));
        return extraClaims;
    }


    @Override
    public UserEntity findLoggedInUser() {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            throw new IllegalStateException("No user logged in");
        }

        String usernameHash = (String) auth.getPrincipal();

        return this.findUserByUsernameHash(usernameHash);
    }
}
