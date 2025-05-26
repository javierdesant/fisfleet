package es.upm.etsisi.fis.fisfleet.infrastructure.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.upm.etsisi.fis.model.Partida;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<UUID, Partida> gamePartidaCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(500)
                .build();
    }

    @Bean
    public Cache<Long, String> playerSessionCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(500)
                .build();
    }
}
