package es.upm.etsisi.fis.fisfleet.infrastructure.config.security;

import utilidades.Cifrado;

public class LDAPAuthenticator {

    public static boolean accountBelongsToUPM(String email) {
        if (email == null) {
            return false;
        }
        email = email.trim();
        return email.endsWith("@upm.es") || email.endsWith("@alumnos.upm.es");
    }

    public static String encryptUsername(String username) {
        return username == null ? null : Cifrado.cifrar(username);
    }

    public static String authenticate(String username) {
        return accountBelongsToUPM(username) ? encryptUsername(username) : null;
    }

}
