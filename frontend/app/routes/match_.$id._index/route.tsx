import type { Route } from "~/../.react-router/types/app/routes/match_.$id._index/+types/route";
import React from "react";
import PlayerBoard from "./PlayerBoard";
import EnemyBoard from "./EnemyBoard";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Match" },
    { name: "description", content: "Good luck!" },
  ];
};

export default function BattlePage() {
  return (
    <div className="flex flex-col lg:flex-row gap-4">
      <div className="p-2 m-2 grow">
        <h2 className="text-eerie_black-500 dark:text-seasalt-500 mb-4 font-bold text-2xl md::text-3xl text-center bg-alice_blue-200">
          Your Fleet
        </h2>
        <EnemyBoard />
      </div>

      <div className="border-onyx-800 dark:border-white-500 min-h-full border"/>

      <div className="p-2 m-2 grow">
        <h2 className="text-eerie_black-500 dark:text-seasalt-500 mb-4 font-bold text-2xl md::text-3xl text-center bg-alice_blue-200">
          Enemy Fleet
        </h2>
        <PlayerBoard />
      </div>
    </div>
  );
}
