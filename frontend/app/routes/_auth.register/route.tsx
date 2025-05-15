import type { Route } from "~/../.react-router/types/app/routes/_auth.register/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Register" },
    { name: "description", content: "Register page" },
  ];
};

export default function RegisterPage() {
  return <h1>Register</h1>;
}