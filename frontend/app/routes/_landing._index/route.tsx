import type { Route } from "../../../.react-router/types/app/routes/_landing._index/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Landing" },
    { name: "description", content: "Landing page" },
  ];
};

export default function HomePage() {
  return <h2 className="text-2xl">Welcome to Battle Fleet!</h2>;
}
