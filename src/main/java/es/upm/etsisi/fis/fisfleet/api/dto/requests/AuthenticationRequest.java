package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import es.upm.etsisi.fis.fisfleet.api.validation.ValidUPMEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationRequest implements Serializable {
    @NotBlank(message = "The username cannot be empty, blank or null.")
    @ValidUPMEmail
    private String username;    // email in this case

    @NotBlank(message = "The password cannot be empty.")
    private String password;
}
