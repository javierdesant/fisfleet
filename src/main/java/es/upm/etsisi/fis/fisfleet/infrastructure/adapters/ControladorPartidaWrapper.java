package es.upm.etsisi.fis.fisfleet.infrastructure.adapters;

import es.upm.etsisi.fis.controller.ControladorPartida;
import es.upm.etsisi.fis.model.*;

import java.util.HashMap;
import java.util.Scanner;

public class ControladorPartidaWrapper {
    public Partida crearPartida(IJugador jugador, IJugador maquina, IPuntuacion puntuacion, IMovimiento movimiento) {
        Scanner scanner = new Scanner("");  // dummy
        ControladorPartida controlador = ControladorPartida.getInstance(scanner);

        return new Partida(jugador, maquina, movimiento);
    }

    public String procesarTurno(Partida partida) {
        HashMap<String, Object> salidaHash = partida.aplicaTurno();
        partida.swapTurn();

        String resultado = (String) salidaHash.get("String");
        Nave ultimaTocada = (Nave) salidaHash.get("Nave");
        if (ultimaTocada != null) {
            ultimaTocada.accionComplementaria(partida);
        }

        return resultado;
    }

}
