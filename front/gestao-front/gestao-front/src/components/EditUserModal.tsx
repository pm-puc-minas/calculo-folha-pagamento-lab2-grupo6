import React, { useState, useEffect } from "react";
import api from "../services/api";
import { User } from "../types";
import "./EditUserModal.css";

interface EditUserModalProps {
  user: User;
  onClose: () => void;
  onSuccess: () => void;
}

const EditUserModal: React.FC<EditUserModalProps> = ({
  user,
  onClose,
  onSuccess,
}) => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [grossSalary, setGrossSalary] = useState<number | string>("");
  const [cpf, setCpf] = useState("");
  const [position, setPosition] = useState("");
  const [dependents, setDependents] = useState<number | string>(0);

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (user) {
      setName(user.name);
      setEmail(user.email);

      if (user.userType === "EMPLOYEE") {
        setGrossSalary(user.grossSalary || 0);
        setCpf(user.cpf || "");
        setPosition(user.position || "");
        setDependents(user.dependents || 0);
      }
    }
  }, [user]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const payload: any = {
        name,
        email,

        password: password || undefined,
        userType: user.userType,
        isAdmin: user.isAdmin,
      };

      if (user.userType === "EMPLOYEE") {
        payload.grossSalary = Number(grossSalary);
        payload.cpf = cpf;
        payload.position = position;
        payload.dependents = Number(dependents);
      }

      await api.put(`/users/${user.id}`, payload);

      alert("Usuário atualizado com sucesso!");
      onSuccess();
      onClose();
    } catch (error) {
      console.error("Erro ao atualizar usuário", error);
      alert("Erro ao atualizar usuário.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-container edit-user-modal">
        <div className="modal-header">
          <h3>Editar: {user.name}</h3>
          <span className="badge-type">{user.userType}</span>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nome Completo</label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label>E-mail</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label>Nova Senha (deixe em branco para manter)</label>
            <input
              type="password"
              value={password}
              placeholder="******"
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          {/* Renderização Condicional para Employee */}
          {user.userType === "EMPLOYEE" && (
            <div className="employee-fields">
              <h4>Dados Contratuais</h4>

              <div className="form-row">
                <div className="form-group">
                  <label>CPF</label>
                  <input
                    type="text"
                    value={cpf}
                    onChange={(e) => setCpf(e.target.value)}
                    maxLength={14}
                  />
                </div>
                <div className="form-group">
                  <label>Cargo</label>
                  <input
                    type="text"
                    value={position}
                    onChange={(e) => setPosition(e.target.value)}
                  />
                </div>
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label>Salário Bruto (R$)</label>
                  <input
                    type="number"
                    step="0.01"
                    value={grossSalary}
                    onChange={(e) => setGrossSalary(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <label>Dependentes</label>
                  <input
                    type="number"
                    value={dependents}
                    onChange={(e) => setDependents(e.target.value)}
                  />
                </div>
              </div>
            </div>
          )}

          <div className="modal-actions">
            <button type="button" className="cancel-btn" onClick={onClose}>
              Cancelar
            </button>
            <button type="submit" className="save-btn" disabled={loading}>
              {loading ? "Salvando..." : "Salvar Alterações"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditUserModal;
