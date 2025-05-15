package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.AuthenticationRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.AuthenticationResponse;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.UserResponse;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import servidor.Autenticacion;

// Should thgis be called LDAPAuthServiceImpl or just LDAPAuthService?
public class LDAPAuthServiceImpl implements AuthenticationService {

    @Override
    public UserResponse register(UserRequest request) {

        return null; // FIXME
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
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
