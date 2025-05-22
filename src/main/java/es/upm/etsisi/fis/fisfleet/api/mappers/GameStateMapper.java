package es.upm.etsisi.fis.fisfleet.api.mappers;

import es.upm.etsisi.fis.fisfleet.api.dto.GameStateDTO;
import es.upm.etsisi.fis.model.IPartida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GameStateMapper {

    @Mapping(source = "turnoName", target = "player1Id", qualifiedByName = "stringToLongOrNull")
    @Mapping(source = "turnoOpuestoName", target = "player2Id", qualifiedByName = "stringToLongOrNull")
    @Mapping(source = "turnoName", target = "turnOfPlayer")
    @Mapping(expression = "java(match.getTableros().get(0))", target = "player1Board")
    @Mapping(expression = "java(match.getTableros().get(1))", target = "player2Board")
    GameStateDTO mapToGameStateDTO(IPartida match);

    @Named("stringToLongOrNull")
    default Long stringToLongOrNull(String value) {
        return value != null ? Long.parseLong(value) : null;
    }
}
