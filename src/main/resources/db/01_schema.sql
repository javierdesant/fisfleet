DROP TABLE IF EXISTS movimiento CASCADE;
DROP TABLE IF EXISTS puntuacion CASCADE;
DROP TABLE IF EXISTS partida CASCADE;
DROP TABLE IF EXISTS maquina CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS jugador CASCADE;

CREATE TABLE jugador
(
    id BIGSERIAL PRIMARY KEY
);

CREATE TABLE usuarios
(
    id             BIGSERIAL PRIMARY KEY,
    username_hash  CHAR(64) UNIQUE NOT NULL,
    alias          VARCHAR(50)     NOT NULL,
    jugador_id     BIGINT UNIQUE REFERENCES jugador (id),
    fecha_registro TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE maquina
(
    id              BIGSERIAL PRIMARY KEY,
    jugador_id      BIGINT UNIQUE REFERENCES jugador (id),
    nombre_generado VARCHAR(50) UNIQUE NOT NULL,
    algoritmo       VARCHAR(50)        NOT NULL,
    dificultad      VARCHAR(20)        NOT NULL CHECK (dificultad IN ('FACIL', 'MEDIO', 'DIFICIL'))
);

CREATE TABLE partida
(
    id           BIGSERIAL PRIMARY KEY,
    jugador1_id  BIGINT    NOT NULL REFERENCES jugador (id),
    jugador2_id  BIGINT    NOT NULL REFERENCES jugador (id),
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_fin    TIMESTAMP NOT NULL,
    ganador_id   BIGINT    NOT NULL REFERENCES jugador (id),
    CHECK (jugador1_id != jugador2_id),
    CHECK (ganador_id IN (jugador1_id, jugador2_id))
);

CREATE TABLE movimiento
(
    partida_id   BIGINT      NOT NULL REFERENCES partida (id),
    jugador_id   BIGINT      NOT NULL REFERENCES jugador (id),
    coordenada_x INT         NOT NULL CHECK (coordenada_x BETWEEN 0 AND 9),
    coordenada_y INT         NOT NULL CHECK (coordenada_y BETWEEN 0 AND 9),
    resultado    VARCHAR(10) NOT NULL CHECK (resultado IN ('HUNDIDO', 'TOCADO', 'AGUA')),
    fecha        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (partida_id, jugador_id, fecha)
);

CREATE TABLE puntuacion
(
    partida_id          BIGINT    NOT NULL REFERENCES partida (id),
    jugador_id          BIGINT    NOT NULL REFERENCES jugador (id),
    PRIMARY KEY (partida_id, jugador_id),
    puntos              INT       NOT NULL CHECK (puntos >= 0) DEFAULT 0,
    fecha_actualizacion TIMESTAMP NOT NULL                     DEFAULT CURRENT_TIMESTAMP
);
