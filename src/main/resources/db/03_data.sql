INSERT INTO jugador (id) VALUES (1000);

INSERT INTO usuarios (
    username_hash,
    alias,
    jugador_id,
    tipo
) VALUES (
    '102140f918f1de0baffc2e08ddfa5b7fc2900fc4d947a09023098b229ccacc88', -- hash de admin@upm.es
    'Administrador',
    1000,
    'PAS'
);