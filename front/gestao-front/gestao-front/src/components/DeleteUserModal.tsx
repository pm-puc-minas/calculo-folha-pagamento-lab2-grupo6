import React, { useState } from "react";
import api from "../services/api";
import { User } from "../types";
import "./DeleteUserModal.css"; // CSS específico que criaremos abaixo

interface DeleteUserModalProps {
  user: User;
  onClose: () => void;
  onSuccess: () => void;
}

const DeleteUserModal: React.FC<DeleteUserModalProps> = ({
  user,
  onClose,
  onSuccess,
}) => {
  const [loading, setLoading] = useState(false);

  const handleDelete = async () => {
    setLoading(true);
    try {
      await api.delete(`/users/${user.id}`);
      onSuccess(); // Recarrega a lista
      onClose(); // Fecha modal
      alert("Usuário removido com sucesso.");
    } catch (error) {
      console.error("Erro ao deletar usuário", error);
      alert("Erro ao excluir. Verifique se o usuário ainda existe.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-container delete-modal">
        <div className="delete-header">
          <h3>Excluir Usuário</h3>
        </div>

        <div className="delete-body">
          <p>
            Tem certeza que deseja excluir o usuário{" "}
            <strong>{user.name}</strong>?
          </p>

          {user.userType === "EMPLOYEE" && (
            <div className="warning-box">
              ⚠️ Atenção: Esta ação irá apagar permanentemente todas as
              <strong> Folhas de Pagamento</strong> e históricos vinculados a
              este funcionário.
            </div>
          )}

          <p className="confirm-text">Esta ação não pode ser desfeita.</p>
        </div>

        <div className="modal-actions">
          <button className="cancel-btn" onClick={onClose} disabled={loading}>
            Cancelar
          </button>
          <button
            className="confirm-delete-btn"
            onClick={handleDelete}
            disabled={loading}
          >
            {loading ? "Excluindo..." : "Sim, Excluir Permanentemente"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default DeleteUserModal;
