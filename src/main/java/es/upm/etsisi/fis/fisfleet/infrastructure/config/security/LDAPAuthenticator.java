package es.upm.etsisi.fis.fisfleet.infrastructure.config.security;

import servidor.Autenticacion;
import utilidades.Cifrado;

public class LDAPAuthenticator {

    public static boolean accountBelongsToUPM(String email) {
        if (email == null) {
            return false;
        }
        return Autenticacion.existeCuentaUPMStatic(email);
    }

    public static String encryptUsername(String username) {
        return username == null ? null : Cifrado.cifrar(username);
    }

    public static String authenticate(String username) {
        return accountBelongsToUPM(username) ? encryptUsername(username) : null;
    }

}
