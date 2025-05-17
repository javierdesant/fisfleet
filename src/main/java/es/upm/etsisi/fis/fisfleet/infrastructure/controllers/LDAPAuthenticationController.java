package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.AuthenticationRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.AuthenticationResponse;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.UserResponse;
import es.upm.etsisi.fis.fisfleet.api.mappers.UserMapper;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.AuthenticationService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LDAPAuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isValidToken = tokenService.validateToken(jwt);
        return ResponseEntity.ok(isValidToken);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> findMyProfile() {
        UserEntity user = authenticationService.findLoggedInUser();
        UserResponse userResponse = userMapper.mapToUserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
}
