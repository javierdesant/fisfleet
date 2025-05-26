package es.upm.etsisi.fis.fisfleet.infrastructure.exceptions;

public class TurnTimeoutException extends RuntimeException {

    public TurnTimeoutException() {
        super("El jugador ha excedido el tiempo permitido para su turno.");
    }

    public TurnTimeoutException(Throwable cause) {
        super("El jugador ha excedido el tiempo permitido para su turno.", cause);
    }
}
