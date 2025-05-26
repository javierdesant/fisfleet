package es.upm.etsisi.fis.fisfleet.api.validation.internal;

import es.upm.etsisi.fis.fisfleet.api.validation.ValidUPMEmail;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUPMEmailValidator implements ConstraintValidator<ValidUPMEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;    // @NotBlank should handle this
        }

        return LDAPAuthenticator.accountBelongsToUPM(email);
    }
}