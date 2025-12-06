// src/services/api.ts
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Interceptor para injetar o Basic Auth automaticamente
api.interceptors.request.use((config) => {
  const userStr = localStorage.getItem("user");
  const authHeader = localStorage.getItem("authHeader"); // Vamos salvar isso no login

  if (authHeader) {
    config.headers.Authorization = authHeader;
  }
  return config;
});

export default api;
