import type { Route } from "../../../.react-router/types/app/routes/_landing._index/+types/route";
import React from "react";
import Hero from "./Hero";
import ObjectivesSection from "./ObjectivesSection";
import AccessibilitySection from "./AccessibilitySection";
import ProjectStructureSection from "./ProjectStructureSection";
import HowToPlaySection from "./HowToPlaySection";
import TechnologiesSection from "./TechnologiesSection";
import TeamSection from "./TeamSection";
import ServicesSection from "./ServicesSection";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Landing" },
    { name: "description", content: "Landing page" },
  ];
};

export default function HomePage() {
  return (
    <div>
      <Hero />
      <ServicesSection />
      <ObjectivesSection />
      <AccessibilitySection />
      <ProjectStructureSection />
      <HowToPlaySection />
      <TechnologiesSection />
      <TeamSection />
    </div>
  );
}
