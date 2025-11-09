package io.github.progmodular.gestaorh.dto;


import io.github.progmodular.gestaorh.model.entities.Payroll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollReportDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private Integer month;
    private Integer year;
    private BigDecimal grossSalary;
    private BigDecimal inssDiscount;
    private BigDecimal irrfDiscount;
    private BigDecimal fgts;
    private BigDecimal dangerAllowance;
    private BigDecimal netSalary;
    private LocalDateTime calculationDate;

    public static PayrollReportDTO from(Payroll result) {
        return PayrollReportDTO.builder()
                .id(result.getId())
                .employeeId(result.getEmployee().getId())
                .employeeName(result.getEmployee().getName())
                .month(result.getMonth())
                .year(result.getYear())
                .grossSalary(result.getGrossSalary())
                .inssDiscount(result.getInssDiscount())
                .irrfDiscount(result.getIrrfDiscount())
                .fgts(result.getFgts())
                .dangerAllowance(result.getDangerAllowance())
                .netSalary(result.getNetSalary())
                .calculationDate(result.getCalculationDate())
                .build();
    }
}
