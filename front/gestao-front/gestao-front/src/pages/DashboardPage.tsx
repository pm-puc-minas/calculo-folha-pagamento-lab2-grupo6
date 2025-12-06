import React from "react";
import { useNavigate } from "react-router-dom";

const DashboardPage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div>
      <h2>Bem-vindo, PAYROLL_ADMIN</h2>
      <button onClick={() => navigate("/payroll")}>
        Criar Folha de Pagamento
      </button>
      <button onClick={() => navigate("/payroll/history")}>
        Visualizar Folhas de Pagamento
      </button>
    </div>
  );
};

export default DashboardPage;
