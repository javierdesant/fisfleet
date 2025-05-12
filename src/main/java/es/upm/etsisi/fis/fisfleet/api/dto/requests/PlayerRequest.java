package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlayerRequest {

    @NotNull(message = "The userId cannot be empty.")
    private Long userId;
    // FIXME: weird interaction with machine and user req
}
