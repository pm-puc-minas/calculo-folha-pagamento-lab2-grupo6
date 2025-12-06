import React, { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

const SignUpPage: React.FC = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userType, setUserType] = useState("EMPLOYEE");

  const [grossSalary, setGrossSalary] = useState<number | string>("");
  const [cpf, setCpf] = useState("");
  const [position, setPosition] = useState("");
  const [dependents, setDependents] = useState<number | string>(0);

  const navigate = useNavigate();

  const handleSubmit = async () => {
    try {
      const payload: any = {
        name,
        email,
        password,
        userType,
        isAdmin: userType === "PAYROLL_ADMIN",
      };

      if (userType === "EMPLOYEE") {
        payload.grossSalary = Number(grossSalary);
        payload.cpf = cpf;
        payload.position = position;
        payload.dependents = Number(dependents);
      }

      await api.post("/users", payload);

      alert("Usuário criado com sucesso!");

      setName("");
      setEmail("");
      setPassword("");
      setUserType("EMPLOYEE");

      setGrossSalary("");
      setCpf("");
      setPosition("");
      setDependents(0);
    } catch (error) {
      console.error("Falha ao criar o usuário", error);
      alert(
        "Erro ao criar usuário. Verifique se você está logado como Admin e se os dados estão corretos."
      );
    }
  };

  return (
    <div className="login-container">
      <div
        className="login-card"
        style={{ maxWidth: "500px", maxHeight: "90vh", overflowY: "auto" }}
      >
        <h2>Cadastrar Novo Usuário</h2>
        <p style={{ textAlign: "center", marginBottom: "20px", color: "#666" }}>
          Preencha os dados do novo colaborador ou administrador.
        </p>

        <input
          className="login-input"
          type="text"
          placeholder="Nome Completo"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          className="login-input"
          type="email"
          placeholder="Email Corporativo"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          className="login-input"
          type="password"
          placeholder="Senha Inicial"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <label
          style={{ display: "block", marginBottom: "8px", fontWeight: "bold" }}
        >
          Tipo de Perfil:
        </label>
        <select
          className="login-input"
          onChange={(e) => setUserType(e.target.value)}
          value={userType}
        >
          <option value="EMPLOYEE">Funcionário (Comum)</option>
          <option value="PAYROLL_ADMIN">Administrador (Payroll)</option>
        </select>

        {/* --- SEÇÃO CONDICIONAL: Aparece apenas se for EMPLOYEE --- */}
        {userType === "EMPLOYEE" && (
          <div
            style={{
              marginTop: "10px",
              borderTop: "1px dashed #ccc",
              paddingTop: "15px",
            }}
          >
            <h4 style={{ marginBottom: "10px", color: "#4b5563" }}>
              Dados do Contrato
            </h4>

            <input
              className="login-input"
              type="text"
              placeholder="CPF (apenas números)"
              value={cpf}
              onChange={(e) => setCpf(e.target.value)}
              maxLength={14}
            />

            <input
              className="login-input"
              type="text"
              placeholder="Cargo / Posição"
              value={position}
              onChange={(e) => setPosition(e.target.value)}
            />

            <div style={{ display: "flex", gap: "10px" }}>
              <div style={{ flex: 1 }}>
                <label style={{ fontSize: "0.8rem", fontWeight: "bold" }}>
                  Salário Bruto
                </label>
                <input
                  className="login-input"
                  type="number"
                  placeholder="Ex: 3000.00"
                  value={grossSalary}
                  onChange={(e) => setGrossSalary(e.target.value)}
                  step="0.01"
                />
              </div>
              <div style={{ flex: 1 }}>
                <label style={{ fontSize: "0.8rem", fontWeight: "bold" }}>
                  Dependentes
                </label>
                <input
                  className="login-input"
                  type="number"
                  placeholder="Qtd"
                  value={dependents}
                  onChange={(e) => setDependents(e.target.value)}
                  min="0"
                />
              </div>
            </div>
          </div>
        )}
        {/* --- FIM DA SEÇÃO CONDICIONAL --- */}

        <button className="login-button" onClick={handleSubmit}>
          Cadastrar Usuário
        </button>

        <button
          className="login-button"
          style={{ backgroundColor: "#6b7280", marginTop: "10px" }}
          onClick={() => navigate("/dashboard")}
        >
          Voltar ao Dashboard
        </button>
      </div>
    </div>
  );
};

export default SignUpPage;
