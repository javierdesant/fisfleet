import React from "react";

const objectives = [
  "Especificación de requisitos y análisis detallado",
  "Modelado UML y diseño de software",
  "Gestión de cambios y desarrollo iterativo",
  "Verificación, validación y calidad",
  "Trabajo en equipo y liderazgo",
];

const ObjectivesSection: React.FC = () => (
  <section id="objectives" className="py-16 bg-blue-50 dark:bg-gray-900 w-full flex flex-col items-center">
    <h2 className="text-3xl font-bold mb-4 text-blue-800 dark:text-blue-300">🎯 Objetivos</h2>
    <ul className="list-disc pl-8 text-lg text-gray-700 dark:text-gray-200 max-w-xl">
      {objectives.map((obj) => (
        <li key={obj}>{obj}</li>
      ))}
    </ul>
  </section>
);

export default ObjectivesSection;