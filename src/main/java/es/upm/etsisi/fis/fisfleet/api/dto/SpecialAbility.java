package es.upm.etsisi.fis.fisfleet.api.dto;

import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;

public enum SpecialAbility {
    NONE,
    COUNTER_ATTACK,
    ARTILLERY_ATTACK,
    REPAIR,
    REVEAL_ROW;

    public static SpecialAbility fromShipName(String name) {
        if (name == null) {
            return NONE;
        }

        return switch (name.trim().toUpperCase()) {
            case "PORTAVIONES" -> COUNTER_ATTACK;
            case "ACORAZADO" -> ARTILLERY_ATTACK;
            case "SUBMARINO" -> REPAIR;
            case "PATRULLERO" -> REVEAL_ROW;
            default -> null;
        };
    }
}
