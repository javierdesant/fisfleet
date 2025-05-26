package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.model.Partida;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Set;

public interface GameService {

    Partida getPartidaOrThrow(Long playerId);

    boolean isPlayerTurn(Partida partida, Long playerId);

    HashMap<String, Object> applyTurn(Partida partida);

    void handlePartidaState(Partida partida, Long playerId);

    /**
     * TODO: Process special abilities for human player
     * FIXME: Integrate with HumanPlayer and MoveRequestWaiter classes
     */
    void sendPartidaView(Partida partida, Long lastPlayerId, HashMap<String, Object> result, Set<WebSocketSession> sessions);
}

//    should I add GameStateDTO createPvPMatch(); ?
