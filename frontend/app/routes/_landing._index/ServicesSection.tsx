import React from "react";
import ServiceItem from "./ServiceItem";

const services = [
  {
    icon: "lni lni-cog",
    title: "Web Development",
    description:
      "Desarrollo de aplicaciones web modernas, accesibles y escalables utilizando tecnologías de última generación como React, Spring Boot y Docker. Creamos soluciones robustas adaptadas a las necesidades de la Universidad Politécnica de Madrid.",
  },
  {
    icon: "lni lni-bar-chart",
    title: "Graphic Design",
    description:
      "Diseño gráfico profesional enfocado en la identidad visual de la UPM. Creamos interfaces atractivas, accesibles y coherentes con los colores corporativos, garantizando una experiencia de usuario óptima en todos los dispositivos.",
  },
  {
    icon: "lni lni-briefcase",
    title: "Business Branding",
    description:
      "Impulsamos la imagen de marca de la UPM mediante estrategias de branding digital, integrando logotipos, paletas de colores y mensajes clave para fortalecer la presencia institucional en el entorno digital.",
  },
  {
    icon: "lni lni-pencil-alt",
    title: "Content Writing",
    description:
      "Redacción de contenidos claros, inclusivos y adaptados a la comunidad universitaria. Elaboramos documentación técnica, tutoriales y textos accesibles para todos los usuarios, cumpliendo con los estándares de calidad y accesibilidad.",
  },
  {
    icon: "lni lni-mobile",
    title: "App Development",
    description:
      "Desarrollo de aplicaciones móviles multiplataforma, optimizadas para smartphones, tablets y otros dispositivos. Garantizamos rendimiento, seguridad y una experiencia de usuario fluida en cualquier entorno.",
  },
  {
    icon: "lni lni-layers",
    title: "Digital Marketing",
    description:
      "Estrategias de marketing digital orientadas a la difusión de proyectos universitarios. Gestionamos campañas, redes sociales y posicionamiento web para maximizar el alcance y la visibilidad de la UPM.",
  },
];

const ServicesSection: React.FC = () => (
  <section id="services" className="py-24">
    <div className="mx-auto px-5">
      <div className="mb-12 text-center">
        <h2 className="text-4xl font-bold tracking-wide text-eerie_black-500 dark:text-seasalt-500">
          Our Services
        </h2>
      </div>
      <div className="-m-4 flex flex-wrap p-4 text-block">
        {services.map((service) => (
          <ServiceItem
            key={service.title}
            icon={service.icon}
            title={service.title}
            description={service.description}
          />
        ))}
      </div>
    </div>
  </section>
);

export default ServicesSection;