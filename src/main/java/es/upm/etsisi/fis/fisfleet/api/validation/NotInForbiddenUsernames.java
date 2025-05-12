package es.upm.etsisi.fis.fisfleet.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ForbiddenUsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInForbiddenUsernames {

    String message() default "The alias is in the list of forbidden names.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}