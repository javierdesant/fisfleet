package es.upm.etsisi.fis.fisfleet.infrastructure.adapters;

import es.upm.etsisi.fis.model.IPartida;
import es.upm.etsisi.fis.vista.VistaControladoraPartida;

import java.util.Scanner;

// TODO
public class VistaWebControladoraPartida extends VistaControladoraPartida {
    public VistaWebControladoraPartida(Scanner escanerIn) {
        // Required by superclass constructor
        super(escanerIn);
    }

    public String seleccioarNivelMaquina() {
        return "";
    }

    public String seleccionaAccionParaRealizar() {
        return "";
    }

    public void pintapartida(IPartida p) {

    }

    public void pintaInstrucciones() {

    }

    public void printMessage(String mensaje) {

    }

    public void printError(String mensaje_error) {

    }

    public void espera() {

    }

}
