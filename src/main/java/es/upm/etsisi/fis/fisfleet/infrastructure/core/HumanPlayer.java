package es.upm.etsisi.fis.fisfleet.infrastructure.core;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.MoveRequest;
import es.upm.etsisi.fis.fisfleet.infrastructure.exceptions.TurnTimeoutException;
import es.upm.etsisi.fis.model.IJugador;
import es.upm.etsisi.fis.model.IMovimiento;
import es.upm.etsisi.fis.model.IPuntuacion;
import es.upm.etsisi.fis.model.TBarcoAccionComplementaria;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HumanPlayer implements IJugador {
    private final Long userId;
//    private volatile boolean isMyTurn = true;  // TODO

    public HumanPlayer(Long userId) {
        this.userId = userId;
    }

    public boolean acceptComplementaryAction(TBarcoAccionComplementaria complementaryShipAction, int availableAmount) {
        if (availableAmount < 1) {
            return false;
        }

        return true; // TODO
    }

    @Override
    public boolean aceptarAccionComplementaria(TBarcoAccionComplementaria tBarcoAccionComplementaria, int i) {
        if (i < 1) {
            return false;
        }

        return true; // TODO
    }

    @Override
    public int[] realizaTurno(char[][] chars) {
//        if (!isMyTurn) {
//            throw new IllegalStateException("It's not your turn.");
//        }

        try {
            MoveRequest move = MoveRequestWaiter.waitForMove(userId).get(30, TimeUnit.SECONDS);
            return new int[]{move.getCoordinateX(), move.getCoordinateY()};
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException("Error processing the move.", ex);
        } catch (TimeoutException ex) {
            throw new TurnTimeoutException();
        }


//    public void setMyTurn(boolean isMyTurn) {
//        this.isMyTurn = isMyTurn;
//    }
    }

    @Override
    public void addMovimiento(IMovimiento iMovimiento) {}

    @Override
    public String getNombre() {
        return "";
    }

    @Override
    public void addPuntuacion(IPuntuacion iPuntuacion) {}

    @Override
    public List<IMovimiento> getMovimientos() {
        return List.of();
    }

    @Override
    public List<IPuntuacion> getPuntuaciones() {
        return List.of();
    }
}
