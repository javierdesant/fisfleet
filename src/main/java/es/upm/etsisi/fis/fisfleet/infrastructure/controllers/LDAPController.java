package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.AuthenticationRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.AuthenticationResponse;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LDAPController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }
}
