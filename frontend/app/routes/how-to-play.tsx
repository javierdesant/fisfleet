import type { Route } from "~/../.react-router/types/app/routes/+types/how-to-play"
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "How to play" },
    { name: "description", content: "How to play page" },
  ];
};

export default function HowToPlayPage() {
  return (
    <div className="bg-alice_blue-500 dark:bg-oxford_blue-500 p-6 rounded-2xl border border-gunmetal-500 dark:border-white-500">
      <h1 className="text-3xl font-bold mb-4">How To Play</h1>
      <p>Use your fleet to sink the enemy ships in this cartoon naval battle!</p>
    </div>
  );
}
