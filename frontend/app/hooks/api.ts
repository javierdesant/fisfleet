import { getAuthHeaders } from "./authService";

export async function apiFetch(url: string, options: RequestInit = {}) {
  const rawHeaders = {
    ...getAuthHeaders(),
    "Content-Type": "application/json",
    ...(options.headers || {}),
  };
  // Remove undefined header values
  const headers: Record<string, string> = Object.fromEntries(
    Object.entries(rawHeaders).filter(([_, v]) => v !== undefined)
  );
  const res = await fetch(url, { ...options, headers });
  if (!res.ok) throw new Error("Error en la petición");
  return res.json();
}