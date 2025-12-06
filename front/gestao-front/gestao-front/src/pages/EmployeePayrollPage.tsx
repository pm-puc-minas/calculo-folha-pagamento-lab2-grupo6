import React, { useState, useEffect } from "react";
import api from "../services/api";
import { PayrollResponse } from "../types";
import PayrollDetailsModal from "../components/PayrollDetailsModal";
import { useNavigate } from "react-router-dom";
import "./EmployeePayrollPage.css";

const EmployeePayrollPage: React.FC = () => {
  const [history, setHistory] = useState<PayrollResponse[]>([]);
  const [selectedPayroll, setSelectedPayroll] =
    useState<PayrollResponse | null>(null);
  const navigate = useNavigate();

  const userStorage = localStorage.getItem("user");
  const authHeader = localStorage.getItem("authHeader");
  const user = userStorage ? JSON.parse(userStorage) : null;

  useEffect(() => {
    if (!user || !user.id || !authHeader) {
      alert("Sessão expirada. Faça login novamente.");
      handleLogout();
      return;
    }
    fetchHistory(user.id);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("authHeader");
    navigate("/login");
  };

  const fetchHistory = async (id: number) => {
    try {
      const response = await api.get(`/api/payroll/employee/${id}/history`, {
        headers: {
          Authorization: authHeader,
        },
      });
      setHistory(response.data);
    } catch (error) {
      console.error("Erro ao buscar histórico", error);
    }
  };

  return (
    <div className="page-container">
      {/* HEADER ATUALIZADO */}
      <header className="page-header">
        <h2>Minhas Folhas</h2>

        <div className="header-actions">
          {/* Exibição do Nome e Email */}
          <div className="user-profile">
            <span className="user-name">{user?.name}</span>
            <span className="user-email">{user?.email}</span>
          </div>

          <button onClick={handleLogout} className="logout-btn">
            Sair
          </button>
        </div>
      </header>

      <div className="card-grid">
        {history.length > 0 ? (
          history.map((item) => (
            <div key={item.id} className="payroll-card">
              <div>
                <h3 className="card-title">
                  📅 {item.month.toString().padStart(2, "0")}/{item.year}
                </h3>
                <p className="card-info">
                  Líquido:{" "}
                  <span className="money-highlight">
                    R$ {item.netSalary?.toFixed(2)}
                  </span>
                </p>
              </div>
              <button
                className="details-btn"
                onClick={() => setSelectedPayroll(item)}
              >
                Visualizar Detalhes
              </button>
            </div>
          ))
        ) : (
          <div className="empty-state">
            <p>Não existe folha de pagamento lançada para este perfil.</p>
          </div>
        )}
      </div>

      {selectedPayroll && (
        <PayrollDetailsModal
          payroll={selectedPayroll}
          onClose={() => setSelectedPayroll(null)}
        />
      )}
    </div>
  );
};

export default EmployeePayrollPage;
