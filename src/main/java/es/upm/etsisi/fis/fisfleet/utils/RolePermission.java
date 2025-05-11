package es.upm.etsisi.fis.fisfleet.utils;

import lombok.Getter;

@Getter
public enum RolePermission {
    PLAY_GAME,
    VIEW_SCOREBOARD,
    REGISTER,
    LOGIN,
    VIEW_ALL_SCORES,
    MANAGE_USERS,
    CONFIGURE_SYSTEM,
    ACCESS_ADMIN_PANEL;
}