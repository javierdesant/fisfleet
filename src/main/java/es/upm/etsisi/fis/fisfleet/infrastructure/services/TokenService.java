package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface TokenService {
    String generateToken(UserDetails user, Map<String, Object> extraClaims);

    String extractUsername(String token);

    boolean validateToken(String token);
}
