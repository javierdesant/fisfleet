package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import es.upm.etsisi.fis.fisfleet.api.validation.ValidWinnerId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ValidWinnerId
public class GameResultRequest {

    @NotNull(message = "Player1Id cannot be empty.")
    private Long player1Id;

    @NotNull(message = "Player2Id cannot be empty.")
    private Long player2Id;

    @NotNull(message = "The start date cannot be empty.")
    private Instant startDate;

    @NotNull(message = "The end date cannot be empty.")
    private Instant endDate;

    @NotNull(message = "The winnerId cannot be empty.")
    private Long winnerId;
}