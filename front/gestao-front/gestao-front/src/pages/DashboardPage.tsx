import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./DashboardPage.css";
const DashboardPage: React.FC = () => {
  const navigate = useNavigate();
  const userStorage = localStorage.getItem("user");
  const user = userStorage ? JSON.parse(userStorage) : null;

  useEffect(() => {
    if (!user || user.userType !== "PAYROLL_ADMIN") {
      alert("Acesso restrito a Administradores.");
      navigate("/login");
    }
  }, [navigate, user]);

  const handleLogout = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("authHeader");
    navigate("/login");
  };

  return (
    <div className="dashboard-container">
      <header className="dash-header">
        <h2>Painel Administrativo</h2>
        <div className="user-info">
          <span>Olá, {user?.name}</span>
          <button onClick={handleLogout} className="logout-link">
            Sair
          </button>
        </div>
      </header>

      <div className="actions-grid">
        <div className="action-card" onClick={() => navigate("/signup")}>
          <h3>➕ Cadastrar Usuário</h3>
          <p>Criar novos Funcionários ou Administradores.</p>
        </div>

        <div className="action-card" onClick={() => navigate("/payroll")}>
          <h3>💰 Gerar Folha</h3>
          <p>Calcular pagamento e descontos.</p>
        </div>

        <div className="action-card" onClick={() => navigate("/admin/users")}>
          <h3>👥 Ver Usuários</h3>
          <p>Listar funcionários e visualizar histórico de pagamentos.</p>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
