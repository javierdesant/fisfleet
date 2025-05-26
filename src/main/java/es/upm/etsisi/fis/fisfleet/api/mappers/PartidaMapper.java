package es.upm.etsisi.fis.fisfleet.api.mappers;

import es.upm.etsisi.fis.fisfleet.domain.entities.GameResultEntity;
import es.upm.etsisi.fis.fisfleet.domain.entities.PlayerEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.PlayerRepository;
import es.upm.etsisi.fis.model.Partida;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = PlayerEntityResolver.class)
public interface PartidaMapper {

    @Mapping(target = "player1", source = "partida", qualifiedByName = "player1FromPartida")
    @Mapping(target = "player2", source = "partida", qualifiedByName = "player2FromPartida")
    @Mapping(target = "winner", source = "partida", qualifiedByName = "winnerFromPartida")
    @Mapping(target = "startDate", source = "inicio", qualifiedByName = "longToInstant")
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "moves", ignore = true)
    @Mapping(target = "id", ignore = true)
    GameResultEntity partidaToGameResultEntity(Partida partida);

    @Named("longToInstant")
    static java.time.Instant longToInstant(Long value) {
        return value == null ? null : java.time.Instant.ofEpochMilli(value);
    }
}

@AllArgsConstructor
@Component
class PlayerEntityResolver {

    private PlayerRepository playerRepository;

    @Named("player1FromPartida")
    public PlayerEntity player1FromPartida(Partida partida) {
        Long playerId = Long.valueOf(partida.getTurnoName());
        return playerRepository.findById(playerId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Named("player2FromPartida")
    public PlayerEntity player2FromPartida(Partida partida) {
        Long playerId = Long.valueOf(partida.getTurnoOpuestoName());
        return playerRepository.findById(playerId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Named("winnerFromPartida")
    public PlayerEntity winnerFromPartida(Partida partida) {
        return this.player1FromPartida(partida);
    }
}

