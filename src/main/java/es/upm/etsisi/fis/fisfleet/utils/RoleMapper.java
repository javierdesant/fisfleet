package es.upm.etsisi.fis.fisfleet.utils;

import servidor.UPMUsers;

import java.util.EnumMap;
import java.util.Map;

public class RoleMapper {

    private static final Map<UPMUsers, Role> userRoleMap = new EnumMap<>(UPMUsers.class);

    static {
        userRoleMap.put(UPMUsers.PDI, Role.ADMIN);
        userRoleMap.put(UPMUsers.PAS, Role.ADMIN);
        userRoleMap.put(UPMUsers.ALUMNO, Role.ALUMNO);
    }

    public static Role getRoleForUPMUser(UPMUsers upmUser) {
        return userRoleMap.get(upmUser);
    }

}
