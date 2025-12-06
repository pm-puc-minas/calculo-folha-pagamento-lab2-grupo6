import React, { useEffect, useState } from "react";
import api from "../services/api";
import { User } from "../types";
import { useNavigate } from "react-router-dom";
import "./EmployeePayrollPage.css";
const AdminUserListPage: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    api
      .get("/users")
      .then((response) => setUsers(response.data))
      .catch((err) => console.error("Erro ao listar usuários", err));
  }, []);

  return (
    <div className="page-container">
      <header className="page-header">
        <h2>Gestão de Usuários</h2>
        <button className="logout-btn" onClick={() => navigate("/dashboard")}>
          Voltar
        </button>
      </header>

      <div className="card-grid">
        {users.map((user) => (
          <div
            key={user.id}
            className="payroll-card"
            style={{
              borderColor:
                user.userType === "PAYROLL_ADMIN" ? "#2563eb" : "#e5e7eb",
            }}
          >
            <div>
              <h3 className="card-title">{user.name}</h3>
              <p className="card-info">Email: {user.email}</p>
              <p className="card-info">
                Tipo: <strong>{user.userType}</strong>
              </p>
              {user.userType === "EMPLOYEE" && (
                <p className="card-info">ID: {user.id}</p>
              )}
            </div>

            {user.userType === "EMPLOYEE" ? (
              <button
                className="details-btn"
                onClick={() => navigate(`/admin/users/${user.id}/history`)}
              >
                Ver Folhas de Pagamento
              </button>
            ) : (
              <button
                className="details-btn"
                disabled
                style={{ backgroundColor: "#ccc", cursor: "not-allowed" }}
              >
                Sem Folha (Admin)
              </button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default AdminUserListPage;
