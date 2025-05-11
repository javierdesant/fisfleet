-- Eliminar tablas si existen
DROP TABLE IF EXISTS movimiento CASCADE;
DROP TABLE IF EXISTS puntuacion CASCADE;
DROP TABLE IF EXISTS partida CASCADE;
DROP TABLE IF EXISTS maquina CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS jugador CASCADE;

-- Tabla base para todos los jugadores
CREATE TABLE jugador (
                         id SERIAL PRIMARY KEY
);

-- Tabla de usuarios humanos
CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          username_hash CHAR(64) UNIQUE NOT NULL,
                          alias VARCHAR(50) NOT NULL,
                          jugador_id INT UNIQUE REFERENCES jugador(id),
                          fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          es_admin BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabla de máquinas
CREATE TABLE maquina (
                         id SERIAL PRIMARY KEY,
                         jugador_id INT UNIQUE REFERENCES jugador(id),
                         nombre_generado VARCHAR(50) UNIQUE NOT NULL,
                         algoritmo VARCHAR(50) NOT NULL
);

-- Resto de tablas
CREATE TABLE partida (
                         id SERIAL PRIMARY KEY,
                         jugador1_id INT NOT NULL REFERENCES jugador(id),
                         jugador2_id INT NOT NULL REFERENCES jugador(id),
                         fecha_inicio TIMESTAMP NOT NULL,
                         fecha_fin TIMESTAMP NOT NULL,
                         ganador_id INT NOT NULL REFERENCES jugador(id),
                         CHECK (jugador1_id != jugador2_id),
                         CHECK (ganador_id IN (jugador1_id, jugador2_id))
);

CREATE TABLE movimiento (
                            id SERIAL PRIMARY KEY,
                            partida_id INT NOT NULL REFERENCES partida(id),
                            jugador_id INT NOT NULL REFERENCES jugador(id),
                            coordenada_x INT NOT NULL CHECK (coordenada_x BETWEEN 0 AND 9),
                            coordenada_y INT NOT NULL CHECK (coordenada_y BETWEEN 0 AND 9),
                            resultado VARCHAR(10) NOT NULL CHECK (resultado IN ('HUNDIDO', 'TOCADO', 'AGUA')),
                            fecha TIMESTAMP NOT NULL,
                            UNIQUE (partida_id, jugador_id, coordenada_x, coordenada_y)
);

CREATE TABLE puntuacion (
                            jugador_id INT PRIMARY KEY REFERENCES jugador(id),
                            puntos INT NOT NULL CHECK (puntos >= 0) DEFAULT 0,
                            fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
