package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.AuthenticationRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.AuthenticationResponse;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.UserResponse;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;

public interface AuthenticationService {
    UserResponse register(UserRequest request);

    AuthenticationResponse login(AuthenticationRequest request);

    UserEntity findLoggedInUser();
}
