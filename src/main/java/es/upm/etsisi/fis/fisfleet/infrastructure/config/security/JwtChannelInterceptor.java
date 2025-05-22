package es.upm.etsisi.fis.fisfleet.infrastructure.config.security;

import es.upm.etsisi.fis.fisfleet.infrastructure.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (this.isInitialConnectCommand(accessor)) {
            this.validateAndAuthenticateToken(accessor);
        }
        return message;
    }

    private void validateAndAuthenticateToken(StompHeaderAccessor accessor) {
        String token;
        try {
            token = this.extractTokenFromHeaders(accessor);
            if (token == null) {
                log.warn("JWT token not found in WebSocket connection");
                throw new SecurityException("JWT Token is missing in WebSocket handshake");
            }
            this.authenticateUserWithToken(token, accessor);
        } catch (SecurityException ex) {
            log.error("Security exception during WebSocket handshake: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during WebSocket handshake: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error during WebSocket handshake", ex);
        }
    }

    private boolean isInitialConnectCommand(StompHeaderAccessor accessor) {
        return accessor != null && StompCommand.CONNECT.equals(accessor.getCommand());
    }

    private String extractTokenFromHeaders(StompHeaderAccessor accessor) {
        try {
            List<String> authorizationHeaders = accessor.getNativeHeader(AUTH_HEADER);
            if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
                String bearerToken = authorizationHeaders.get(0);
                if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
                    return bearerToken.substring(BEARER_PREFIX.length());
                }
            }
        } catch (Exception ex) {
            log.error("Error extracting token from WebSocket headers: {}", ex.getMessage());
            throw new SecurityException("Failed to extract JWT token from headers", ex);
        }
        return null;
    }

    private void authenticateUserWithToken(String token, StompHeaderAccessor accessor) {
        try {
            String username = jwtService.extractUsername(token);
            if (username == null) {
                log.warn("Unable to extract username from token");
                throw new UsernameNotFoundException("Username could not be extracted from the token");
            }

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                log.info("Authentication already exists in SecurityContext for username: {}", username);
                return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token)) {
                this.setAuthenticationContext(accessor, userDetails, username);
            } else {
                log.warn("Invalid token provided for username: {}", username);
                throw new SecurityException("JWT Token is invalid");
            }
        } catch (UsernameNotFoundException ex) {
            log.error("User details not found for username: {}", ex.getMessage());
            throw ex;
        } catch (SecurityException ex) {
            log.error("Security issue while authenticating JWT for WebSocket: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while authenticating user with token: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error during user authentication", ex);
        }
    }

    private void setAuthenticationContext(StompHeaderAccessor accessor, UserDetails userDetails, String username) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            accessor.setUser(authToken);
            log.info("User authenticated successfully in WebSocket: {}", username);
        } catch (Exception ex) {
            log.error("Error setting authentication context for username: {}", username, ex);
            throw new SecurityException("Failed to set authentication context", ex);
        }
    }
}