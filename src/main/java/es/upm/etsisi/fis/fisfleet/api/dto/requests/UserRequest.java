package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import es.upm.etsisi.fis.fisfleet.api.validation.NotInForbiddenUsernames;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequest {

    @NotBlank(message = "The usernameHash cannot be empty, blank, or null.")
    private String usernameHash;

    @NotBlank(message = "The alias cannot be empty, blank, or null.")
    @Size(min = 3, max = 10, message = "Alias length must be between 3 and 10 characters.")
    @Pattern(
            regexp = "^[a-zA-Z0-9]+$",
            message = "The alias can only contain letters and numbers."
    )
    @NotInForbiddenUsernames
    private String alias;

    // FIXME: this has to be generated for sure...
    @NotNull(message = "The playerId cannot be empty.")     //  still no idea about how to do it tho
    private Long playerId;
}
