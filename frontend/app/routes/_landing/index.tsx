import { Outlet } from "react-router-dom";

export default function LandingLayout() {
  return (
    <div className="bg-picton_blue-500 dark:bg-marian_blue-500 rounded-2xl p-4 shadow-lg">
      <Outlet />
    </div>
  );
}
