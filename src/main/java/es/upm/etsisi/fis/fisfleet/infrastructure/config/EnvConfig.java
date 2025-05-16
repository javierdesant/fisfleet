package es.upm.etsisi.fis.fisfleet.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @PostConstruct
    public void loadEnvVariables() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("POSTGRES_USER", dotenv.get("POSTGRES_USER", "default_user"));
        System.setProperty("POSTGRES_PASSWORD", dotenv.get("POSTGRES_PASSWORD", "default_password"));
    }
}