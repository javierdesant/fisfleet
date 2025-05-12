package es.upm.etsisi.fis.fisfleet.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private Long id;
    private String usernameHash;
    private String alias;
    private Long playerId;
    private Instant registrationDate;
}
