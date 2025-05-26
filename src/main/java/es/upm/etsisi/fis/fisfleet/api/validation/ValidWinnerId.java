package es.upm.etsisi.fis.fisfleet.api.validation;

import es.upm.etsisi.fis.fisfleet.api.validation.internal.WinnerIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WinnerIdValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWinnerId {
    String message() default "The winnerId must match either player1Id or player2Id.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
