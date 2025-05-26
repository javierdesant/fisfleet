package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.fisfleet.domain.entities.ScoreEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreRepository scoreRepository;

    @PreAuthorize("hasAuthority('VIEW_ALL_SCORES')")
    @GetMapping("/all")
    public ResponseEntity<List<ScoreEntity>> getAllScoresDesc() {
        List<ScoreEntity> scores = scoreRepository.findAllByOrderByPointsDesc();
        return ResponseEntity.ok(scores);
    }
}
