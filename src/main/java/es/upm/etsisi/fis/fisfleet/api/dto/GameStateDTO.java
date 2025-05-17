package es.upm.etsisi.fis.fisfleet.api.dto;

import es.upm.etsisi.fis.fisfleet.api.validation.ValidMatrixSize;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameStateDTO implements Serializable {
    @NotNull(message = "Player1Id cannot be empty.")
    private Long player1Id;

    @NotNull(message = "Player2Id cannot be empty.")
    private Long player2Id;

    @Min(value = 1, message = "Current turn must be either 1 or 2.")
    @Max(value = 2, message = "Current turn must be either 1 or 2.")
    private int currentTurn;

    @NotNull
    @ValidMatrixSize
    private char[][] player1Board;

    @NotNull
    @ValidMatrixSize
    private char[][] player2Board;
}
