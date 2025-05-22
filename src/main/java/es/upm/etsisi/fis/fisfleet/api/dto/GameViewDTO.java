package es.upm.etsisi.fis.fisfleet.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameViewDTO implements Serializable {

    private Long gameId;

    private char[][] ownBoard;

    private char[][] enemyBoardMasked;

    private boolean isYourTurn;

    private SpecialAbility availableAbility;
}
