package es.upm.etsisi.fis.fisfleet.utils;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role {

    ADMIN(List.of(
            RolePermission.PLAY_GAME,
            RolePermission.VIEW_SCOREBOARD,
            RolePermission.VIEW_ALL_SCORES,
            RolePermission.MANAGE_USERS,
            RolePermission.CONFIGURE_SYSTEM,
            RolePermission.ACCESS_ADMIN_PANEL
    )),

    ALUMNO(List.of(
            RolePermission.PLAY_GAME,
            RolePermission.VIEW_SCOREBOARD,
            RolePermission.REGISTER,
            RolePermission.LOGIN
    ));

    private final List<RolePermission> permissions;

    Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

}