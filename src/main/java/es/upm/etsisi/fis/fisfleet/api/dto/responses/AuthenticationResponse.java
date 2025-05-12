package es.upm.etsisi.fis.fisfleet.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationResponse implements Serializable {
    private String jwt;
}
