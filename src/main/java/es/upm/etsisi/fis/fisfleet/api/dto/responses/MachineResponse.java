package es.upm.etsisi.fis.fisfleet.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MachineResponse {
    private Long id;
    private Long playerId;
    private String generatedName;
    private String algorithm;
    private String difficulty;
}
