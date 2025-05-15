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
    <div className="grid grid-cols-2 gap-4">
      <div className="border border-onyx-500 dark:border-white-500 p-2 rounded-lg bg-alice_blue-500 dark:bg-oxford_blue-500">
        <h2 className="text-xl font-semibold mb-2 text-eerie_black-500 dark:text-seasalt-500">Enemy Fleet</h2>
        <EnemyBoard />
      </div>
      <div className="border border-onyx-500 dark:border-white-500 p-2 rounded-lg bg-alice_blue-500 dark:bg-oxford_blue-500">
        <h2 className="text-xl font-semibold mb-2 text-eerie_black-500 dark:text-seasalt-500">Your Fleet</h2>
        <PlayerBoard />
      </div>
    </div>
  );
}
