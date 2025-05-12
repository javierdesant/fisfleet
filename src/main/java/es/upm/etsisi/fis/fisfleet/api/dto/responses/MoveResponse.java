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
public class MoveResponse {
    private Long gameId;
    private Long playerId;
    private Instant date;
    private Integer coordinateX;
    private Integer coordinateY;
    private String result;
}
