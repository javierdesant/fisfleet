import { Outlet } from "react-router";

export default function MatchLayout() {
  return (
    <div className="bg-marian_blue-500 dark:bg-marian_blue-500 p-4 rounded-lg shadow-md">
      <Outlet />
    </div>
  );
}
