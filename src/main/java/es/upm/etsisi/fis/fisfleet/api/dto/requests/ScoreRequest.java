package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScoreRequest {

    @NotNull(message = "The gameId cannot be empty.")
    private Long gameId;

    @NotNull(message = "The playerId cannot be empty.")
    private Long playerId;

    @NotNull(message = "The points cannot be empty.")
    @Min(value = 0, message = "The points cannot be negative.")
    private Integer points;
}