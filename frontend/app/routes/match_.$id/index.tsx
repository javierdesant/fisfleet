import { Outlet } from "react-router";

export default function MatchLayout() {
  return (
    <div className="from-alice_blue-500 to-alice_blue-600 dark:from-oxford_blue-500 dark:to-oxford_blue-400 text-eerie_black-500 dark:text-seasalt-500 flex grow flex-col bg-gradient-to-br">
      <Outlet />
    </div>
  );
}
