import { useEffect, useRef, useState, useCallback } from "react";
import { Client } from "@stomp/stompjs";

type MoveRequest = {
  coordinateX: number;
  coordinateY: number;
  specialAbility: boolean;
};

export function useBattleshipGame(gameId: string, token: string) {
  const [playerBoard, setPlayerBoard] = useState<string[]>(Array(100).fill("?"));
  const [enemyBoard, setEnemyBoard] = useState<string[]>(Array(100).fill("?"));
  const [isMyTurn, setIsMyTurn] = useState(false);
  const [specialAvailable, setSpecialAvailable] = useState(true);
  const clientRef = useRef<Client | null>(null);

  // Recibe mensajes del backend y actualiza el estado
  const onMessage = useCallback((msg: any) => {
    setPlayerBoard(msg.ownBoard);
    setEnemyBoard(msg.enemyBoardMasked);
    setIsMyTurn(msg.isMyTurn);
    setSpecialAvailable(msg.specialAvailable ?? true);
  }, []);

  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws", // Cambia si usas SockJS
      connectHeaders: { Authorization: `Bearer ${token}` },
      reconnectDelay: 5000,
      onConnect: () => {
        client.subscribe(`/topic/game/${gameId}`, (message) => {
          onMessage(JSON.parse(message.body));
        });
      },
    });
    client.activate();
    clientRef.current = client;
    return () => {
      client.deactivate();
    };
  }, [gameId, token, onMessage]);

  // Enviar movimiento
  function sendMove(x: number, y: number, specialAbility: boolean) {
    clientRef.current?.publish({
      destination: `/app/game/${gameId}/move`,
      body: JSON.stringify({ coordinateX: x, coordinateY: y, specialAbility }),
    });
  }

  return { playerBoard, enemyBoard, isMyTurn, specialAvailable, sendMove };
}