package es.upm.etsisi.fis.fisfleet.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MatrixSizeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMatrixSize {
    String message() default "The matrix must be of size 10x10";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
