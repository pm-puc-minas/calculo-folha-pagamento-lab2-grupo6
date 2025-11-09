package io.github.progmodular.gestaorh.dto;

import io.github.progmodular.gestaorh.model.entities.Payroll;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
class PayrollResponseDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private BigDecimal grossSalary;
    private BigDecimal inssDiscount;
    private BigDecimal irrfDiscount;
    private BigDecimal fgts;
    private BigDecimal dangerAllowance;
    private BigDecimal netSalary;
    private LocalDateTime calculationDate;

    public PayrollResponseDTO(Payroll payroll) {
        this.id = payroll.getId();
        this.employeeId = payroll.getEmployee().getId();
        this.employeeName = payroll.getEmployee().getName();
        this.grossSalary = payroll.getGrossSalary();
        this.inssDiscount = payroll.getInssDiscount();
        this.irrfDiscount = payroll.getIrrfDiscount();
        this.fgts = payroll.getFgts();
        this.dangerAllowance = payroll.getDangerAllowance();
        this.netSalary = payroll.getNetSalary();
        this.calculationDate = payroll.getCalculationDate();
    }
}
