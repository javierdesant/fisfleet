package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.JwtKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Primary
@Slf4j
@Service
public class JwtService implements TokenService {

    private final JwtKeyProvider jwtKeyProvider;
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    public JwtService(JwtKeyProvider jwtKeyProvider) {
        this.jwtKeyProvider = jwtKeyProvider;
    }

    @Override
    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(issuedAt.getTime() + (EXPIRATION_IN_MINUTES * 60 * 1000));

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .header().add("typ", "JWT").and()
                .signWith(jwtKeyProvider.getSigningKey())
                .compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return this.extractAllClaims(jwt).getSubject();
    }

    @Override
    public boolean validateToken(String jwt) {
        try {
            Claims claims = this.extractAllClaims(jwt);

            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                log.info("The token has expired");
                return false;
            }

            return true;
        } catch (Exception ex) {
            log.error("Error validating the token: {}", ex.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String jwt) throws JwtException {
        return Jwts.parser()
                .verifyWith(jwtKeyProvider.getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}
