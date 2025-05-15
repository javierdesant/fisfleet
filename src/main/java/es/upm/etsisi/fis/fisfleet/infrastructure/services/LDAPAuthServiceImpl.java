package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.AuthenticationRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.AuthenticationResponse;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.UserResponse;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import servidor.Autenticacion;

// Should this be called LDAPAuthServiceImpl or just LDAPAuthService?
@Service
@Slf4j
@AllArgsConstructor
public class LDAPAuthServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;


    @Override
    public UserResponse register(UserRequest request) {

        return null; // FIXME
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {


//          TODO: implement basic auth service like...
//                Authentication authentication = new UsernamePasswordAuthenticationToken(
//                        request.getUsername(), request.getPassword()
//                );
//                authenticationManager.authenticate(authentication);
//                UserEntity user = userRepository.findByEmail(request.getUsername())
//                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//                String jwt = tokenService.generateToken(user, buildExtraClaims(user));
//                return new AuthenticationResponse(jwt);
//        Do i have to do it for UsernamePasswordAuthenticationToken? Maybe its a type of OAuth
//        TODO: create extra claims fn
//        are there extra claim in OAuth???





        String email = "email"; // TEST
//                request.getEmail(); FIXME
        String password = request.getPassword();

        if (!Autenticacion.existeCuentaUPMStatic(email)) {
            throw new IllegalArgumentException("The email does not belong to UPM.");
        }

        // TODO: devolver un token de autenticacion
        return new AuthenticationResponse("token");
    }

    @Override
    public UserEntity findLoggedInUser() {
        // Retrieve the logged-in user from Spring Security's context
        String loggedInUserEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName(); // Gets the currently authenticated user's username (email in this case)

        if (loggedInUserEmail == null || loggedInUserEmail.isEmpty()) {
            throw new IllegalStateException("No authenticated user found.");
        }

        // Assume UserEntity can be built or fetched from a service based on the email
        return null;  // FIXME: no findByEmail method
//                TODO: implement... userService.findByEmail(loggedInUserEmail)
//                                  .orElseThrow(() -> new IllegalStateException("Authenticated user not found in the system."));
    }

}
