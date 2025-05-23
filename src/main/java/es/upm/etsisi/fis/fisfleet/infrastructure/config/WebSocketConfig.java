package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.JwtHandshakeHandler;
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
    private JwtHandshakeHandler jwtHandshakeHandler;
    private GameWebSocketHandler gameWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameWebSocketHandler, "api/ws/game")
                .addInterceptors(jwtInterceptor)
                .setHandshakeHandler(jwtHandshakeHandler)
                .setAllowedOrigins("*");
    }
}
