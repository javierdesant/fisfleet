package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import es.upm.etsisi.fis.fisfleet.api.dto.SpecialAbility;
import jakarta.validation.constraints.Max;
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
public class MoveRequest {

    @NotNull(message = "The X coordinate cannot be empty.")
    @Min(value = 0, message = "The X coordinate cannot be less than 0.")
    @Max(value = 9, message = "The X coordinate cannot be greater than 9.")
    private Integer coordinateX;

    @NotNull(message = "The Y coordinate cannot be empty.")
    @Min(value = 0, message = "The Y coordinate cannot be less than 0.")
    @Max(value = 9, message = "The Y coordinate cannot be greater than 9.")
    private Integer coordinateY;

    @Builder.Default
    private boolean specialAbility = false;
}
