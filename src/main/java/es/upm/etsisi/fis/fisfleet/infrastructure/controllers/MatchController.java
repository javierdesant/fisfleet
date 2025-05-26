package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.fisfleet.infrastructure.services.MatchmakingService;
import es.upm.etsisi.fis.model.TDificultad;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchmakingService matchmakingService;

    @PostMapping("/pve")
    public ResponseEntity<String> startPveMatch(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam(name = "difficulty", defaultValue = "NORMAL") TDificultad difficulty) {
        Long playerId = Long.parseLong(userDetails.getUsername());
        String partidaId = matchmakingService.createPveMatch(playerId, difficulty);
        return ResponseEntity.ok(partidaId);
    }

    @PostMapping("/pvp")
    public ResponseEntity<String> queueForPvp(@AuthenticationPrincipal UserDetails userDetails) {
        Long playerId = Long.parseLong(userDetails.getUsername());
        String partidaId = matchmakingService.queuePvpMatch(playerId);
        return ResponseEntity.ok(partidaId);
    }
}