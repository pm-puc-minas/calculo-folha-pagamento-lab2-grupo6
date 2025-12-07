import React from "react";
import { PayrollResponse } from "../types";
import "./PayrollDetailsModal.css";

interface Props {
  payroll: PayrollResponse;
  onClose: () => void;
}

const PayrollDetailsModal: React.FC<Props> = ({ payroll, onClose }) => {
  // Função auxiliar para formatar moeda
  const formatMoney = (val: number) => {
    return val ? `R$ ${val.toFixed(2)}` : "R$ 0.00";
  };

  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <div className="modal-header">
          <h3>
            Holerite: {payroll.month}/{payroll.year}
          </h3>
          <div className="employee-info">
            <p>
              <strong>Funcionário:</strong> {payroll.employee.name} (ID:{" "}
              {payroll.employee.id})
            </p>
            <p>
              <strong>Cargo:</strong> {payroll.employee.position || "N/A"}
            </p>
          </div>
        </div>

        <div className="payroll-grid">
          {/* Vencimentos */}
          <div>
            <h4 className="section-title title-earnings">Vencimentos</h4>
            {/* <div className="financial-row">
              <span>Dias Trabalhados:</span>
              <span className="value-positive">
                {formatMoney(payroll.daysWorked)}
              </span>
            </div> */}
            <div className="financial-row">
              <span>Salário Bruto:</span>
              <span className="value-positive">
                {formatMoney(payroll.grossSalary)}
              </span>
            </div>
            {payroll.dangerAllowance > 0 && (
              <div className="financial-row">
                <span>Adicional Periculosidade:</span>
                <span className="value-positive">
                  {formatMoney(payroll.dangerAllowance)}
                </span>
              </div>
            )}
            {payroll.unhealthyAllowance > 0 && (
              <div className="financial-row">
                <span>Adicional Insalubridade:</span>
                <span className="value-positive">
                  {formatMoney(payroll.unhealthyAllowance)}
                </span>
              </div>
            )}
          </div>

          {/* Descontos */}
          <div>
            <h4 className="section-title title-discounts">Descontos</h4>
            <div className="financial-row">
              <span>INSS:</span>
              <span className="value-negative">
                - {formatMoney(payroll.inssDiscount)}
              </span>
            </div>
            <div className="financial-row">
              <span>IRRF:</span>
              <span className="value-negative">
                - {formatMoney(payroll.irrfDiscount)}
              </span>
            </div>
            <div className="financial-row">
              <span>Vale Transporte:</span>
              <span className="value-negative">
                - {formatMoney(payroll.valueTransportDiscount)}
              </span>
            </div>
          </div>
        </div>

        <div className="modal-footer">
          <div className="fgts-info">
            FGTS (Recolhido): {formatMoney(payroll.fgts)}
          </div>
          <div className="net-salary-container">
            <div className="net-label">Líquido a Receber:</div>
            <h2 className="net-value">{formatMoney(payroll.netSalary)}</h2>
          </div>
        </div>

        <button onClick={onClose} className="close-btn">
          Fechar
        </button>
      </div>
    </div>
  );
};

export default PayrollDetailsModal;
