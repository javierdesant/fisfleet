import React, { useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";

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
          <ul className="mr-auto items-center justify-center lg:flex">
            {[
              { path: "#hero-area", label: "Home" },
              { path: "#about", label: "About" },
              { path: "#services", label: "Services" },
              { path: "#contact", label: "Contact" },
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

          {/*TODO: add register and login links*/}

          <div className="hidden sm:absolute sm:right-0 sm:mr-16 sm:block lg:static lg:mr-0">
            <NavLink
              className="border-picton_blue-500 dark:border-marian_blue-500 hover:bg-picton_blue-500 dark:hover:bg-marian_blue-500 rounded-full border px-10 py-3 duration-300 hover:text-white"
              to="/#feature"
            >
              FAQ
            </NavLink>
          </div>
        </nav>
      </div>
    </header>
  );
}

export default Header;
