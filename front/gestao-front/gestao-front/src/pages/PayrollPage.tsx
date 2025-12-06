import React, { useState } from "react";
import api from "../services/api"; // Importe o axios configurado
import { User, DegreeUnhealthiness } from "../types";

const PayrollPage: React.FC = () => {
  // Estados de Controle
  const [step, setStep] = useState<"SEARCH" | "FORM">("SEARCH");
  const [searchId, setSearchId] = useState("");
  const [loading, setLoading] = useState(false);

  // Dados do Funcionário e Parametros de Folha
  const [employee, setEmployee] = useState<User | null>(null);

  // Inputs do Formulário (Valores que serão atualizados via PATCH)
  const [formData, setFormData] = useState({
    hoursWorkedMonth: 0,
    daysWorked: 0,
    actualVTCost: 0,
    degreeUnhealthiness: DegreeUnhealthiness.MINIMUM,
    hasDanger: false,
    // Parametros para o POST de cálculo
    month: new Date().getMonth() + 1,
    year: new Date().getFullYear(),
  });

  // PASSO 1: Buscar Funcionário
  const handleSearch = async () => {
    if (!searchId) return;
    setLoading(true);
    try {
      const response = await api.get<User>(`/users/${searchId}`);
      const emp = response.data;

      // Só permite avançar se for Employee
      if (emp.userType !== "EMPLOYEE") {
        alert("O ID informado não pertence a um funcionário (Employee).");
        setLoading(false);
        return;
      }

      setEmployee(emp);

      // Preenche o formulário com os dados ATUAIS do banco
      setFormData({
        hoursWorkedMonth: emp.hoursWorkedMonth || 220,
        daysWorked: emp.daysWorked || 20,
        actualVTCost: emp.actualVTCost || 0,
        degreeUnhealthiness:
          emp.degreeUnhealthiness || DegreeUnhealthiness.MINIMUM,
        hasDanger: emp.hasDanger || false,
        month: new Date().getMonth() + 1,
        year: new Date().getFullYear(),
      });

      setStep("FORM");
    } catch (error) {
      alert("Funcionário não encontrado!");
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  // PASSO 2: Atualizar Dados e Calcular
  const handleSubmit = async () => {
    if (!employee) return;
    setLoading(true);

    try {
      // 1. Atualizar dados variáveis do funcionário (PATCH)
      // O endpoint espera um objeto Employee no body
      await api.patch(`/users/employee/${employee.id}`, {
        hoursWorkedMonth: formData.hoursWorkedMonth,
        daysWorked: formData.daysWorked,
        actualVTCost: formData.actualVTCost,
        degreeUnhealthiness: formData.degreeUnhealthiness,
        hasDanger: formData.hasDanger,
      });

      // 2. Calcular a folha (POST)
      // O endpoint espera PayrollDTO: { employeeId, month, year }
      await api.post("/api/payroll/calculate", {
        employeeId: employee.id,
        month: formData.month,
        year: formData.year,
      });

      alert("Dados atualizados e Folha de Pagamento calculada com sucesso!");
      // Resetar ou redirecionar
      setStep("SEARCH");
      setSearchId("");
    } catch (error) {
      console.error("Erro no processo:", error);
      alert("Erro ao processar. Verifique o console.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Gerar Nova Folha de Pagamento</h2>

      {step === "SEARCH" && (
        <div className="card">
          <label>Informe o ID do Funcionário:</label>
          <input
            type="number"
            value={searchId}
            onChange={(e) => setSearchId(e.target.value)}
            placeholder="Ex: 1"
          />
          <button onClick={handleSearch} disabled={loading}>
            {loading ? "Buscando..." : "Buscar e Iniciar"}
          </button>
        </div>
      )}

      {step === "FORM" && employee && (
        <div className="form-container">
          <h3>Configurar Parâmetros para: {employee.name}</h3>

          <div className="row">
            <label>Mês de Referência:</label>
            <input
              type="number"
              max={12}
              min={1}
              value={formData.month}
              onChange={(e) =>
                setFormData({ ...formData, month: Number(e.target.value) })
              }
            />
            <label>Ano:</label>
            <input
              type="number"
              value={formData.year}
              onChange={(e) =>
                setFormData({ ...formData, year: Number(e.target.value) })
              }
            />
          </div>

          <hr />
          <h4>Dados Variáveis (Atualizará o Cadastro)</h4>

          <div className="input-group">
            <label>Horas Trabalhadas no Mês:</label>
            <input
              type="number"
              value={formData.hoursWorkedMonth}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  hoursWorkedMonth: Number(e.target.value),
                })
              }
            />
          </div>

          <div className="input-group">
            <label>Dias Trabalhados:</label>
            <input
              type="number"
              value={formData.daysWorked}
              onChange={(e) =>
                setFormData({ ...formData, daysWorked: Number(e.target.value) })
              }
            />
          </div>

          <div className="input-group">
            <label>Custo VT Real:</label>
            <input
              type="number"
              value={formData.actualVTCost}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  actualVTCost: Number(e.target.value),
                })
              }
            />
          </div>

          <div className="input-group">
            <label>Insalubridade:</label>
            <select
              value={formData.degreeUnhealthiness}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  degreeUnhealthiness: e.target.value as DegreeUnhealthiness,
                })
              }
            >
              <option value="MINIMUM">Mínimo</option>
              <option value="MEDIUM">Médio</option>
              <option value="MAXIMUM">Máximo</option>
            </select>
          </div>

          <div className="input-group">
            <label>
              <input
                type="checkbox"
                checked={formData.hasDanger}
                onChange={(e) =>
                  setFormData({ ...formData, hasDanger: e.target.checked })
                }
              />
              Possui Periculosidade?
            </label>
          </div>

          <div className="actions">
            <button onClick={() => setStep("SEARCH")}>Cancelar</button>
            <button
              onClick={handleSubmit}
              disabled={loading}
              style={{ backgroundColor: "#4CAF50", color: "white" }}
            >
              {loading ? "Processando..." : "Salvar Dados e Calcular Folha"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default PayrollPage;
