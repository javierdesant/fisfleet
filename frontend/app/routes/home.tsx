import React from "react";
import { useNavigate } from "react-router-dom";

export default function HomePage() {
  const navigate = useNavigate();

  const handleCrearPartida = () => {
    navigate("/crear-partida");
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-alice_blue-100">
      <h1 className="text-4xl font-bold mb-6 text-eerie_black-700">Bienvenido a FisFleet</h1>
      <p className="mb-8 text-lg text-gray-700">¿Listo para jugar Batalla Naval?</p>
      <button
        onClick={handleCrearPartida}
        className="px-6 py-3 bg-indigo-600 text-white rounded-lg font-semibold hover:bg-indigo-700 transition"
      >
        Crear partida nueva
      </button>
      {/* Puedes agregar más opciones aquí, como ver partidas anteriores, perfil, etc. */}
    </div>
  );
}