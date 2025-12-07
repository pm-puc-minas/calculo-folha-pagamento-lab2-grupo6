package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll")
@Getter
@Setter
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Integer month;
    private Integer year;
    private BigDecimal grossSalary;
    private BigDecimal dangerAllowance;
    private BigDecimal valueTransportDiscount;
    private BigDecimal fgts;
    private BigDecimal hoursSalary;
    private BigDecimal inssDiscount;
    private BigDecimal irrfDiscount;
    private BigDecimal netSalary;
    private BigDecimal unhealthyAllowance;
    private LocalDateTime calculationDate;
    private Integer daysWorked;
    private BigDecimal actualVTCost;

}