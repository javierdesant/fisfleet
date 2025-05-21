import { Outlet } from "react-router";
import Header from "./Header";
import Footer from "./Footer";

export default function LandingLayout() {
  return (
    <div>
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}
