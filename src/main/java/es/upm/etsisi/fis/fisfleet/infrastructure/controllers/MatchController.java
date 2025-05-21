package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import es.upm.etsisi.fis.controller.ControladorPartida;
import es.upm.etsisi.fis.fisfleet.infrastructure.adapters.WebGameViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partida")
public class MatchController {

    private final ControladorPartida controladorPartida;

    @Autowired
    public MatchController(VistaWebControladoraPartida vistaWeb) {
        this.controladorPartida = ControladorPartida.getInstance(null);
        this.controladorPartida.setGui(vistaWeb);
    }

    @PostMapping("/crear")  // FIXME: change me pls
    public ResponseEntity<Void> crearPartida() {
        return null; // TODO
        //  remove <Void>?
    }
}