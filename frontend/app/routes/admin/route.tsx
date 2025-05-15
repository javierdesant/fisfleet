import type { Route } from "~/../.react-router/types/app/routes/admin/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Admin" },
    { name: "description", content: "Admin dashboard" },
  ];
};

export default function AdminPage() {
  return (
    <div className="p-4 bg-onyx-500 dark:bg-cadet_gray-500 rounded-lg text-seasalt-500">
      <h1 className="text-2xl mb-3">Admin Dashboard</h1>
      <p>Manage matches and users here.</p>
    </div>
  );
}