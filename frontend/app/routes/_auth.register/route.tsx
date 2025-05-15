import type { Route } from "~/../.react-router/types/app/routes/_auth.register/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Register" },
    { name: "description", content: "Register page" },
  ];
};

export default function RegisterPage() {
  return (
    <form className="max-w-sm mx-auto bg-white dark:bg-oxford_blue-500 p-6 rounded-lg shadow-md">
      <h1 className="text-2xl mb-4 text-eerie_black-500 dark:text-seasalt-500">Register</h1>
      {/* TODO: Add register fields here */}
    </form>
  );
}