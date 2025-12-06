import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css"; // Importando o estilo

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    // Limpa erro anterior ao tentar novamente
    setError("");

    if (!email || !password) {
      setError("Por favor, preencha todos os campos.");
      return;
    }

    try {
      // 1. Tenta logar (Validação)
      const response = await axios.post("http://localhost:8080/users/login", {
        email,
        password,
      });

      // 2. Gera e salva o token
      const token = "Basic " + btoa(`${email}:${password}`);
      localStorage.setItem("authHeader", token);

      // 3. Salva os dados do usuário
      localStorage.setItem("user", JSON.stringify(response.data));

      // 4. Redirecionamento
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

  // Permite logar apertando ENTER no campo de senha
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
