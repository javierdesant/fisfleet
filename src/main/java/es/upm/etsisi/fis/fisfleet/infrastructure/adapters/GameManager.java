package es.upm.etsisi.fis.fisfleet.infrastructure.adapters;

import es.upm.etsisi.fis.controller.ControladorPartida;
import es.upm.etsisi.fis.model.*;
import org.springframework.stereotype.Component;

@Component
public class GameManager {
    private final ControladorPartida controladorPartida;
//  TODO  private final WebGameViewAdapter webGameViewAdapter;

    public GameManager(WebGameViewAdapter vistaWeb) {
        this.controladorPartida = ControladorPartida.getInstance(null);
        this.controladorPartida.setGui(vistaWeb);
    }

    public void realizaContrataque(Partida partidaActual) {
        this.controladorPartida.realizaContrataque(partidaActual);
    }

    public void crearPartida(IJugador usuariologued, IJugador maquina, IPuntuacion puntuacion, IMovimiento movimiento) {
        this.controladorPartida.crearPartida(usuariologued, maquina, puntuacion, movimiento);
    }

    public void crearPartida(IJugador usuariologued, IJugador maquina, IPuntuacion puntuacion, IMovimiento movimiento, boolean espera) {
        this.controladorPartida.crearPartida(usuariologued, maquina, puntuacion, movimiento, espera);
    }

    public void crearPartida(IJugador usuariologued, IPuntuacion puntuacion, IMovimiento movimiento) {
        this.controladorPartida.crearPartida(usuariologued, puntuacion, movimiento);
    }

    public void lanzaArtilleria(Partida partidaActual, IAcorazado acorazado) {
        this.controladorPartida.lanzaArtilleria(partidaActual, acorazado);
    }

    public void reparase(Partida partidaActual, ISubmarino submarino) {
        this.controladorPartida.reparase(partidaActual, submarino);
    }

    public void mostrar(Partida partidaActual, IPatrullero patrullero) {
        this.controladorPartida.mostrar(partidaActual, patrullero);
    }

    public void setGui(WebGameViewAdapter gui) {
        this.controladorPartida.setGui(gui);
    }

}