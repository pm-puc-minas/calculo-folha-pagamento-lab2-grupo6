package io.github.progmodular.gestaorh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PayrollDTOResponse
        (
                @JsonProperty("employee")
                UserDTOResponse userDTOResponse,
                Long id,
                Integer month,
                Integer year,
                BigDecimal grossSalary,
                BigDecimal dangerAllowance,
                BigDecimal valueTransportDiscount,
                BigDecimal fgts,
                BigDecimal hoursSalary,
                BigDecimal inssDiscount,
                BigDecimal irrfDiscount,
                BigDecimal netSalary,
                BigDecimal unhealthyAllowance
        )
{

}
