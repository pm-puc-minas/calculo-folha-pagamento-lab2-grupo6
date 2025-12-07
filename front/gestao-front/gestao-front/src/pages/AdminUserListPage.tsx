import React, { useEffect, useState } from "react";
import api from "../services/api";
import { User } from "../types";
import { useNavigate } from "react-router-dom";
import EditUserModal from "../components/EditUserModal";
import DeleteUserModal from "../components/DeleteUserModal";
import "./EmployeePayrollPage.css";

const AdminUserListPage: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [editingUser, setEditingUser] = useState<User | null>(null);
  const [deletingUser, setDeletingUser] = useState<User | null>(null);
  const navigate = useNavigate();

  const userStorage = localStorage.getItem("user");
  const currentUser = userStorage ? JSON.parse(userStorage) : null;

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
        {users.map((user) => {
          const isCurrentUser = currentUser && currentUser.id === user.id;

          return (
            <div
              key={user.id}
              className="payroll-card"
              style={{
                borderColor:
                  user.userType === "PAYROLL_ADMIN" ? "#2563eb" : "#e5e7eb",
                opacity: isCurrentUser ? 0.8 : 1,
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
                  {/* Tag visual "Você" */}
                  {isCurrentUser && (
                    <span
                      style={{
                        fontSize: "0.7rem",
                        backgroundColor: "#d1fae5",
                        color: "#065f46",
                        marginLeft: "8px",
                        padding: "2px 6px",
                        borderRadius: "4px",
                      }}
                    >
                      VOCÊ
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
                  {/* 3. Desabilita botões se for o usuário atual */}
                  <button
                    className={`edit-btn ${
                      isCurrentUser ? "disabled-action-btn" : ""
                    }`}
                    style={{ flex: 1 }}
                    onClick={() => !isCurrentUser && setEditingUser(user)}
                    disabled={isCurrentUser}
                    title={
                      isCurrentUser
                        ? "Você não pode editar seu próprio perfil aqui."
                        : "Editar Usuário"
                    }
                  >
                    ✏️ Editar
                  </button>

                  <button
                    className={`delete-btn-card ${
                      isCurrentUser ? "disabled-action-btn" : ""
                    }`}
                    style={{ flex: 1 }}
                    onClick={() => !isCurrentUser && setDeletingUser(user)}
                    disabled={isCurrentUser}
                    title={
                      isCurrentUser
                        ? "Você não pode excluir a si mesmo."
                        : "Excluir Usuário"
                    }
                  >
                    🗑️ Excluir
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {/* Modais... */}
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
