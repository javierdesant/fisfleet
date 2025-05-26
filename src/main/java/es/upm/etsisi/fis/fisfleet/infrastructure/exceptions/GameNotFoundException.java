package es.upm.etsisi.fis.fisfleet.infrastructure.exceptions;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {
  public GameNotFoundException(UUID gameId) {
    super("Game not found with ID: " + gameId);
  }
}
