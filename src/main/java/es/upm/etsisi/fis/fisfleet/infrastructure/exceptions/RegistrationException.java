package es.upm.etsisi.fis.fisfleet.infrastructure.exceptions;

public class RegistrationException extends RuntimeException {

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }

    public RegistrationException() {
        super("Error during registration");
    }
}