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
  const { register, handleSubmit, formState } = useForm<LoginFormValues>({
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const { errors } = formState;

  return (
    <form
      onSubmit={handleSubmit(onSubmit, onError)}
      className="space-y-6 p-6 sm:p-8 md:p-10 lg:p-12"
      noValidate
    >
      <div>
        <div className="relative">
          <div className="absolute inset-y-0 left-3 flex items-center">
            <UserIcon />
          </div>
          <input
            id="username"
            type="text"
            className={`w-full rounded bg-gray-200 py-3 pr-4 pl-12 text-sm focus:ring focus:ring-blue-500 focus:outline-none md:text-base lg:py-4 lg:text-lg ${
              errors.username ? "border-red-500" : "border-gray-300"
            }`}
            placeholder="Username"
            {...register("username", {
              required: "Username is required",
            })}
          />
        </div>
        {errors.username && (
          <span className="mt-1 block text-sm text-red-500">
            {errors.username.message}
          </span>
        )}
      </div>
      <div>
        <div className="relative">
          <div className="absolute inset-y-0 left-3 flex items-center">
            <LockIcon />
          </div>
          <input
            id="password"
            type="password"
            className={`w-full rounded bg-gray-200 py-3 pr-4 pl-12 text-sm focus:ring focus:ring-blue-500 focus:outline-none md:text-base lg:py-4 lg:text-lg ${
              errors.password ? "border-red-500" : "border-gray-300"
            }`}
            placeholder="Password"
            {...register("password", {
              required: "Password is required",
            })}
          />
        </div>
        {errors.password && (
          <span className="mt-1 block text-sm text-red-500">
            {errors.password.message}
          </span>
        )}
      </div>
      <button
        type="submit"
        className="w-full rounded bg-gradient-to-b from-blue-500 to-blue-700 py-3 text-sm font-medium text-white uppercase hover:opacity-90 focus:ring focus:ring-blue-500 focus:outline-none lg:py-4 lg:text-base dark:from-gray-700 dark:to-gray-900"
      >
        Login
      </button>
    </form>
  );
}
