package es.upm.etsisi.fis.fisfleet.api.validation;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.GameResultRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WinnerIdValidator implements ConstraintValidator<ValidWinnerId, GameResultRequest> {

    @Override
    public boolean isValid(GameResultRequest request, ConstraintValidatorContext context) {
        if (request.getWinnerId() == null || request.getPlayer1Id() == null || request.getPlayer2Id() == null) {
            return true;
        }

        boolean valid = request.getWinnerId().equals(request.getPlayer1Id()) ||
                request.getWinnerId().equals(request.getPlayer2Id());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The winnerId must match either player1Id or player2Id.")
                    .addPropertyNode("winnerId")
                    .addConstraintViolation();
        }

        return valid;
    }
}
