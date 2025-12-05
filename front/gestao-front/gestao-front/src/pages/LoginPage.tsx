import React, { useState } from "react";
import axios from "axios";

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/login", {
        email,
        password,
      });
      // Redirecionar para a página principal após o login
      console.log("Login sucesso", response.data);
    } catch (error) {
      setError("Falha no login. Verifique suas credenciais.");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      {error && <div style={{ color: "red" }}>{error}</div>}
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Senha"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>Entrar</button>
      <div>
        <button onClick={() => (window.location.href = "/cadastro")}>
          Cadastrar
        </button>
      </div>
    </div>
  );
};

export default LoginPage;
