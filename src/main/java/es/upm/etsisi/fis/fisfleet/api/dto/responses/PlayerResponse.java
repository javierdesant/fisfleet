package es.upm.etsisi.fis.fisfleet.api.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlayerResponse {
    private Long id;
    private List<GameResultResponse> gamesAsPlayer1;
    private List<GameResultResponse> gamesAsPlayer2;
    private List<GameResultResponse> gamesWon;
}