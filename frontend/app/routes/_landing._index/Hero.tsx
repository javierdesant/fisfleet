import React from "react";
import Logo from "./Logo";
import { useNavigate } from "react-router-dom";

function Hero() {
  const navigate = useNavigate();

  return (
    <section id="hero-area" className="flex flex-col items-center pt-32 pb-10">
      <div className="mx-auto flex flex-col items-center px-5">
        <div className="mb-6 flex flex-col items-center justify-center gap-4 lg:flex-row">
          <div className="flex-shrink-0 flex items-center justify-center">
            <Logo
              title="Fisfleet logo"
              titleId="fisfleet-logo"
              className="w-32 h-32 md:w-56 md:h-56 lg:w-72 lg:h-72 max-w-full max-h-72"
            />
          </div>
          <div className="flex-shrink-0 flex items-center justify-center">
            <img
              src="/fisfleet-title.png"
              alt="Fisfleet Title"
              className="h-20 md:h-32 lg:h-48 w-auto max-w-xs md:max-w-md lg:max-w-lg"
              style={{ objectFit: "contain" }}
            />
          </div>
        </div>
        <div className="mt-4">
          <button
            type="button"
            className="cursor-pointer bg-light_red-500 hover:bg-light_red-400 text-eerie_black-500 dark:bg-light_red-dark-500 hover:dark:bg-light_red-dark-400 dark:text-seasalt-500 rounded-full px-10 py-3 text-2xl font-black duration-300"
            onClick={() => navigate("/login")}
          >
            Play Now!
          </button>
        </div>
      </div>
    </section>
  );
}

export default Hero;