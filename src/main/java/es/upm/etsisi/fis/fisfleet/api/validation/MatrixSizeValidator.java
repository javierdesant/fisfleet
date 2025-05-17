package es.upm.etsisi.fis.fisfleet.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatrixSizeValidator implements ConstraintValidator<ValidMatrixSize, char[][]> {

    @Override
    public boolean isValid(char[][] matrix, ConstraintValidatorContext context) {
        if (matrix == null || matrix.length != 10) {
            return false;
        }

        for (char[] chars : matrix) {
            if (!this.isValidRow(chars)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidRow(char[] row) {
        return row != null && row.length == 10;
    }
}