package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
import es.upm.etsisi.fis.model.TDificultad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GameWebSocketController {

    private final GameService gameService;
    private final GameCacheService gameCacheService;
//    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/game/new/pve")
    @SendToUser("/queue/new")
    public GameViewDTO newPvEMatch(TDificultad difficulty) {
        return gameService.createPvEMatch(difficulty);
    }

    @MessageMapping("/game/move")
    public void processMove(MoveRequest moveRequest, Principal principal) {
        String playerId = principal.getName(); // Comes from authenticated JWT
        Long gameId = moveRequest.getGameId();

//        TODO: Register the move
//         gameService.processMove(gameId, playerId, moveRequest);

//        TODO: Get the list of players in the game
//         List<Long> players = gameService.getPlayers(gameId);

        // Send personalized view to each player
//        for (Long player : players) {
//            GameStateDTO view = gameService.getViewForPlayer(gameId, player);
//            messagingTemplate.convertAndSendToUser(
//                    player, // username from Principal
//                    "/queue/game/" + gameId,
//                    view
//            );
//        }
    }

}
