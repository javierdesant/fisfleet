package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import es.upm.etsisi.fis.fisfleet.api.validation.NotInForbiddenUsernames;
import es.upm.etsisi.fis.fisfleet.api.validation.ValidUPMEmail;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "The username cannot be empty, blank, or null.")
    @ValidUPMEmail
    private String username;

    @NotBlank(message = "The alias cannot be empty, blank, or null.")
    @Size(min = 3, max = 10, message = "Alias length must be between 3 and 10 characters.")
    @Pattern(
            regexp = "^[a-zA-Z0-9]+$",
            message = "The alias can only contain letters and numbers."
    )
    @NotInForbiddenUsernames
    private String alias;

    @NotBlank(message = "The password cannot be empty.")
    private String password;
}
