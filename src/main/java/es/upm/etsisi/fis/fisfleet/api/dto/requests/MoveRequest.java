package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MoveRequest {

    @NotNull(message = "The gameId cannot be empty.")
    private Long gameId;

    @NotNull(message = "The playerId cannot be empty.")
    private Long playerId;

    // Validate that the coordinates are within the bounds of a 10x10 board
    @NotNull(message = "The X coordinate cannot be empty.")
    @Min(value = 0, message = "The X coordinate cannot be less than 0.")
    @Max(value = 9, message = "The X coordinate cannot be greater than 9.")
    private Integer coordinateX;

    @NotNull(message = "The Y coordinate cannot be empty.")
    @Min(value = 0, message = "The Y coordinate cannot be less than 0.")
    @Max(value = 9, message = "The Y coordinate cannot be greater than 9.")
    private Integer coordinateY;

    @NotNull(message = "The result cannot be empty.")
    @Pattern(
            regexp = "^(HUNDIDO|TOCADO|AGUA)$",
            message = "The result must be HUNDIDO, TOCADO, or AGUA."
    )
    private String result;
}