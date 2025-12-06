import React, { useState, useEffect } from "react";
import api from "../services/api";
import { PayrollResponse, User } from "../types";
import PayrollDetailsModal from "../components/PayrollDetailsModal";
import { useNavigate, useParams } from "react-router-dom";
import "./EmployeePayrollPage.css";

const AdminUserHistoryPage: React.FC = () => {
  const [history, setHistory] = useState<PayrollResponse[]>([]);
  const [selectedPayroll, setSelectedPayroll] =
    useState<PayrollResponse | null>(null);
  const [employeeInfo, setEmployeeInfo] = useState<User | null>(null);

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      fetchHistory(Number(id));
      fetchUserInfo(Number(id));
    }
  }, [id]);

  const fetchUserInfo = async (userId: number) => {
    try {
      const response = await api.get(`/users/${userId}`);
      setEmployeeInfo(response.data);
    } catch (error) {
      console.error("Erro ao buscar info do user", error);
    }
  };

  const fetchHistory = async (userId: number) => {
    try {
      const response = await api.get(`/api/payroll/employee/${userId}/history`);
      setHistory(response.data);
    } catch (error) {
      console.error("Erro ao buscar histórico", error);
    }
  };

  return (
    <div className="page-container">
      <header className="page-header">
        <div>
          <h2 style={{ fontSize: "1.2rem", color: "#666" }}>Histórico de:</h2>
          <h1 style={{ fontSize: "2rem", margin: 0 }}>
            {employeeInfo?.name || "Carregando..."}
          </h1>
        </div>

        <button onClick={() => navigate("/admin/users")} className="logout-btn">
          Voltar para Lista
        </button>
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
            <p>Este usuário não possui folhas de pagamento lançadas.</p>
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

export default AdminUserHistoryPage;
