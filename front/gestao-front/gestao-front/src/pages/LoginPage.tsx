import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError("");

    if (!email || !password) {
      setError("Por favor, preencha todos os campos.");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/users/login", {
        email,
        password,
      });

      const token = "Basic " + btoa(`${email}:${password}`);
      localStorage.setItem("authHeader", token);

      localStorage.setItem("user", JSON.stringify(response.data));

      if (response.data.userType === "PAYROLL_ADMIN") {
        navigate("/dashboard");
      } else {
        navigate("/employee/payroll");
      }
    } catch (error) {
      localStorage.removeItem("authHeader");
      localStorage.removeItem("user");
      setError("Falha no login. Verifique suas credenciais.");
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      handleLogin();
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2>Acesso Geral</h2>

        {error && <div className="error-message">{error}</div>}

        <input
          className="login-input"
          type="email"
          placeholder="E-mail Corporativo"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          className="login-input"
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          onKeyDown={handleKeyDown}
        />

        <button className="login-button" onClick={handleLogin}>
          Acessar Sistema
        </button>

        {/* Botão de cadastro removido conforme solicitado */}
      </div>
    </div>
  );
};

export default LoginPage;
