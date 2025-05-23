package es.upm.etsisi.fis.fisfleet.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.upm.etsisi.fis.fisfleet.api.dto.GameSession;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<Long, GameSession> gameStateCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(500)
                .build();
    }

    @Bean
    public Cache<Long, List<Long>> playerGamesCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(500)
                .build();
    }

    @Bean
    public Cache<String, GameViewDTO> playerViewsCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(500)
                .build();
    }

    @Bean
    public Cache<Long, String> activeSessionsCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(500)
                .build();
    }
}
