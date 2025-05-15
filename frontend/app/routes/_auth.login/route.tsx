import type { Route } from "~/../.react-router/types/app/routes/_auth.login/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Login" },
    { name: "description", content: "Login page" },
  ];
};

export default function LoginPage() {
  return (
    <form className="max-w-sm mx-auto bg-white dark:bg-oxford_blue-500 p-6 rounded-lg shadow-md">
      <h1 className="text-2xl mb-4 text-eerie_black-500 dark:text-seasalt-500">Login</h1>
      {/* TODO: Add login fields here */}
    </form>
  );
}