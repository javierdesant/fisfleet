import type { Route } from "~/../.react-router/types/app/routes/_auth.profile/+types/route";
import React from "react";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Profile" },
    { name: "description", content: "User profile page" },
  ];
};

export default function ProfilePage() {
  return (
    <div className="max-w-md mx-auto bg-white dark:bg-oxford_blue-500 p-6 rounded-lg shadow-md">
      <h1 className="text-2xl mb-4 text-eerie_black-500 dark:text-seasalt-500">Your Profile</h1>
      {/* TODO: Profile details here */}
    </div>
  );
}