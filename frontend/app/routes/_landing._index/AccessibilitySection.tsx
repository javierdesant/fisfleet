import React from "react";

const features = [
  "Diseño responsive y multiplataforma",
  "Soporte para daltonismo y dificultades visuales",
  "Colores corporativos UPM",
];

const AccessibilitySection: React.FC = () => (
  <section id="accessibility" className="py-16 w-full flex flex-col items-center dark:bg-oxford_blue-600 bg-white">
    <h2 className="text-3xl font-bold mb-4 text-blue-800 dark:text-blue-200">
      🌐 Accesibilidad y Diseño
    </h2>
    <ul className="list-disc pl-8 text-lg text-gray-700 dark:text-gray-200 max-w-xl">
      {features.map((f) => (
        <li key={f}>{f}</li>
      ))}
    </ul>
  </section>
);

export default AccessibilitySection;