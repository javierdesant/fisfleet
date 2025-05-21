package es.upm.etsisi.fis.fisfleet.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import servidor.Autenticacion;

public class ValidUPMEmailValidator implements ConstraintValidator<ValidUPMEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;    // @NotBlank should handle this
        }

        return Autenticacion.existeCuentaUPMStatic(email);
    }
}