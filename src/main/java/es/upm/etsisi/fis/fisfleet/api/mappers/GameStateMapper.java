//package es.upm.etsisi.fis.fisfleet.api.mappers;
//
//import es.upm.etsisi.fis.fisfleet.api.dto.GameStateDTO;
//import es.upm.etsisi.fis.model.IPartida;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface GameStateMapper {
//
//    @Mapping(source = "turnoName", target = "player1Id", qualifiedByName = "stringToLongOrNull")
//    @Mapping(source = "turnoOpuestoName", target = "player2Id", qualifiedByName = "stringToLongOrNull")
//    @Mapping(source = "turnoName", target = "turnOfPlayer", qualifiedByName = "stringToTurnNumber")
//    @Mapping(source = "tableros", target = "player1Board", qualifiedByName = "extractBoard1")
//    @Mapping(source = "tableros", target = "player2Board", qualifiedByName = "extractBoard2")
//    GameStateDTO mapToGameStateDTO(IPartida match);
//
//    @Named("stringToLongOrNull")
//    default Long stringToLongOrNull(String value) {
//        return value != null ? Long.parseLong(value) : null;
//    }
//
//    @Named("stringToTurnNumber")
//    default int stringToTurnNumber(String playerId) {
//        // Suponiendo que player1Id es el turno 1 y player2Id es el turno 2.
//        return 1; // Lógica temporal, podrías personalizarlo mejor
//    }
//
//    @Named("extractBoard1")
//    default char[][] extractBoard1(List<char[][]> boards) {
//        return boards != null && !boards.isEmpty() ? boards.get(0) : new char[10][10];
//    }
//
//    @Named("extractBoard2")
//    default char[][] extractBoard2(List<char[][]> boards) {
//        return boards != null && boards.size() > 1 ? boards.get(1) : new char[10][10];
//    }
//}
