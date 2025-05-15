import type { Route } from "~/../.react-router/types/app/routes/_auth.profile/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Profile" },
    { name: "description", content: "User profile page" },
  ];
};

export default function ProfilePage() {
  return <h1>Profile</h1>;
}