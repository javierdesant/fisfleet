import React from "react";

const techs = [
  "Backend: Java 23+, Spring Boot, LDAP, Docker",
  "Frontend: React, TailwindCSS, React Router",
  "Test: JUnit, Mockito, typecheck",
];

const TechnologiesSection: React.FC = () => (
  <section id="technologies" className="py-16 bg-blue-50 dark:bg-gray-900 w-full flex flex-col items-center">
    <h2 className="text-3xl font-bold mb-4 text-blue-800 dark:text-blue-300">📦 Tecnologías</h2>
    <ul className="list-disc pl-8 text-lg text-gray-700 dark:text-gray-200 max-w-xl">
      {techs.map((t) => (
        <li key={t}>{t}</li>
      ))}
    </ul>
  </section>
);

export default TechnologiesSection;