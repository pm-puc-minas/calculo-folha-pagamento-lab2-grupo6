package io.github.progmodular.gestaorh.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PayrollReportRequestDTO {
    private long employeeId;
    private Integer month;
    private Integer year;
    private BigDecimal inssDiscount;
    private BigDecimal irrfDiscount;
    private BigDecimal fgts;
    private BigDecimal dangerAllowance;
    private BigDecimal netSalary;
    private LocalDateTime calculationDate;
}
