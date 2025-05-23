package es.upm.etsisi.fis.fisfleet.infrastructure.config.security;

import es.upm.etsisi.fis.fisfleet.infrastructure.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@AllArgsConstructor
@Slf4j
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private JwtService jwtService;

    @Override
    public boolean beforeHandshake(
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response,
            @NonNull WebSocketHandler wsHandler,
            @NonNull Map<String, Object> attributes) {

        log.info("Initiating WebSocket handshake for request: {}", request.getURI());

        if (!(request instanceof ServletServerHttpRequest servletRequest)) {
            log.error("Unsupported request type: {}", request.getClass().getName());
            return false;
        }

        HttpServletRequest httpRequest = servletRequest.getServletRequest();
        String gameId = this.extractGameId(httpRequest);

        this.validateTokenWebsocket(httpRequest, request);

        attributes.put("gameId", gameId);
        return true;
    }

    private String extractGameId(HttpServletRequest request) {
        String gameId = request.getParameter("gameId");
        log.debug("Extracted gameId: {} from request parameters", gameId);
        return gameId;
    }

    private void validateTokenWebsocket(HttpServletRequest request, ServerHttpRequest originalRequest) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Missing or invalid Authorization header in WebSocket handshake");
            throw new SecurityException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        if (!jwtService.validateToken(token)) {
            log.error("Invalid JWT token in session: {}", originalRequest.getURI());
            throw new SecurityException("Invalid JWT token");
        }
    }

    @Override
    public void afterHandshake(@NonNull ServerHttpRequest request,
                               @NonNull ServerHttpResponse response,
                               @NonNull WebSocketHandler wsHandler,
                               Exception exception) {
        if (exception != null) {
            log.error("WebSocket handshake error for {}: {}", request.getURI(), exception.getMessage(), exception);
        } else {
            log.info("WebSocket handshake completed successfully for {}", request.getURI());
        }
    }
}