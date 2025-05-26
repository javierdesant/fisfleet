import React, { useState } from "react";
import type { FieldErrors } from "react-hook-form";
import RegisterForm, { type RegisterFormValues } from "./RegisterForm";
import { register as registerUser } from "app/hooks/authService";
import { useNavigate } from "react-router-dom";

export default function RegisterPage() {
  const [showSuccess, setShowSuccess] = useState(false);
  const navigate = useNavigate();

  const onSubmit = async (data: RegisterFormValues) => {
    try {
      const response = await registerUser(data.username, data.alias, data.password);
      localStorage.setItem("jwt", response.jwt);
      setShowSuccess(true);
      setTimeout(() => {
        setShowSuccess(false);
        navigate("/");
      }, 1500);
    } catch (e) {
      if (e instanceof Error) {
        alert("Registro fallido: " + e.message);
      } else {
        alert("Registro fallido: Error desconocido");
      }
    }
  };

  const onError = (errors: FieldErrors<RegisterFormValues>) => {
    if (errors.username?.message) console.log("Username Error", errors.username.message);
    if (errors.alias?.message) console.log("Alias Error", errors.alias.message);
    if (errors.password?.message) console.log("Password Error", errors.password.message);
  };

  return (
    <section className="flex min-h-screen items-center justify-center bg-alice_blue-500 px-5">
      <div className="w-full max-w-md p-8">
        {showSuccess && (
          <div className="fixed top-10 left-1/2 z-50 flex -translate-x-1/2 items-center justify-center">
            <div className="rounded-lg bg-white p-6 text-center shadow-lg">
              <h2 className="mb-4 text-xl font-semibold text-green-600">
                ¡Registro exitoso!
              </h2>
              <p className="text-eerie_black-500">Bienvenido a Fisfleet.</p>
            </div>
          </div>
        )}
        <RegisterForm onSubmit={onSubmit} onError={onError} />
      </div>
    </section>
  );
}