import type { Route } from "../../../.react-router/types/app/routes/_landing._index/+types";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Landing" },
    { name: "description", content: "Landing page" },
  ];
};

export default function LandingPage() {
  return <h1>Landing</h1>;
}