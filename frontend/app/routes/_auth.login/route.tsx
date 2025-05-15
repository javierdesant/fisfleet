import type { Route } from "~/../.react-router/types/app/routes/_auth.login/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Login" },
    { name: "description", content: "Login page" },
  ];
};

export default function LoginPage() {
  return <h1>Login</h1>;
}