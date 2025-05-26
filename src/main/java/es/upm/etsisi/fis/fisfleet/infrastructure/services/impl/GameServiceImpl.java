package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.GameViewDTO;
import es.upm.etsisi.fis.fisfleet.api.dto.SpecialAbility;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.GameCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.GameService;
import es.upm.etsisi.fis.model.Nave;
import es.upm.etsisi.fis.model.Partida;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameCacheService gameCacheService;
    private final GameResultService gameResultService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Partida getPartidaOrThrow(Long playerId) {
        return gameCacheService.getPartida(playerId)
                .orElseThrow(() -> new IllegalStateException("Partida not found"));
    }

    @Override
    public boolean isPlayerTurn(Partida partida, Long playerId) {
        return partida.getTurnoName().equals(playerId.toString());
    }

    @Override
    public HashMap<String, Object> applyTurn(Partida partida) {
        return partida.aplicaTurno();
    }

    @Override
    public void handlePartidaState(Partida partida, Long playerId) {
        if (partida.fin()) {
            partida.setfin();
            gameResultService.persistFinished(partida);
            gameCacheService.removePartida(playerId);
        } else {
            partida.swapTurn();
            gameCacheService.savePartida(playerId, partida);
        }
    }

    @Override
    public void sendPartidaView(Partida partida, Long lastPlayerId, HashMap<String, Object> result, Set<WebSocketSession> sessions) {
        Long opponentId = Long.valueOf(partida.getTurnoName());
        if (opponentId.equals(lastPlayerId)) return;

        Nave nave = (Nave) result.get("Nave");
        GameViewDTO view = GameViewDTO.builder()
                .availableAbility(SpecialAbility.fromShipName(nave.getName()))
                .ownBoard(partida.getTableros().get(0))
                .enemyBoardMasked(partida.getTableros().get(1))
                .build();

        sendViewToPlayer(opponentId, view, sessions);
    }

    private void sendViewToPlayer(Long playerId, GameViewDTO view, Set<WebSocketSession> sessions) {
        Optional<String> sessionIdOpt = gameCacheService.getPlayerSession(playerId);
        if (sessionIdOpt.isEmpty()) return;

        String sessionId = sessionIdOpt.get();
        sessions.stream()
                .filter(s -> s.getId().equals(sessionId))
                .findFirst()
                .ifPresent(session -> sendMessage(session, view));
    }

    private void sendMessage(WebSocketSession session, GameViewDTO view) {
        try {
            String payload = objectMapper.writeValueAsString(view);
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            log.error("Error sending view to player {}: {}", session.getId(), e.getMessage(), e);
        }
    }
}
