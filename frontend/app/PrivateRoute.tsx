import React from "react";
import { Navigate } from "react-router-dom";

type Props = {
  readonly children: React.ReactNode;
};

export default function PrivateRoute({ children }: Props) {
  const jwt = localStorage.getItem("jwt");
  if (!jwt) {
    return <Navigate to="/login" replace />;
  }
  return <>{children}</>;
}