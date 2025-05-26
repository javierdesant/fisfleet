import type { Route } from "~/../.react-router/types/app/routes/_auth.login/+types/route";
import React, { useState } from "react";
import type { FieldErrors } from "react-hook-form";
import LoginForm, { type LoginFormValues } from "./LoginForm";
import EnvelopeIcon from "./icons/EnvelopeIcon";
import { login } from "app/hooks/authService";

export const meta: Route.MetaFunction = () => {
  return [
    { title: "Login" },
    { name: "description", content: "Login page" },
  ];
};

export default function LoginPage() {

  const [showSuccess, setShowSuccess] = useState(false);

const onSubmit = async (data: LoginFormValues) => {
  try {
    const response = await login(data.username, data.password);
    localStorage.setItem("jwt", response.jwt);
    setShowSuccess(true);
    // Redirige o actualiza estado de usuario autenticado aquí
  } catch (e) {
    if (e instanceof Error) {
      alert("Login fallido: " + e.message);
    } else {
      alert("Login fallido: Error desconocido");
    }
  }
};

  const onError = (errors: FieldErrors<LoginFormValues>) => {
    if (errors.username?.message)
      console.log("Username Error", errors.username.message);
    if (errors.password?.message)
      console.log("Password Error", errors.password.message);
  };

  return (
    <section className="flex min-h-screen items-center justify-center bg-alice_blue-500 px-5 sm:px-10 md:px-12 lg:px-16 dark:bg-oxford_blue-500">
      <div className="shadow-3xl relative w-full max-w-md rounded-xl bg-white dark:bg-oxford_blue-500 sm:max-w-lg md:max-w-xl lg:max-w-3xl">
        <div className="absolute left-1/2 -translate-x-1/2 -translate-y-1/2 transform rounded-full bg-gunmetal-500 dark:bg-picton_blue-500 p-4 shadow-md sm:p-6 md:p-8 lg:p-10">
          <EnvelopeIcon />
        </div>

        {showSuccess && (
          <div className="fixed top-10 left-1/2 z-50 flex -translate-x-1/2 items-center justify-center">
            <div className="rounded-lg bg-white dark:bg-oxford_blue-500 p-6 text-center shadow-lg">
              <h2 className="mb-4 text-xl font-semibold text-light_red-500 dark:text-light_red-dark-500">
                ¡Inicio de sesión exitoso!
              </h2>
              <p className="text-eerie_black-500 dark:text-seasalt-500">Bienvenido a tu cuenta.</p>
              <button
                onClick={() => setShowSuccess(false)}
                className="mt-4 rounded-lg bg-mustard-500 px-4 py-2 text-white transition hover:bg-amber-500"
              >
                Cerrar
              </button>
            </div>
          </div>
        )}

        <LoginForm onSubmit={onSubmit} onError={onError} />
      </div>
    </section>
  );
}