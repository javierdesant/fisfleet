package es.upm.etsisi.fis.fisfleet.infrastructure.exceptions;

import java.util.UUID;

public class NotPlayerTurnException extends RuntimeException {
  public NotPlayerTurnException(Long userId, UUID gameId) {
    super(String.format("It's not player %s turn in game %s", userId, gameId));
  }
}
