import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CrearPartidaPage() {
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const jwt = typeof window !== "undefined" ? localStorage.getItem("jwt") ?? "" : "";
    if (!jwt) {
      setError("Debes iniciar sesión para crear una partida.");
      return;
    }
    fetch("/api/match/pve?difficulty=NORMAL", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    })
      .then(async (res) => {
        if (!res.ok) throw new Error(await res.text());
        return res.text();
      })
      .then((newGameId) => {
        navigate(`/match_${newGameId}`);
      })
      .catch((err) => {
        setError("Error creando partida: " + err.message);
      });
  }, [navigate]);

  if (error) {
    return <div className="text-red-600 font-bold">{error}</div>;
  }

  return (
    <div className="flex flex-col items-center justify-center h-full">
      <h2 className="text-xl font-bold mb-4">Creando partida...</h2>
      <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
    </div>
  );
}