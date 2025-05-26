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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameCacheService gameCacheService;
    private final GameResultService gameResultService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Partida getPartidaOrThrow(UUID gameId) {
        return gameCacheService.getPartida(gameId)
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
    public void handlePartidaState(Partida partida, UUID gameId) {
        if (partida.fin()) {
            partida.setfin();
            gameResultService.persistFinished(partida);
            gameCacheService.removePartida(gameId);
        } else {
            partida.swapTurn();
            gameCacheService.savePartida(gameId, partida);
        }
    }

    @Override
    public void sendPartidaView(Partida partida, Long lastPlayerId, HashMap<String, Object> result, Set<WebSocketSession> sessions) {
        Long opponentId = Long.valueOf(partida.getTurnoName());
        if (opponentId.equals(lastPlayerId)) {
            log.debug("Not sending view to opponent {} as they are the last player {}", opponentId, lastPlayerId);
            return;
        }

        Nave nave = (Nave) result.get("Nave");
        GameViewDTO view = GameViewDTO.builder()
                .availableAbility(SpecialAbility.fromShipName(nave.getName()))
                .ownBoard(partida.getTableros().get(0))
                .enemyBoardMasked(partida.getTableros().get(1))
                .build();

        this.sendViewToPlayer(opponentId, view, sessions);
    }

    private void sendViewToPlayer(Long playerId, GameViewDTO view, Set<WebSocketSession> sessions) {
        Optional<String> sessionIdOpt = gameCacheService.getPlayerSession(playerId);
        if (sessionIdOpt.isEmpty()) {
            log.warn("No session found for player ID {}", playerId);
            return;
        }

        String sessionId = sessionIdOpt.get();
        sessions.stream()
                .filter(s -> s.getId().equals(sessionId))
                .findFirst()
                .ifPresent(session -> this.sendMessage(session, view));
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
