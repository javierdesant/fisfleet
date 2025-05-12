CREATE OR REPLACE FUNCTION crear_jugador_automatico()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
DECLARE
    nuevo_jugador_id INT;
BEGIN
    INSERT INTO jugador DEFAULT VALUES RETURNING id INTO nuevo_jugador_id;
    NEW.jugador_id := nuevo_jugador_id;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trigger_crear_jugador_usuario
    BEFORE INSERT
    ON usuarios
    FOR EACH ROW
    WHEN (NEW.jugador_id IS NULL)
EXECUTE FUNCTION crear_jugador_automatico();

CREATE TRIGGER trigger_crear_jugador_maquina
    BEFORE INSERT
    ON maquina
    FOR EACH ROW
    WHEN (NEW.jugador_id IS NULL)
EXECUTE FUNCTION crear_jugador_automatico();