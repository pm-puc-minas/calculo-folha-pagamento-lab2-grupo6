import React, { useEffect, useState } from "react";
import api from "../services/api";
import { User } from "../types";
import { useNavigate } from "react-router-dom";
import EditUserModal from "../components/EditUserModal";
import DeleteUserModal from "../components/DeleteUserModal"; // Importe o novo modal
import "./EmployeePayrollPage.css";

const AdminUserListPage: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [editingUser, setEditingUser] = useState<User | null>(null);
  const [deletingUser, setDeletingUser] = useState<User | null>(null); // Novo estado
  const navigate = useNavigate();

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = () => {
    api
      .get("/users")
      .then((response) => setUsers(response.data))
      .catch((err) => console.error("Erro ao listar usuários", err));
  };

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
              <h3 className="card-title">
                {user.name}
                {user.userType === "PAYROLL_ADMIN" && (
                  <span
                    style={{
                      fontSize: "0.8rem",
                      color: "#2563eb",
                      marginLeft: "8px",
                    }}
                  >
                    (Admin)
                  </span>
                )}
              </h3>
              <p className="card-info">Email: {user.email}</p>

              {user.userType === "EMPLOYEE" && (
                <p className="card-info">
                  Cargo: {user.position || "Não informado"}
                </p>
              )}
            </div>

            <div className="card-actions-column">
              {/* Botão de Histórico */}
              {user.userType === "EMPLOYEE" ? (
                <button
                  className="details-btn"
                  onClick={() => navigate(`/admin/users/${user.id}/history`)}
                >
                  📄 Ver Folhas
                </button>
              ) : (
                <button className="details-btn disabled-btn" disabled>
                  🚫 Sem Folha
                </button>
              )}

              {/* Grupo de Ações de Manutenção */}
              <div style={{ display: "flex", gap: "10px" }}>
                <button
                  className="edit-btn"
                  style={{ flex: 1 }}
                  onClick={() => setEditingUser(user)}
                >
                  ✏️ Editar
                </button>

                <button
                  className="delete-btn-card"
                  style={{ flex: 1 }}
                  onClick={() => setDeletingUser(user)}
                >
                  🗑️ Excluir
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Modal de Edição */}
      {editingUser && (
        <EditUserModal
          user={editingUser}
          onClose={() => setEditingUser(null)}
          onSuccess={() => {
            fetchUsers();
            setEditingUser(null);
          }}
        />
      )}

      {/* Modal de Deleção (NOVO) */}
      {deletingUser && (
        <DeleteUserModal
          user={deletingUser}
          onClose={() => setDeletingUser(null)}
          onSuccess={() => {
            fetchUsers();
            setDeletingUser(null);
          }}
        />
      )}
    </div>
  );
};

export default AdminUserListPage;
