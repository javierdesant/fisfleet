package es.upm.etsisi.fis.fisfleet.api.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationRequest implements Serializable {
    private String username;    // email in this case
    private String password;
}
