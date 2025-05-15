import { Outlet } from "react-router-dom";

function DefaultLayout() {
  return (
    <div className="flex flex-col min-h-screen">
      {/* Encabezado */}
      <header className="bg-blue-500 text-white p-4">
        <h1 className="text-2xl font-bold">Default Layout</h1>
      </header>

      {/* Contenido principal */}
      <main className="flex-grow p-4">
        <Outlet />
      </main>

      {/* Pie de página */}
      <footer className="bg-gray-800 text-white text-center p-4">
        <p>© 2025 - Todos los derechos reservados</p>
      </footer>
    </div>
  );
}

export default DefaultLayout;