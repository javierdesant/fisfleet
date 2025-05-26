package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.JwtHandshakeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@AllArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private JwtHandshakeInterceptor jwtInterceptor;
    private WebSocketHandshakeHandler webSocketHandshakeHandler;
    private WebSocketGameHandler webSocketGameHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketGameHandler, "api/ws/pve/{gameId}", "api/ws/pvp/{gameId}")
                .addInterceptors(jwtInterceptor)
                .setHandshakeHandler(webSocketHandshakeHandler)
                .setAllowedOrigins("*");
    }
}
