import type { Route } from "~/../.react-router/types/app/routes/match_.$id._index/+types/route";
import PlayerBoard from "./PlayerBoard";
import EnemyBoard from "./EnemyBoard";
import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useBattleshipGame } from "../../hooks/useBattleshipGame";
import SpecialAbilityButton from "./SpecialAbilityButton";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Match" },
    { name: "description", content: "Good luck!" },
  ];
};

export default function BattlePage() {
  const { id: gameIdParam } = useParams();
  const navigate = useNavigate();
  const [token, setToken] = useState<string>("");
  const [gameId, setGameId] = useState<string | undefined>(gameIdParam);

  // 1. Obtener el token JWT del usuario
  useEffect(() => {
    if (typeof window !== "undefined") {
      const jwt = localStorage.getItem("jwt") ?? "";
      setToken(jwt);
    }
  }, []);

  // 2. Si no hay gameId en la URL, crear partida (PVE por defecto, dificultad NORMAL)
  useEffect(() => {
    if (!token) return;
    if (!gameId) {
      fetch("/api/match/pve?difficulty=NORMAL", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then(async (res) => {
          if (!res.ok) throw new Error(await res.text());
          return res.text();
        })
        .then((newGameId) => {
          setGameId(newGameId);
          navigate(`/match_${newGameId}`);
        })
        .catch((err) => {
          alert("Error creando partida: " + err);
        });
    }
  }, [token, gameId, navigate]);

  // 3. Hook para lógica de juego y conexión websocket
  const {
    playerBoard,
    enemyBoard,
    isMyTurn,
    specialAvailable,
    sendMove,
  } = useBattleshipGame(gameId!, token);

  const [specialMode, setSpecialMode] = useState(false);

  // 4. Cuando el usuario hace click en una celda enemiga
  function handleAttack(x: number, y: number) {
    if (!isMyTurn) return;
    sendMove(x, y, specialMode);
    setSpecialMode(false);
  }

  // 5. Renderizado de la página de batalla
  return (
    <div className="flex flex-col lg:flex-row gap-4">
      <div className="p-2 m-2 grow">
        <h2 className="text-eerie_black-500 dark:text-seasalt-500 mb-4 font-bold text-2xl md::text-3xl text-center bg-alice_blue-200">
          Flota enemiga
        </h2>
        <EnemyBoard gridData={enemyBoard} onCellClick={handleAttack} />
        <SpecialAbilityButton
          enabled={isMyTurn && specialAvailable && !specialMode}
          onClick={() => setSpecialMode(true)}
        />
        {specialMode && (
          <div className="text-indigo-700 font-bold mt-2">
            ¡Selecciona una celda para usar la habilidad especial!
          </div>
        )}
        {!isMyTurn && (
          <div className="text-gray-500 mt-2">Esperando turno...</div>
        )}
      </div>

      <div className="border-onyx-800 dark:border-white-500 min-h-full border" />

      <div className="p-2 m-2 grow">
        <h2 className="text-eerie_black-500 dark:text-seasalt-500 mb-4 font-bold text-2xl md::text-3xl text-center bg-alice_blue-200">
          Tu flota
        </h2>
        <PlayerBoard gridData={playerBoard} />
      </div>
    </div>
  );
}