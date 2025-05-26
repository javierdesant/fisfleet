import React from "react";

const ProjectStructureSection: React.FC = () => (
  <section id="structure" className="py-16 bg-blue-50 dark:bg-gray-900 w-full flex flex-col items-center">
    <h2 className="text-3xl font-bold mb-4 text-blue-800 dark:text-blue-300">📂 Estructura del Proyecto</h2>
    <p className="text-gray-700 dark:text-gray-200 max-w-2xl mb-2">
      Frontend y backend en un solo repositorio, organizados por carpetas para documentación, frontend (React), backend (Spring Boot), librerías, tests y configuración Docker.
    </p>
    <pre className="bg-gray-100 dark:bg-gray-800 rounded p-4 text-xs overflow-x-auto max-w-2xl text-gray-800 dark:text-gray-200">
{`fisfleet/
├── docs/
├── frontend/
├── libs/
├── src/
├── target/
├── TODO/
├── .env.example
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── mvnw, mvnw.cmd
├── pom.xml
├── LICENSE
└── README.md`}
    </pre>
  </section>
);

export default ProjectStructureSection;