package es.upm.etsisi.fis.fisfleet.infrastructure.core;

import es.upm.etsisi.fis.model.Nave;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LastHitCache {
    private static final Map<Long, Nave> lastNave = new ConcurrentHashMap<>();

    public static void save(Long userId, Nave nave) {
        lastNave.put(userId, nave);
    }

    public static Nave get(Long userId) {
        return lastNave.get(userId);
    }
}