package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll_results")
@Getter
@Setter
public class PayrollResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Integer month;
    private Integer year;
    private BigDecimal grossSalary;
    private BigDecimal inssDiscount;
    private BigDecimal irrfDiscount;
    private BigDecimal fgts;
    private BigDecimal dangerAllowance;
//    private BigDecimal unhealthyAllowance;
    private BigDecimal netSalary;
    private LocalDateTime calculationDate;

}