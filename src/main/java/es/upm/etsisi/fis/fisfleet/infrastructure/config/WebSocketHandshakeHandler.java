package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(@NonNull ServerHttpRequest request, @NonNull WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        Object playerId = attributes.get("playerId");
        if (playerId == null) {
            throw new IllegalStateException("playerId is missing in handshake attributes");
        }

        return playerId::toString;
    }
}

