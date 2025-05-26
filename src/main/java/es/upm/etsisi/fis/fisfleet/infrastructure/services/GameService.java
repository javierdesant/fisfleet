package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.model.Partida;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public interface GameService {

    Partida getPartidaOrThrow(UUID gameId);

    boolean isPlayerTurn(Partida partida, Long playerId);

    HashMap<String, Object> applyTurn(Partida partida);

    void handlePartidaState(Partida partida, UUID gameId);

    void sendPartidaView(Partida partida, Long lastPlayerId, HashMap<String, Object> result, Set<WebSocketSession> sessions);

    void sendRevealedRow(Long playerId, Partida partida, Set<WebSocketSession> sessions);
}

//    should I add GameStateDTO createPvPMatch(); ?
