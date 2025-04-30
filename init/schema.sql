-- Esquema PostgreSQL para el proyecto fisfleet (Battleship)

-- 0. Tipos ENUM personalizados
CREATE TYPE player_difficulty AS ENUM ('FÁCIL','NORMAL','DIFÍCIL');

-- 1. Jugadores (usuarios, máquinas y administradores)
CREATE TABLE players (
                         player_id      SERIAL PRIMARY KEY,
                         username       VARCHAR(10) NOT NULL UNIQUE,
                         email          VARCHAR(255),
                         password_hash  VARCHAR(255),
                         is_admin       BOOLEAN NOT NULL DEFAULT FALSE,
                         difficulty     player_difficulty,
                         created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT chk_username_length CHECK (char_length(username) BETWEEN 3 AND 10)
);

-- 2. Tipos de barco y habilidades
CREATE TABLE ship_types (
                            type_id   SERIAL PRIMARY KEY,
                            name      VARCHAR(20) NOT NULL UNIQUE,
                            size      SMALLINT NOT NULL,
                            ability   VARCHAR(50) NOT NULL,
                            max_uses  SMALLINT NOT NULL
);

-- Ejemplo de inserción de ship_types:
-- INSERT INTO ship_types (name, size, ability, max_uses) VALUES
--   ('Portaviones', 4, 'Contraataque', 2147483647),
--   ('Acorazado',   3, 'Artillería',   2),
--   ('Submarino',   3, 'Resurrección', 1),
--   ('Patrullero',  2, 'Radar',        1);

-- 3. Partidas
CREATE TABLE games (
                       game_id       SERIAL PRIMARY KEY,
                       player1_id    INTEGER NOT NULL REFERENCES players(player_id),
                       player2_id    INTEGER NOT NULL REFERENCES players(player_id),
                       start_time    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       duration_secs INTEGER,
                       finished_at   TIMESTAMP,
                       winner_id     INTEGER REFERENCES players(player_id)
);

-- 4. Barcos en cada partida
CREATE TABLE ships (
                       ship_id    SERIAL PRIMARY KEY,
                       game_id    INTEGER NOT NULL REFERENCES games(game_id),
                       owner_id   INTEGER NOT NULL REFERENCES players(player_id),
                       type_id    INTEGER NOT NULL REFERENCES ship_types(type_id),
                       uses_left  SMALLINT NOT NULL,
                       is_sunk    BOOLEAN NOT NULL DEFAULT FALSE
);

-- 5. Posiciones de cada barco
CREATE TABLE ship_cells (
                            ship_id   INTEGER NOT NULL REFERENCES ships(ship_id),
                            row_idx   SMALLINT NOT NULL CHECK (row_idx BETWEEN 0 AND 9),
                            col_idx   SMALLINT NOT NULL CHECK (col_idx BETWEEN 0 AND 9),
                            is_hit    BOOLEAN NOT NULL DEFAULT FALSE,
                            PRIMARY KEY (ship_id, row_idx, col_idx)
);

-- 6. Ataques realizados
CREATE TABLE attacks (
                         attack_id   SERIAL PRIMARY KEY,
                         game_id     INTEGER NOT NULL REFERENCES games(game_id),
                         attacker_id INTEGER NOT NULL REFERENCES players(player_id),
                         row_idx     SMALLINT NOT NULL CHECK (row_idx BETWEEN 0 AND 9),
                         col_idx     SMALLINT NOT NULL CHECK (col_idx BETWEEN 0 AND 9),
                         is_hit      BOOLEAN NOT NULL,
                         attack_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 7. Puntuaciones por partida y jugador
CREATE TABLE scores (
                        score_id  SERIAL PRIMARY KEY,
                        game_id   INTEGER NOT NULL REFERENCES games(game_id),
                        player_id INTEGER NOT NULL REFERENCES players(player_id),
                        points    INTEGER NOT NULL
);

-- Índices para consultas frecuentes
CREATE INDEX idx_scores_points ON scores (points DESC);
CREATE INDEX idx_attacks_game_attacker ON attacks (game_id, attacker_id);

-- 8. Usuario adicional 'admin'
CREATE ROLE admin WITH LOGIN PASSWORD ${ADMIN_PASS};
ALTER ROLE admin WITH SUPERUSER;
