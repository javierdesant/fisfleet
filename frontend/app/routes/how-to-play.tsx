import type { Route } from "~/../.react-router/types/app/routes/match_.$id/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "How to play" },
    { name: "description", content: "How to play page" },
  ];
};

export default function HowToPlayPage() {
  return <h1>HowToPlay</h1>;
}