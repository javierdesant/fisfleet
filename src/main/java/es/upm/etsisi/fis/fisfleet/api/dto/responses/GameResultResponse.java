package es.upm.etsisi.fis.fisfleet.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameResultResponse {
    private Long id;
    private Long player1Id;
    private Long player2Id;
    private Instant startDate;
    private Instant endDate;
    private Long winnerId;
    private List<MoveResponse> moves;
}
