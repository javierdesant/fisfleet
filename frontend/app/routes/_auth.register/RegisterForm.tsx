import React from "react";
import { useForm } from "react-hook-form";

export type RegisterFormValues = {
  username: string;
  alias: string;
  password: string;
};

type Props = {
  onSubmit: (data: RegisterFormValues) => void;
  onError: (errors: any) => void;
};

export default function RegisterForm({ onSubmit, onError }: Props) {
  const { register, handleSubmit, formState: { errors } } = useForm<RegisterFormValues>();

  return (
    <form
      onSubmit={handleSubmit(onSubmit, onError)}
      className="max-w-md mx-auto flex flex-col gap-6 p-8 bg-white dark:bg-gray-900 rounded-xl shadow-2xl"
      noValidate
    >
      <h2 className="text-2xl font-bold text-center text-blue-800 dark:text-blue-200 mb-2">Registro</h2>
      <p className="text-center text-gray-500 dark:text-gray-300 mb-4">Crea tu cuenta UPM para jugar</p>
      <div>
        <label htmlFor="username" className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
          Usuario UPM
        </label>
        <input
          id="username"
          {...register("username", { required: "Usuario requerido" })}
          placeholder="Usuario UPM"
          className={`w-full rounded-lg bg-gray-100 dark:bg-gray-800 py-3 px-4 text-base focus:ring-2 focus:ring-blue-500 focus:outline-none border ${errors.username ? "border-red-500" : "border-gray-300"}`}
        />
        {errors.username && <span className="mt-1 block text-sm text-red-500">{errors.username.message}</span>}
      </div>
      <div>
        <label htmlFor="alias" className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
          Alias
        </label>
        <input
          id="alias"
          {...register("alias", { required: "Alias requerido" })}
          placeholder="Alias visible"
          className={`w-full rounded-lg bg-gray-100 dark:bg-gray-800 py-3 px-4 text-base focus:ring-2 focus:ring-blue-500 focus:outline-none border ${errors.alias ? "border-red-500" : "border-gray-300"}`}
        />
        {errors.alias && <span className="mt-1 block text-sm text-red-500">{errors.alias.message}</span>}
      </div>
      <div>
        <label htmlFor="password" className="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">
          Contraseña
        </label>
        <input
          id="password"
          {...register("password", { required: "Contraseña requerida" })}
          type="password"
          placeholder="Contraseña"
          className={`w-full rounded-lg bg-gray-100 dark:bg-gray-800 py-3 px-4 text-base focus:ring-2 focus:ring-blue-500 focus:outline-none border ${errors.password ? "border-red-500" : "border-gray-300"}`}
        />
        {errors.password && <span className="mt-1 block text-sm text-red-500">{errors.password.message}</span>}
      </div>
      <button
        type="submit"
        className="w-full rounded-lg bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 text-lg transition"
      >
        Registrarse
      </button>
    </form>
  );
}