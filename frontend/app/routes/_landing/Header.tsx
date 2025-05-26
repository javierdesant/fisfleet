import React, { useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import Logo from "../_landing._index/Logo";

function Header() {
  const navigate = useNavigate();

  useEffect(() => {
    const handlePopState = () => {
      const hash = window.location.hash;
      if (hash) {
        const element = document.querySelector(hash);
        if (element) {
          element.scrollIntoView({ behavior: "smooth" });
        }
      }
    };

    window.addEventListener("popstate", handlePopState);
    return () => window.removeEventListener("popstate", handlePopState);
  }, []);

  const scrollToSection = (
    event: React.MouseEvent<HTMLAnchorElement>,
    id: string,
  ) => {
    event.preventDefault();
    const selector = id.startsWith("#") ? id : `#${id}`;
    const element = document.querySelector(selector);

    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
      navigate(selector, { replace: false });
    } else {
      navigate(selector);
    }
  };

  return (
    <header id="header-wrap" className="relative">
      <div className="fixed top-0 left-0 z-30 w-full bg-white shadow dark:bg-black">
        <nav className="flex items-center justify-between py-2">
          <ul className="mr-auto items-center justify-center flex">
            <li key="#hero-area">
              <a
                href="#hero-area"
                onClick={(e) => scrollToSection(e, "#hero-area")}
                className="hover:text-oxford_blue-700 dark:hover:text-picton_blue-500 block px-5 py-2 font-bold duration-300"
              >
                <Logo className="w-32 h-12" /> {/* Ajusta w-32 y h-12 según el tamaño deseado */}
              </a>
            </li>
            {[
              { path: "#services", label: "Servicios" },
              { path: "#objectives", label: "Objetivos" },
              { path: "#accessibility", label: "Accesibilidad" },
              { path: "#structure", label: "Estructura" },
              { path: "#how-to-play", label: "Cómo jugar" },
              { path: "#technologies", label: "Tecnologías" },
              { path: "#team", label: "Equipo" },
            ].map((item) => (
              <li key={item.path}>
                <a
                  href={item.path}
                  onClick={(e) => scrollToSection(e, item.path)}
                  className="hover:text-oxford_blue-700 dark:hover:text-picton_blue-500 block px-5 py-2 font-bold duration-300"
                >
                  {item.label}
                </a>
              </li>
            ))}
          </ul>

          <div className="flex gap-2 items-center">
            <NavLink
              to="/login"
              className="border border-picton_blue-500 dark:border-marian_blue-500 rounded-full px-6 py-2 font-bold text-picton_blue-500 dark:text-marian_blue-500 hover:bg-picton_blue-500 hover:text-white dark:hover:bg-marian_blue-500 dark:hover:text-white duration-300"
            >
              Iniciar sesión
            </NavLink>
            <NavLink
              to="/register"
              className="border border-light_red-500 dark:border-light_red-dark-500 rounded-full px-6 py-2 font-bold text-light_red-500 dark:text-light_red-dark-500 hover:bg-light_red-500 hover:text-white dark:hover:bg-light_red-dark-500 dark:hover:text-white duration-300"
            >
              Registrarse
            </NavLink>
          </div>
        </nav>
      </div>
    </header>
  );
}

export default Header;
