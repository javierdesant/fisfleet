import React from "react";

const steps = [
  "Regístrate con tu correo UPM",
  "Elige dificultad y juega contra la máquina o humanos",
  "Consulta el ranking y mejora tu puntuación",
];

const HowToPlaySection: React.FC = () => (
  <section id="how-to-play" className="py-16 w-full flex flex-col items-center bg-white dark:bg-oxford_blue-600">
    <h2 className="text-3xl font-bold mb-4 text-blue-800 dark:text-blue-300">🕹️ Cómo Jugar</h2>
    <ul className="list-disc pl-8 text-lg text-gray-700 dark:text-gray-200 max-w-xl">
      {steps.map((step) => (
        <li key={step}>{step}</li>
      ))}
    </ul>
  </section>
);

export default HowToPlaySection;