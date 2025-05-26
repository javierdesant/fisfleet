package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.GameResultRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.impl.GameResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameResultController {

    private final GameResultService gameResultService;
    private final GameResultRepository gameResultRepository;

    @PreAuthorize("hasAuthority('VIEW_ALL_SCORES')")
    @GetMapping("/{id}")
    public ResponseEntity<GameResultEntity> getGameById(@PathVariable Long id) {
        GameResultEntity game = gameResultService.read(id);
        return ResponseEntity.ok(game);
    }

    @PreAuthorize("hasAuthority('ACCESS_ADMIN_PANEL')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameById(@PathVariable Long id) {
        gameResultService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Top 10 puntuaciones de un jugador descendente
    @PreAuthorize("hasAuthority('VIEW_SCOREBOARD')")
    @GetMapping("/top10/{playerId}")
    public ResponseEntity<List<GameResultEntity>> getTop10ByPlayer(@PathVariable Long playerId) {
        List<GameResultEntity> top10 = gameResultRepository.findTop10ByPlayer1IdOrderByWinner_ScoresDesc(playerId);
        return ResponseEntity.ok(top10);
    }

    // Todos los resultados de una partida, ordenados por fecha de inicio ascendente
    @PreAuthorize("hasAuthority('VIEW_SCOREBOARD')")
    @GetMapping("/game/{gameId}/results")
    public ResponseEntity<List<GameResultEntity>> getResultsByGame(@PathVariable Long gameId) {
        List<GameResultEntity> results = gameResultRepository.findByIdOrderByStartDateAsc(gameId);
        return ResponseEntity.ok(results);
    }
}
