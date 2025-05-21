package es.upm.etsisi.fis.fisfleet.infrastructure.exceptions; // Usa el paquete apropiado para tu proyecto

public class RequestCanceledException extends RuntimeException {
    public RequestCanceledException() {
        super("The request expired.");
    }
}