import React from "react";

// FIXME
function Footer() {
  return (
    <footer id="footer" className="bg-gray-800 py-16">
      <div className="container mx-auto px-4">
        <div className="flex flex-wrap">
          <div
            className="w-full sm:w-1/2 md:w-1/2 lg:w-1/4"
            data-wow-delay="0.2s"
          >
            <div className="mx-3 mb-8">
              <div className="mb-3">
                <img src="assets/img/logo.svg" alt="" />
              </div>
              <p className="text-gray-300">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Quisquam excepturi quasi, ipsam voluptatem.
              </p>
            </div>
          </div>
          <div
            className="w-full sm:w-1/2 md:w-1/2 lg:w-1/4"
            data-wow-delay="0.4s"
          >
            <div className="mx-3 mb-8">
              <h3 className="mb-5 text-xl font-bold text-white">Company</h3>
              <ul className="space-y-2">
                <li>
                  <a
                    href="#"
                    className="text-gray-300 transition hover:text-white"
                  >
                    Press Releases
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="text-gray-300 transition hover:text-white"
                  >
                    Mission
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="text-gray-300 transition hover:text-white"
                  >
                    Strategy
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div
            className="w-full sm:w-1/2 md:w-1/2 lg:w-1/4"
            data-wow-delay="0.6s"
          >
            <div className="mx-3 mb-8">
              <h3 className="mb-5 text-xl font-bold text-white">About</h3>
              <ul className="space-y-2">
                <li>
                  <a
                    href="#"
                    className="text-gray-300 transition hover:text-white"
                  >
                    Career
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="text-gray-300 transition hover:text-white"
                  >
                    Team
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="text-gray-300 transition hover:text-white"
                  >
                    Clients
                  </a>
                </li>
              </ul>
            </div>
          </div>
          <div
            className="w-full sm:w-1/2 md:w-1/2 lg:w-1/4"
            data-wow-delay="0.8s"
          >
            <div className="mx-3 mb-8">
              <h3 className="mb-5 text-xl font-bold text-white">Find us on</h3>
              <ul className="flex space-x-4">
                <li>
                  <a
                    href="#"
                    className="rounded-full bg-gray-700 p-2 text-white transition hover:bg-indigo-500"
                  >
                    <i
                      className="lni lni-facebook-original"
                      aria-hidden="true"
                    ></i>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="rounded-full bg-gray-700 p-2 text-white transition hover:bg-blue-400"
                  >
                    <i
                      className="lni lni-twitter-original"
                      aria-hidden="true"
                    ></i>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="rounded-full bg-gray-700 p-2 text-white transition hover:bg-red-500"
                  >
                    <i
                      className="lni lni-instagram-original"
                      aria-hidden="true"
                    ></i>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="rounded-full bg-gray-700 p-2 text-white transition hover:bg-indigo-600"
                  >
                    <i
                      className="lni lni-linkedin-original"
                      aria-hidden="true"
                    ></i>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
