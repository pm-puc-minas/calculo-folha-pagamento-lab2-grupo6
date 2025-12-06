import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const SignUpPage: React.FC = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userType, setUserType] = useState("EMPLOYEE");
  const navigate = useNavigate();

  const handleSubmit = async () => {
    try {
      const response = await axios.post("http://localhost:8080/users", {
        name,
        email,
        password,
        userType,
        isAdmin: false,
      });
      navigate("/login");
    } catch (error) {
      console.error("Falha ao criar o usuário", error);
    }
  };

  return (
    <div>
      <h2>Cadastrar</h2>
      <input
        type="text"
        placeholder="Nome"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
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
      <select onChange={(e) => setUserType(e.target.value)} value={userType}>
        <option value="EMPLOYEE">Funcionário</option>
        <option value="PAYROLL_ADMIN">Administrador</option>
      </select>
      <button onClick={handleSubmit}>Cadastrar</button>
      <div>
        <button onClick={() => navigate("/login")}>
          Já tem conta? Faça login
        </button>
      </div>
    </div>
  );
};

export default SignUpPage;
