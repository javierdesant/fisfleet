package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.core.MoveRequestWaiter;
import es.upm.etsisi.fis.fisfleet.infrastructure.exceptions.NotPlayerTurnException;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @MessageMapping("/game/{gameId}/move")
    public void handleMove(@DestinationVariable UUID gameId,
                           @Payload MoveRequest moveRequest,
                           Principal principal) {

        Long userId = Long.valueOf(principal.getName());

        var partida = gameService.getPartidaOrThrow(gameId);
        if (!gameService.isPlayerTurn(partida, userId)) {
            log.warn("Jugador {} intentó mover fuera de turno en la partida {}", userId, gameId);
            throw new NotPlayerTurnException(userId, gameId);
        }

        log.info("Recibido movimiento de jugador {}: ({}, {})", userId, moveRequest.getCoordinateX(), moveRequest.getCoordinateY());
        MoveRequestWaiter.submitMove(userId, moveRequest);
    }
}
