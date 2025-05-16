package es.upm.etsisi.fis.fisfleet.infrastructure.adapters;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import es.upm.etsisi.fis.fisfleet.api.dto.GameStateDTO;
import es.upm.etsisi.fis.fisfleet.api.mappers.GameStateMapper;
import es.upm.etsisi.fis.fisfleet.infrastructure.exceptions.RequestCanceledException;
import es.upm.etsisi.fis.model.IPartida;
import es.upm.etsisi.fis.vista.VistaControladoraPartida;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class VistaWebControladoraPartida extends VistaControladoraPartida {

    private final GameStateMapper gameStateMapper;

    private final Cache<String, CompletableFuture<String>> responsesCache = Caffeine.newBuilder()
            .expireAfterWrite(8, TimeUnit.HOURS)
            .maximumSize(1000)
            .build();

    @Autowired
    public VistaWebControladoraPartida(GameStateMapper gameStateMapper) {
        super(null);
        this.gameStateMapper = gameStateMapper;
    }

    @Override
    public String seleccioarNivelMaquina() {
        String key = "machinelvl-" + UUID.randomUUID();
        return this.registerRequest(key, 60)
                ;
    }

    @Override
    public String seleccionaAccionParaRealizar() {
        String key = "action-" + UUID.randomUUID();
        return this.registerRequest(key, 120);
    }

    private String registerRequest(String key, int timeoutSeconds) {
        CompletableFuture<String> future = new CompletableFuture<>();
        this.responsesCache.put(key, future);

        // TODO!: send the key to the client via WebSocket
        sendMessageWebSocket("Waiting for response for: " + key);

        try {
            return future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for response", e);
            throw new RuntimeException("Thread interrupted.");
        } catch (TimeoutException e) {
            log.warn("Request with key {} expired", key);
            sendErrorWebSocket("The request expired. Please try again.");
            throw new RequestCanceledException();
        } catch (ExecutionException e) {
            log.error("Error while waiting for response", e);
            throw new RuntimeException("Error processing the request.");
        } finally {
            this.responsesCache.invalidate(key);
        }
    }

    public void completarSolicitud(String key, String res) {
        CompletableFuture<String> future = this.responsesCache.getIfPresent(key);
        if (future != null && !future.isDone()) {
            future.complete(res);
        }
        this.responsesCache.invalidate(key);
    }

    @Override
    public void pintapartida(IPartida match) {
        GameStateDTO gameState = gameStateMapper.mapToGameStateDTO(match);

        this.sendStateWebSocket(gameState);
    }

    @Override
    public void pintaInstrucciones() {
        String instrucciones = "El juego consiste en hundir los 4 barcos del tablero del contrincante.\n" +
                "- Portaaviones: 4 casillas\n" +
                "- Patrullera: 3 casillas\n" +
                "- Acorazado: 3 casillas\n" +
                "- Submarino: 2 casillas\n" +
                "¡Buena suerte!";
        this.sendMessageWebSocket(instrucciones);
    }

    @Override
    public void printMessage(String mensaje) {
        this.sendMessageWebSocket(mensaje);
    }

    @Override
    public void printError(String mensaje_error) {
        this.sendErrorWebSocket(mensaje_error);
    }

    @Override
    public void espera() {
        printMessage("Esperando interacción desde el cliente...");
    }

    // TODO: implement websockets
    private void sendStateWebSocket(GameStateDTO state) {
        System.out.println("[WebSocket] State sent: " + state);
    }

    // TODO: implement websockets
    private void sendMessageWebSocket(String message) {
        System.out.println("[WebSocket] Message sent: " + message);
    }

    // TODO: implement websockets
    private void sendErrorWebSocket(String error) {
        System.err.println("[WebSocket] Error sent: " + error);
    }
}
