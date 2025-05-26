package es.upm.etsisi.fis.fisfleet.api.validation.internal;

import es.upm.etsisi.fis.fisfleet.api.validation.NotInForbiddenUsernames;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ForbiddenUsernameValidator implements ConstraintValidator<NotInForbiddenUsernames, String> {
    private final Set<String> forbiddenUsernames = new HashSet<>();

    @Override
    public void initialize(NotInForbiddenUsernames constraintAnnotation) {
        try {
            InputStream inputStream = getClass()
                    .getResourceAsStream("/forbidden-usernames.txt");

            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        forbiddenUsernames.add(line.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("No forbidden usernames list could be loaded.", e);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !forbiddenUsernames.contains(value.toLowerCase());
    }

}
