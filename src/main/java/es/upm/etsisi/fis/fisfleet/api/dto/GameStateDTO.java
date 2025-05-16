package es.upm.etsisi.fis.fisfleet.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameStateDTO implements Serializable {
    private Long player1Id;
    private Long player2Id;
    private String currentTurn;
    private char[][] player1Board;
    private char[][] player2Board;
    private String winner;
}
