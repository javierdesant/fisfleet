package es.upm.etsisi.fis.fisfleet.infrastructure.adapters;

import es.upm.etsisi.fis.fisfleet.api.dto.GameStateDTO;
import es.upm.etsisi.fis.fisfleet.api.mappers.GameStateMapper;
import es.upm.etsisi.fis.fisfleet.infrastructure.cache.RequestResponseCacheService;
import es.upm.etsisi.fis.fisfleet.infrastructure.exceptions.RequestCanceledException;
import es.upm.etsisi.fis.model.IPartida;
import es.upm.etsisi.fis.vista.VistaControladoraPartida;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


// TODO important class!


@Component
@Slf4j
public class WebGameViewAdapter extends VistaControladoraPartida {

    private final GameStateMapper gameStateMapper;
    private final SimpMessagingTemplate messagingTemplate;

    // Default game ID for when a specific game is not set
    private String currentGameId = "default";


    // FIXME!: messaging template and caches issues

    @Autowired
    public WebGameViewAdapter(GameStateMapper gameStateMapper,
                              SimpMessagingTemplate messagingTemplate,
                              RequestResponseCacheService requestResponseCacheService) {
        super(null);
        this.gameStateMapper = gameStateMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public String seleccioarNivelMaquina() {
        String key = "machinelvl-" + UUID.randomUUID();
        return this.registerRequest(key, 60);
    }

    @Override
    public String seleccionaAccionParaRealizar() {
        String key = "action-" + UUID.randomUUID();
        return this.registerRequest(key, 120);
    }

    private String registerRequest(String key, int timeoutSeconds) {
//        CompletableFuture<String> future = requestResponseCacheService.registerRequest(key);

        // Send the request key to the client via WebSocket
        try {
            Map<String, Object> payload = Map.of(
                    "type", "REQUEST_INPUT",
                    "requestKey", key,
                    "timeout", timeoutSeconds,
                    "timestamp", System.currentTimeMillis()
            );

            messagingTemplate.convertAndSend("/topic/game/" + currentGameId, payload);
            log.debug("Request sent to game {} with key: {}", currentGameId, key);
        } catch (Exception e) {
            log.error("Error sending request via WebSocket", e);
        }

        try {
            return future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for response", e);
            throw new RuntimeException("Thread interrupted.");
        } catch (TimeoutException e) {
            log.warn("Request with key {} expired", key);
            sendErrorWebSocket("The request expired. Please try again.");
//            requestResponseCacheService.removeRequest(key);
            throw new RequestCanceledException();
        } catch (ExecutionException e) {
            log.error("Error while waiting for response", e);
            throw new RuntimeException("Error processing the request.");
        }
    }

    public void completarSolicitud(String key, String res) {
//        requestResponseCacheService.completeRequest(key, res);
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

    /**
     * Sends game state to clients via WebSocket
     *
     * @param state The game state to send
     */
    private void sendStateWebSocket(GameStateDTO state) {
        try {
            Map<String, Object> payload = Map.of(
                    "type", "GAME_STATE",
                    "payload", state,
                    "timestamp", System.currentTimeMillis()
            );

            messagingTemplate.convertAndSend("/topic/game/" + currentGameId, payload);
            log.debug("Game state sent to game: {}", currentGameId);
        } catch (Exception e) {
            log.error("Error sending game state via WebSocket", e);
        }
    }

    /**
     * Sends a message to clients via WebSocket
     *
     * @param message The message to send
     */
    private void sendMessageWebSocket(String message) {
        try {
            Map<String, Object> payload = Map.of(
                    "type", "MESSAGE",
                    "content", message,
                    "timestamp", System.currentTimeMillis()
            );

            messagingTemplate.convertAndSend("/topic/game/" + currentGameId, payload);
            log.debug("Message sent to game {}: {}", currentGameId, message);
        } catch (Exception e) {
            log.error("Error sending message via WebSocket", e);
        }
    }

    /**
     * Sends an error message to clients via WebSocket
     *
     * @param error The error message to send
     */
    private void sendErrorWebSocket(String error) {
        try {
            Map<String, Object> payload = Map.of(
                    "type", "ERROR",
                    "content", error,
                    "timestamp", System.currentTimeMillis()
            );

            messagingTemplate.convertAndSend("/topic/game/" + currentGameId, payload);
            log.warn("Error sent to game {}: {}", currentGameId, error);
        } catch (Exception e) {
            log.error("Error sending error message via WebSocket", e);
        }
    }
}
