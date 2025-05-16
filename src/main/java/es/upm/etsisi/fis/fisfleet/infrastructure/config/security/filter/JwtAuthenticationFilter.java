package es.upm.etsisi.fis.fisfleet.infrastructure.config.security.filter;

import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.JwtTokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (!isHeaderValid(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = this.extractJwt(authorizationHeader);
        String username = jwtTokenService.extractUsername(jwt);

        UserDetails userDetails = this.fetchUserDetails(username);
        this.authenticateUser(request, userDetails);

        filterChain.doFilter(request, response);
    }

    private boolean isHeaderValid(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

    private String extractJwt(String authorizationHeader) {
        return authorizationHeader.split(" ")[1];
    }

    private UserDetails fetchUserDetails(String usernameHash) {
        return userRepository.findByUsernameHash(usernameHash)
                .orElseThrow(EntityNotFoundException::new);
    }

    private void authenticateUser(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
