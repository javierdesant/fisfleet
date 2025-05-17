package es.upm.etsisi.fis.fisfleet.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Email
@Constraint(validatedBy = ValidUPMEmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUPMEmail {
    String message() default "The email does not belong to a valid UPM domain.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
