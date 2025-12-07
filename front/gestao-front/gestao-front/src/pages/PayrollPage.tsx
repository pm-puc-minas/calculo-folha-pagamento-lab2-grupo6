import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import { User, DegreeUnhealthiness } from "../types";
import "./PayrollPage.css";

const PayrollPage: React.FC = () => {
  const navigate = useNavigate();

  const [step, setStep] = useState<"SEARCH" | "FORM">("SEARCH");
  const [searchId, setSearchId] = useState("");
  const [loading, setLoading] = useState(false);
  const [employee, setEmployee] = useState<User | null>(null);

  const [formData, setFormData] = useState({
    // REMOVIDO: hoursWorkedMonth: 0,
    daysWorked: 0,
    actualVTCost: 0,
    degreeUnhealthiness: DegreeUnhealthiness.MINIMUM,
    hasDanger: false,
    month: new Date().getMonth() + 1,
    year: new Date().getFullYear(),
  });

  const handleSearch = async () => {
    if (!searchId) return;
    setLoading(true);
    try {
      const response = await api.get<User>(`/users/${searchId}`);
      const emp = response.data;

      const role = emp.userType;
      if (role !== "EMPLOYEE") {
        alert("O ID informado não pertence a um funcionário (Employee).");
        setLoading(false);
        return;
      }

      setEmployee(emp);

      setFormData({
        // REMOVIDO: hoursWorkedMonth
        daysWorked: emp.daysWorked || 20, // Padrão 20 ou 30 dias
        actualVTCost: emp.actualVTCost || 0,
        degreeUnhealthiness:
          (emp.degreeUnhealthiness as DegreeUnhealthiness) ||
          DegreeUnhealthiness.MINIMUM,
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

  const handleSubmit = async () => {
    if (!employee) return;
    setLoading(true);

    try {
      // 1. Atualizar dados variáveis (PATCH) - Sem HoursWorked
      await api.patch(`/users/employee/${employee.id}`, {
        daysWorked: formData.daysWorked,
        actualVTCost: formData.actualVTCost,
        degreeUnhealthiness: formData.degreeUnhealthiness,
        hasDanger: formData.hasDanger,
      });

      // 2. Calcular a folha (POST)
      await api.post("/api/payroll/calculate", {
        employeeId: employee.id,
        month: formData.month,
        year: formData.year,
      });

      alert("Sucesso! Folha de pagamento calculada.");

      setStep("SEARCH");
      setSearchId("");
      setEmployee(null);
    } catch (error) {
      console.error("Erro no processo:", error);
      alert("Erro ao processar. Verifique o console.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="payroll-page-container">
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "30px",
        }}
      >
        <h2 style={{ margin: 0, textAlign: "left" }}>Lançamento de Folha</h2>
        <button
          onClick={() => navigate("/dashboard")}
          style={{
            backgroundColor: "#6b7280",
            color: "white",
            padding: "8px 16px",
            height: "auto",
          }}
        >
          Voltar ao Dashboard
        </button>
      </div>

      {step === "SEARCH" && (
        <div className="card">
          <label>
            ID do Funcionário:
            <input
              type="number"
              value={searchId}
              onChange={(e) => setSearchId(e.target.value)}
              placeholder="Digite o ID..."
              onKeyDown={(e) => e.key === "Enter" && handleSearch()}
            />
          </label>
          <button onClick={handleSearch} disabled={loading}>
            {loading ? "Buscando..." : "Iniciar Lançamento"}
          </button>
        </div>
      )}

      {step === "FORM" && employee && (
        <div className="form-container">
          <h3>
            Colaborador:{" "}
            <span style={{ color: "#2563eb" }}>{employee.name}</span>
          </h3>

          <div className="row">
            <div className="input-group">
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
            </div>
            <div className="input-group">
              <label>Ano:</label>
              <input
                type="number"
                value={formData.year}
                onChange={(e) =>
                  setFormData({ ...formData, year: Number(e.target.value) })
                }
              />
            </div>
          </div>

          <hr />
          <h4>Variáveis da Folha</h4>

          <div className="row">
            {/* REMOVIDO INPUT DE HORAS */}
            <div className="input-group">
              <label>Dias Trabalhados:</label>
              <input
                type="number"
                max={30} // Sugestão: limitar a 30 ou 31
                value={formData.daysWorked}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    daysWorked: Number(e.target.value),
                  })
                }
              />
            </div>

            <div className="input-group">
              <label>Custo VT (Real):</label>
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
          </div>

          <div className="row">
            <div className="input-group">
              <label>Grau de Insalubridade:</label>
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

            <div className="input-group" style={{ justifyContent: "center" }}>
              <label style={{ cursor: "pointer" }}>
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
          </div>

          <div className="actions">
            <button
              onClick={() => {
                setStep("SEARCH");
                setSearchId("");
                setEmployee(null);
              }}
            >
              Cancelar
            </button>
            <button
              onClick={handleSubmit}
              disabled={loading}
              style={{ backgroundColor: "#10b981", color: "white" }}
            >
              {loading ? "Processando..." : "Confirmar e Calcular"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default PayrollPage;
