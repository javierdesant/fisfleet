package es.upm.etsisi.fis.fisfleet.utils;

import servidor.UPMUsers;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class RoleMapper {

    private static final Map<UPMUsers, Role> userRoleMap = new EnumMap<>(UPMUsers.class);

    static {
        userRoleMap.put(UPMUsers.PDI, Role.ADMIN);
        userRoleMap.put(UPMUsers.PAS, Role.ADMIN);
        userRoleMap.put(UPMUsers.ALUMNO, Role.ALUMNO);
    }

    private static Role getRoleForUser(UPMUsers upmUser) {
        return userRoleMap.get(upmUser);
    }

    public static List<RolePermission> getPermissionsForUser(UPMUsers upmUser) {
        Role role = getRoleForUser(upmUser);
        return role != null ? role.getPermissions() : List.of();
    }
}
