package es.upm.etsisi.fis.fisfleet.api.dto.requests;

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
public class MachineRequest {

    @NotNull(message = "The playerId cannot be empty.")
    private Long playerId;

    @NotNull(message = "The generated name cannot be empty.")
    @Pattern(
            regexp = "^Maquina_(FACIL|MEDIO|DIFICIL)_\\d+$",
            message = "The generated name must follow the format 'Maquina_<difficulty>_<timestamp>'."
    )
    private String generatedName;

    @NotNull(message = "The difficulty cannot be empty.")
    @Pattern(
            regexp = "^(FACIL|MEDIO|DIFICIL)$",
            message = "The difficulty must be FACIL, MEDIO or DIFICIL."
    )
    private String difficulty;
}