package es.upm.etsisi.fis.fisfleet.api.dto;

import java.io.Serializable;

public class GameViewDTO implements Serializable {

    private Long gameId;

    private char[][] ownBoard;

    private char[][] enemyBoardMasked;

    private boolean isYourTurn;

    private boolean canUseAbility;
}
