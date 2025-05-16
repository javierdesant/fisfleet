import type { Route } from "../../../.react-router/types/app/routes/_landing._index/+types/route";
import React from "react";
import Hero from "~/routes/_landing._index/Hero";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Landing" },
    { name: "description", content: "Landing page" },
  ];
};

// TODO
export default function HomePage() {
  return (
    <div>
      <Hero />

      <section id="services" className="py-24">
        <div className="mx-auto px-5">
          <div className="mb-12 text-center">
            <h2 className="text-4xl font-bold tracking-wide text-gray-700">
              Our Services
            </h2>
          </div>
          <div className="-m-4 flex flex-wrap">
            <div className="w-full p-4 sm:w-1/2 lg:w-1/3">
              <div className="text-center">
                <div className="mb-4 text-5xl text-blue-600">
                  <i className="lni lni-cog"></i>
                </div>
                <h3 className="mb-3 text-lg font-semibold text-gray-800 uppercase">
                  Web Development
                </h3>
                <p className="text-gray-600">Lorem ipsum dolor sit amet...</p>
              </div>
            </div>
            <div className="w-full p-4 sm:w-1/2 lg:w-1/3">
              <div className="text-center">
                <div className="mb-4 text-5xl text-blue-600">
                  <i className="lni lni-bar-chart"></i>
                </div>
                <h3 className="mb-3 text-lg font-semibold text-gray-800 uppercase">
                  Graphic Design
                </h3>
                <p className="text-gray-600">Lorem ipsum dolor sit amet...</p>
              </div>
            </div>
            <div className="w-full p-4 sm:w-1/2 lg:w-1/3">
              <div className="text-center">
                <div className="mb-4 text-5xl text-blue-600">
                  <i className="lni lni-briefcase"></i>
                </div>
                <h3 className="mb-3 text-lg font-semibold text-gray-800 uppercase">
                  Business Branding
                </h3>
                <p className="text-gray-600">Lorem ipsum dolor sit amet...</p>
              </div>
            </div>
            <div className="w-full p-4 sm:w-1/2 lg:w-1/3">
              <div className="text-center">
                <div className="mb-4 text-5xl text-blue-600">
                  <i className="lni lni-pencil-alt"></i>
                </div>
                <h3 className="mb-3 text-lg font-semibold text-gray-800 uppercase">
                  Content Writing
                </h3>
                <p className="text-gray-600">Lorem ipsum dolor sit amet...</p>
              </div>
            </div>
            <div className="w-full p-4 sm:w-1/2 lg:w-1/3">
              <div className="text-center">
                <div className="mb-4 text-5xl text-blue-600">
                  <i className="lni lni-mobile"></i>
                </div>
                <h3 className="mb-3 text-lg font-semibold text-gray-800 uppercase">
                  App Development
                </h3>
                <p className="text-gray-600">Lorem ipsum dolor sit amet...</p>
              </div>
            </div>
            <div className="w-full p-4 sm:w-1/2 lg:w-1/3">
              <div className="text-center">
                <div className="mb-4 text-5xl text-blue-600">
                  <i className="lni lni-layers"></i>
                </div>
                <h3 className="mb-3 text-lg font-semibold text-gray-800 uppercase">
                  Digital Marketing
                </h3>
                <p className="text-gray-600">Lorem ipsum dolor sit amet...</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section id="feature" className="bg-blue-100 py-24">
        <div className="mx-auto px-5">
          <div className="flex flex-wrap items-center">
            <div className="mb-10 w-full lg:mb-0 lg:w-1/2">
              <h2 className="mb-12 text-4xl font-bold tracking-wide text-gray-700">
                Learn More About Us
              </h2>
              <div className="-m-3 flex flex-wrap">
                <div className="w-1/2 p-3">
                  <div className="flex flex-col items-center text-center">
                    <div className="mb-3 text-4xl text-blue-600">
                      <i className="lni lni-layers"></i>
                    </div>
                    <h4 className="mb-3 font-medium text-gray-800">
                      Built with TailwindCSS
                    </h4>
                    <p>Lorem ipsum dolor sit amet...</p>
                  </div>
                </div>
                <div className="w-1/2 p-3">
                  <div className="flex flex-col items-center text-center">
                    <div className="mb-3 text-4xl text-blue-600">
                      <i className="lni lni-gift"></i>
                    </div>
                    <h4 className="mb-3 font-medium text-gray-800">
                      Free to Use
                    </h4>
                    <p>Lorem ipsum dolor sit amet...</p>
                  </div>
                </div>
                <div className="w-1/2 p-3">
                  <div className="flex flex-col items-center text-center">
                    <div className="mb-3 text-4xl text-blue-600">
                      <i className="lni lni-laptop-phone"></i>
                    </div>
                    <h4 className="mb-3 font-medium text-gray-800">
                      Fully Responsive
                    </h4>
                    <p>Lorem ipsum dolor sit amet...</p>
                  </div>
                </div>
                <div className="w-1/2 p-3">
                  <div className="flex flex-col items-center text-center">
                    <div className="mb-3 text-4xl text-blue-600">
                      <i className="lni lni-leaf"></i>
                    </div>
                    <h4 className="mb-3 font-medium text-gray-800">
                      Easy to Customize
                    </h4>
                    <p>Lorem ipsum dolor sit amet...</p>
                  </div>
                </div>
              </div>
            </div>
            <div className="w-full lg:w-1/2">
              <img
                className="mx-auto"
                src="assets/img/feature/img-1.svg"
                alt=""
              />
            </div>
          </div>
        </div>
      </section>

      <section id="team" className="py-24 text-center">
        <div className="mx-auto px-5">
          <h2 className="mb-12 text-4xl font-bold tracking-wide text-gray-700">
            Our Team
          </h2>
          <div className="flex flex-wrap justify-center">
            {/*Repeat team items*/}
            <div className="max-w-sm p-3 sm:w-1/2 lg:w-1/3">
              <div className="relative bg-white p-4 shadow duration-300 hover:shadow-lg">
                <img className="w-full" src="assets/img/team/img1.jpg" alt="" />
                <div className="absolute inset-0 flex items-center justify-center bg-gray-200 opacity-0 duration-300 hover:opacity-100">
                  <ul className="flex space-x-2">
                    <li>
                      <a
                        href="#"
                        className="flex h-10 w-10 items-center justify-center rounded bg-blue-600 text-lg text-white duration-300 hover:bg-indigo-500"
                      >
                        <i className="lni lni-facebook-original"></i>
                      </a>
                    </li>
                    <li>
                      <a
                        href="#"
                        className="flex h-10 w-10 items-center justify-center rounded bg-blue-600 text-lg text-white duration-300 hover:bg-blue-400"
                      >
                        <i className="lni lni-twitter-original"></i>
                      </a>
                    </li>
                    <li>
                      <a
                        href="#"
                        className="flex h-10 w-10 items-center justify-center rounded bg-blue-600 text-lg text-white duration-300 hover:bg-red-500"
                      >
                        <i className="lni lni-instagram-original"></i>
                      </a>
                    </li>
                  </ul>
                </div>
                <h3 className="my-2 text-lg font-bold text-gray-800 uppercase">
                  John Doe
                </h3>
                <p>UX UI Designer</p>
              </div>
            </div>
            {/*Repeat Sarah Doe, Rob Hope similarly*/}
          </div>
        </div>
      </section>

      <section id="clients" className="bg-blue-100 py-16">
        <div className="mx-auto px-5 text-center">
          <h2 className="mb-12 text-4xl font-bold tracking-wide text-gray-700">
            As Seen On
          </h2>
          <div className="flex flex-wrap justify-center">
            <div className="w-1/2 p-3 md:w-1/4">
              <img
                className="mx-auto opacity-50 duration-300 hover:opacity-100"
                src="assets/img/clients/img1.svg"
                alt=""
              />
            </div>
            <div className="w-1/2 p-3 md:w-1/4">
              <img
                className="mx-auto opacity-50 duration-300 hover:opacity-100"
                src="assets/img/clients/img2.svg"
                alt=""
              />
            </div>
            <div className="w-1/2 p-3 md:w-1/4">
              <img
                className="mx-auto opacity-50 duration-300 hover:opacity-100"
                src="assets/img/clients/img3.svg"
                alt=""
              />
            </div>
            <div className="w-1/2 p-3 md:w-1/4">
              <img
                className="mx-auto opacity-50 duration-300 hover:opacity-100"
                src="assets/img/clients/img4.svg"
                alt=""
              />
            </div>
          </div>
        </div>
      </section>

      <section id="testimonial" className="bg-gray-800 py-24">
        <div className="mx-auto px-5">
          <div className="flex justify-center">
            <div className="w-full space-y-8 lg:w-7/12">
              {/*testimonial items*/}
              <div className="rounded border border-gray-900 px-8 py-10 text-center">
                <p className="leading-loose text-gray-600">
                  Holisticly empower leveraged ROI...
                </p>
                <div className="mx-auto my-3 h-24 w-24 overflow-hidden rounded-full shadow-md">
                  <img
                    className="h-full w-full object-cover"
                    src="assets/img/testimonial/img1.jpg"
                    alt=""
                  />
                </div>
                <h2 className="mb-2 text-lg font-bold text-blue-600 uppercase">
                  Fajar
                </h2>
                <h3 className="text-sm font-medium text-white">Euphoriya</h3>
              </div>
              {/*Repeat others similarly*/}
            </div>
          </div>
        </div>
      </section>

      <section id="pricing" className="py-24">
        <div className="mx-auto px-5">
          <div className="-m-3 flex flex-wrap justify-center">
            {/*Basic*/}
            <div className="w-3/4 p-3 sm:w-2/3 md:w-1/2 lg:w-1/3">
              <div className="rounded p-6 pb-10 text-center shadow duration-300 hover:bg-blue-100 hover:shadow-lg">
                <h3 className="mb-3 text-sm font-bold text-blue-600 uppercase">
                  Basic
                </h3>
                <p className="mb-5 text-gray-700">
                  <span className="text-2xl font-bold">$12.90</span>{" "}
                  <span className="text-sm font-medium">/ Month</span>
                </p>
                <ul className="mb-16 leading-9 text-gray-500">
                  <li>Up to 5 projects</li>
                  <li>Up to 10 collaborators</li>
                  <li>2GB of storage</li>
                </ul>
                <a
                  href="#"
                  className="rounded-full bg-blue-600 px-10 py-3 text-white duration-300 hover:bg-blue-500"
                >
                  Get It
                </a>
              </div>
            </div>
            {/*Professional, Expert similarly*/}
          </div>
        </div>
      </section>

      {/*Carousel*/}
      <section className="bg-gray-800 py-32">
        <div className="mx-auto px-5">
          <div className="relative">
            <div className="portfolio-carousel space-y-4">
              <img className="w-full" src="assets/img/slide/img1.jpg" alt="" />
              {/*Other slides*/}
            </div>
          </div>
        </div>
      </section>

      {/*Subscribe*/}
      <section id="subscribes" className="bg-blue-100 py-20 text-center">
        <div className="mx-auto px-5">
          <div className="mx-auto w-full sm:w-3/4 md:w-2/3 lg:w-1/2">
            <h4 className="mb-3 text-4xl font-bold tracking-wide text-gray-700">
              Start For Free
            </h4>
            <p className="mb-4 text-sm leading-loose text-gray-600">
              Existing customized ideas...
            </p>
            <form>
              <div className="flex justify-center">
                <input
                  type="email"
                  className="mb-5 w-full max-w-md rounded-full border border-blue-300 bg-white px-5 py-3 duration-300 outline-none focus:border-blue-600"
                  placeholder="Email Address"
                />
                <button
                  type="submit"
                  className="flex h-12 w-12 items-center justify-center rounded-full border-0 bg-blue-600 text-white duration-300 hover:opacity-75"
                >
                  <i className="lni lni-arrow-right"></i>
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>

      {/*Contact*/}
      <section id="contact" className="py-24">
        <div className="mx-auto px-5">
          <h2 className="mb-12 text-center text-4xl font-bold tracking-wide text-gray-700">
            Contact
          </h2>
          <div className="flex flex-wrap">
            <div className="mb-8 w-full md:mb-0 md:w-1/2">
              <h2 className="mb-5 text-xl font-bold text-gray-700 uppercase">
                Contact Form
              </h2>
              <form id="contactForm" action="assets/contact.php">
                <div className="-m-3 flex flex-wrap">
                  <div className="w-full p-3 lg:w-1/2">
                    <input
                      type="text"
                      id="name"
                      name="name"
                      className="mb-5 w-full rounded-full border border-gray-500 bg-white px-4 py-2 duration-300 outline-none"
                      placeholder="Name"
                      required
                    />
                  </div>
                  <div className="w-full p-3 lg:w-1/2">
                    <input
                      type="email"
                      id="email"
                      name="email"
                      className="mb-5 w-full rounded-full border border-gray-500 bg-white px-4 py-2 duration-300 outline-none"
                      placeholder="Email"
                      required
                    />
                  </div>
                  <div className="w-full p-3">
                    <input
                      type="text"
                      id="subject"
                      name="subject"
                      className="mb-5 w-full rounded-full border border-gray-500 bg-white px-4 py-2 duration-300 outline-none"
                      placeholder="Subject"
                      required
                    />
                  </div>
                  <div className="w-full p-3">
                    <textarea
                      id="message"
                      name="message"
                      rows={5}
                      className="mb-5 w-full rounded-lg border border-gray-500 bg-white px-4 py-2 duration-300 outline-none"
                      placeholder="Your Message"
                      required
                    ></textarea>
                  </div>
                  <div className="w-full p-3">
                    <button
                      type="submit"
                      className="rounded-full bg-blue-600 px-10 py-3 text-white duration-300 hover:bg-blue-500"
                    >
                      Send Message
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <div className="w-full md:w-1/2">
              <h2 className="mb-5 text-xl font-bold text-gray-700 uppercase">
                Get In Touch
              </h2>
              <div className="space-y-6">
                <div className="flex items-center">
                  <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-600 text-xl text-white">
                    <i className="lni lni-map-marker"></i>
                  </div>
                  <p className="pl-3">Skopje, Macedonia</p>
                </div>
                <div className="flex items-center">
                  <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-600 text-xl text-white">
                    <i className="lni lni-envelope"></i>
                  </div>
                  <div className="pl-3">
                    <a href="#" className="block">
                      email@gmail.com
                    </a>
                    <a href="#" className="block">
                      tomsaulnier@gmail.com
                    </a>
                  </div>
                </div>
                <div className="flex items-center">
                  <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-600 text-xl text-white">
                    <i className="lni lni-phone-set"></i>
                  </div>
                  <div className="pl-3">
                    <a href="#" className="block">
                      +42 374 5967
                    </a>
                    <a href="#" className="block">
                      +99 123 5967
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/*Todo: Map*/}
    </div>
  );
}
