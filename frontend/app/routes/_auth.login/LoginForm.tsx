import React from "react";
import { useForm, type FieldErrors, type SubmitHandler } from "react-hook-form";
import UserIcon from "~/routes/_auth.login/icons/UserIcon";
import LockIcon from "~/routes/_auth.login/icons/LockIcon";

export interface LoginFormValues {
  username: string;
  password: string;
}

interface LoginFormProps {
  onSubmit: SubmitHandler<LoginFormValues>;
  onError: (errors: FieldErrors<LoginFormValues>) => void;
}

export default function LoginForm({ onSubmit, onError }: LoginFormProps) {
  const { register, handleSubmit, formState: { errors } } = useForm<LoginFormValues>({
    defaultValues: { username: "", password: "" },
  });

  return (
    <form
      onSubmit={handleSubmit(onSubmit, onError)}
      className="flex flex-col gap-6 p-8 bg-white dark:bg-oxford_blue-500 rounded-xl shadow-2xl w-full"
      noValidate
    >
      <h2 className="text-2xl font-bold text-center text-blue-800 dark:text-blue-200 mb-2 mt-10">Iniciar sesión</h2>
      <p className="text-center text-gray-500 dark:text-gray-300 mb-4">Accede con tu cuenta UPM</p>
      <div>
        <label htmlFor="username" className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
          Usuario
        </label>
        <div className="relative">
          <span className="absolute inset-y-0 left-3 flex items-center pointer-events-none">
            <UserIcon />
          </span>
          <input
            id="username"
            type="text"
            autoComplete="username"
            className={`w-full rounded-lg bg-gray-100 dark:bg-gray-800 py-3 pr-4 pl-12 text-base focus:ring-2 focus:ring-blue-500 focus:outline-none border ${errors.username ? "border-red-500" : "border-gray-300"}`}
            placeholder="Usuario UPM"
            {...register("username", { required: "Usuario requerido" })}
          />
        </div>
        {errors.username && (
          <span className="mt-1 block text-sm text-red-500">{errors.username.message}</span>
        )}
      </div>
      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
          Contraseña
        </label>
        <div className="relative">
          <span className="absolute inset-y-0 left-3 flex items-center pointer-events-none">
            <LockIcon />
          </span>
          <input
            id="password"
            type="password"
            autoComplete="current-password"
            className={`w-full rounded-lg bg-gray-100 dark:bg-gray-800 py-3 pr-4 pl-12 text-base focus:ring-2 focus:ring-blue-500 focus:outline-none border ${errors.password ? "border-red-500" : "border-gray-300"}`}
            placeholder="Contraseña"
            {...register("password", { required: "Contraseña requerida" })}
          />
        </div>
        {errors.password && (
          <span className="mt-1 block text-sm text-red-500">{errors.password.message}</span>
        )}
      </div>
      <button
        type="submit"
        className="w-full rounded-lg bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 text-lg transition"
      >
        Entrar
      </button>
    </form>
  );
}