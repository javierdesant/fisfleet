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
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameStateDTO implements Serializable {
    @NotNull(message = "GameId cannot be empty.")
    private Long gameId;

    @NotNull(message = "Player1Id cannot be empty.")
    private Long player1Id;

    @NotNull(message = "Player2Id cannot be empty.")
    private Long player2Id;

    @NotNull(message = "Player 1 board cannot be null.")
    @ValidMatrixSize(message = "Player 1 board must be of size 10x10.")
    private char[][] player1Board;

    @NotNull(message = "Player 2 board cannot be null.")
    @ValidMatrixSize(message = "Player 2 board must be of size 10x10.")
    private char[][] player2Board;

    @NotNull(message = "Start date cannot be null.")
    private Instant startDate;

    @Min(value = 1, message = "Current turn must be either 1 or 2.")
    @Max(value = 2, message = "Current turn must be either 1 or 2.")
    private int turnOfPlayer;

    @Builder.Default
    private SpecialAbility canUseAbility = SpecialAbility.NONE;
}
