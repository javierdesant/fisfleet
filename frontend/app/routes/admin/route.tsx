import type { Route } from "~/../.react-router/types/app/routes/admin/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Admin" },
    { name: "description", content: "Admin panel" },
  ];
};

export default function adminPage() {
  return <h1>admin</h1>;
}