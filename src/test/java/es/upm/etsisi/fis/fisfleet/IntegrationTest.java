package es.upm.etsisi.fis.fisfleet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

@Testcontainers
@SpringBootTest
class IntegrationTest {

    @SuppressWarnings("resource")
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("fisfleet-test-db")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @BeforeAll
    static void initDatabase() throws Exception {
        try (Connection conn = postgres.createConnection("")) {
            try (Statement stmt = conn.createStatement()) {
                // Ejecutar 01_schema.sql
                String schemaSql = Files.readString(Path.of("src/main/resources/db/01_schema.sql"));
                stmt.execute(schemaSql);

                // Ejecutar 02_triggers.sql
                String triggersSql = Files.readString(Path.of("src/main/resources/db/02_triggers.sql"));
                stmt.execute(triggersSql);
            }
        }
    }

    @Test
    void contextLoads() {
        // Aquí van tus pruebas
    }
}