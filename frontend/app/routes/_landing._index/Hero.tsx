import React from "react";
import Logo from "./Logo";

function Hero() {
  return (
    <section id="hero-area" className="flex flex-col items-center pt-48 pb-10">
      <div className="mx-auto flex flex-col items-center px-5">
        <div className="mb-6 flex flex-col items-center justify-center gap-4 lg:flex-row">
          <Logo
            title="Fisfleet logo"
            titleId="fisfleet-logo"
            className="size-48 md:size-96 lg:size-128"
          />
          <img
            src="/fisfleet-title.png"
            alt="Fisfleet Title"
            className="mx-auto h-48"
          />
        </div>
        <div className="mt-4">
          <a
            href="#"
            rel="nofollow"
            className="bg-light_red-500 hover:bg-light_red-400 text-eerie_black-500 dark:bg-light_red-dark-500 hover:dark:bg-light_red-dark-400 dark:text-seasalt-500 rounded-full px-10 py-3 text-2xl font-black duration-300"
          >
            Play Now!
          </a>
        </div>
      </div>
    </section>
  );
}

export default Hero;
