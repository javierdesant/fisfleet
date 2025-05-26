const API_URL = "http://localhost:8080/api";

export async function login(username: string, password: string) {
  const res = await fetch(`${API_URL}/auth/authenticate`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });
  if (!res.ok) throw new Error("Credenciales incorrectas");
  return res.json();
}

export async function register(username: string, alias: string, password: string) {
  const res = await fetch(`${API_URL}/users/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, alias, password }),
  });
  if (!res.ok) throw new Error("Error al registrar usuario");
  return res.json();
}

export function logout() {
  localStorage.removeItem("jwt");
}

export function getAuthHeaders() {
  const jwt = localStorage.getItem("jwt");
  return jwt ? { Authorization: `Bearer ${jwt}` } : {};
}