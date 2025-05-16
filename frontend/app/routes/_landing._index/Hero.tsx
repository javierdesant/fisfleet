import React from "react";
import Logo from "./Logo";

function Hero() {
  return (
    <section id="hero-area" className="flex flex-col items-center pt-48 pb-10">
      <div className="mx-auto px-5">
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
        <div className="mb-6">
          <img
            src="~/assets/images/fisfleet-title.svg"
            alt="Fisfleet Title"
            className="mx-auto h-12"
          />
        </div>
        <div className="mt-4">
          <a
            href="#"
            rel="nofollow"
            className="rounded-full bg-blue-600 px-10 py-3 text-white duration-300 hover:bg-blue-500"
          >
            Download Now
          </a>
        </div>
      </div>
    </section>
  );
}

export default Hero;
