package es.upm.etsisi.fis.fisfleet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

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
                String schemaSql = Files.readString(Path.of("src/main/resources/db/01_schema.sql"));
                stmt.execute(schemaSql);
                
                String triggersSql = Files.readString(Path.of("src/main/resources/db/02_triggers.sql"));
                stmt.execute(triggersSql);
            }
        }
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        assertNotNull(jdbcTemplate);
    }

    @Test
    void testInsertAndQueryJugador() {
        jdbcTemplate.update("INSERT INTO jugador DEFAULT VALUES");
        Long id = jdbcTemplate.queryForObject("SELECT id FROM jugador LIMIT 1", Long.class);
        assertNotNull(id, "Expected a player to be inserted");
    }

    @Test
    void testUsuariosConstraints() {
        jdbcTemplate.update("INSERT INTO jugador DEFAULT VALUES");
        Long jugadorId = jdbcTemplate.queryForObject("SELECT id FROM jugador LIMIT 1", Long.class);
        jdbcTemplate.update(
                "INSERT INTO usuarios (username_hash, alias, jugador_id, tipo) VALUES (?, ?, ?, ?)",
                "a".repeat(64), "TestAlias", jugadorId, "ALUMNO"
        );
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuarios WHERE jugador_id = ?", Long.class, jugadorId);
        assertEquals(1, count);
    }


    @Test
    void testJugadorTableExists() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'jugador'", Long.class);
        assertNotNull(count);
        assertTrue(count > 0, "Expected 'jugador' table to exist");
    }

    @Test
    void testUsuariosTableExists() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'usuarios'", Long.class);
        assertNotNull(count);
        assertTrue(count > 0, "Expected 'usuarios' table to exist");
    }

    @Test
    void testMaquinaTableExists() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'maquina'", Long.class);
        assertNotNull(count);
        assertTrue(count > 0, "Expected 'maquina' table to exist");
    }

    @Test
    void testPartidaTableExists() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'partida'", Long.class);
        assertNotNull(count);
        assertTrue(count > 0, "Expected 'partida' table to exist");
    }

    @Test
    void testMovimientoTableExists() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'movimiento'", Long.class);
        assertNotNull(count);
        assertTrue(count > 0, "Expected 'movimiento' table to exist");
    }

    @Test
    void testPuntuacionTableExists() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'puntuacion'", Long.class);
        assertNotNull(count);
        assertTrue(count > 0, "Expected 'puntuacion' table to exist");
    }

    @Test
    void testInsertJugador() {
        int rows = jdbcTemplate.update("INSERT INTO jugador DEFAULT VALUES");
        assertEquals(1, rows);
        Long id = jdbcTemplate.queryForObject("SELECT id FROM jugador LIMIT 1", Long.class);
        assertNotNull(id);
    }

    @Test
    void testInsertUsuario() {
        Long jugadorId = insertJugadorAndGetId();
        int rows = jdbcTemplate.update(
                "INSERT INTO usuarios (username_hash, alias, jugador_id, tipo) VALUES (?, ?, ?, ?)",
                "b".repeat(64), "UsuarioTest", jugadorId, "PDI"
        );
        assertEquals(1, rows);
        String alias = jdbcTemplate.queryForObject(
                "SELECT alias FROM usuarios WHERE jugador_id = ?", String.class, jugadorId);
        assertEquals("UsuarioTest", alias);
    }

    @Test
    void testInsertMaquina() {
        Long jugadorId = insertJugadorAndGetId();
        int rows = jdbcTemplate.update(
                "INSERT INTO maquina (jugador_id, nombre_generado, algoritmo, dificultad) VALUES (?, ?, ?, ?)",
                jugadorId, "MaquinaTest", "Minimax", "DIFICIL"
        );
        assertEquals(1, rows);
        String nombre = jdbcTemplate.queryForObject(
                "SELECT nombre_generado FROM maquina WHERE jugador_id = ?", String.class, jugadorId);
        assertEquals("MaquinaTest", nombre);
    }

    @Test
    void testInsertPartida() {
        Long jugador1 = insertJugadorAndGetId();
        Long jugador2 = insertJugadorAndGetId();

        int rows = jdbcTemplate.update(
                "INSERT INTO partida (jugador1_id, jugador2_id, fecha_inicio, fecha_fin, ganador_id) VALUES (?, ?, now(), now(), ?)",
                jugador1, jugador2, jugador1
        );
        assertEquals(1, rows);
        Long ganador = jdbcTemplate.queryForObject(
                "SELECT ganador_id FROM partida WHERE jugador1_id = ? AND jugador2_id = ?", Long.class, jugador1, jugador2);
        assertEquals(jugador1, ganador);
    }

    @Test
    void testInsertMovimiento() {
        Long jugador1 = insertJugadorAndGetId();
        Long jugador2 = insertJugadorAndGetId();
        jdbcTemplate.update(
                "INSERT INTO partida (jugador1_id, jugador2_id, fecha_inicio, fecha_fin, ganador_id) VALUES (?, ?, now(), now(), ?)",
                jugador1, jugador2, jugador1
        );
        Long partidaId = jdbcTemplate.queryForObject("SELECT id FROM partida WHERE jugador1_id = ? AND jugador2_id = ?", Long.class, jugador1, jugador2);
        int rows = jdbcTemplate.update(
                "INSERT INTO movimiento (partida_id, jugador_id, coordenada_x, coordenada_y, resultado) VALUES (?, ?, ?, ?, ?)",
                partidaId, jugador1, 5, 7, "TOCADO"
        );
        assertEquals(1, rows);
        String resultado = jdbcTemplate.queryForObject(
                "SELECT resultado FROM movimiento WHERE partida_id = ? AND jugador_id = ?", String.class, partidaId, jugador1);
        assertEquals("TOCADO", resultado);
    }

    @Test
    void testInsertPuntuacion() {
        Long jugador1 = insertJugadorAndGetId();
        Long jugador2 = insertJugadorAndGetId();
        jdbcTemplate.update(
                "INSERT INTO partida (jugador1_id, jugador2_id, fecha_inicio, fecha_fin, ganador_id) VALUES (?, ?, now(), now(), ?)",
                jugador1, jugador2, jugador1
        );
        Long partidaId = jdbcTemplate.queryForObject("SELECT id FROM partida WHERE jugador1_id = ? AND jugador2_id = ?", Long.class, jugador1, jugador2);
        int rows = jdbcTemplate.update(
                "INSERT INTO puntuacion (partida_id, jugador_id, puntos) VALUES (?, ?, ?)",
                partidaId, jugador1, 100
        );
        assertEquals(1, rows);
        Integer puntos = jdbcTemplate.queryForObject(
                "SELECT puntos FROM puntuacion WHERE partida_id = ? AND jugador_id = ?", Integer.class, partidaId, jugador1);
        assertEquals(100, puntos);
    }

    private Long insertJugadorAndGetId() {
        jdbcTemplate.update("INSERT INTO jugador DEFAULT VALUES");
        return jdbcTemplate.queryForObject("SELECT id FROM jugador ORDER BY id DESC LIMIT 1", Long.class);
    }

}