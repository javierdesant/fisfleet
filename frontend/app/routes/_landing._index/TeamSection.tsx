import React from "react";

const team = [
  "Raúl Gonzalo Toapanta",
  "Javier de Santiago Soto",
  "Araceli Argandoña Macollunco",
  "Daniel Rodríguez de Nobrega",
  "Estefanía Piccetti",
];

const TeamSection: React.FC = () => (
  <section id="team" className="py-16 w-full flex flex-col items-center bg-white dark:bg-oxford_blue-600">
    <h2 className="text-3xl font-bold mb-4 text-blue-800 dark:text-blue-300">👨‍💻 Equipo</h2>
    <ul className="list-disc pl-8 text-lg text-gray-700 dark:text-gray-200 max-w-xl">
      {team.map((member) => (
        <li key={member}>{member}</li>
      ))}
    </ul>
  </section>
);

export default TeamSection;