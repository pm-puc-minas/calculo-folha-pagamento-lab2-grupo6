package io.github.progmodular.gestaorh.service.calculatorservice;

import io.github.progmodular.gestaorh.model.entities.Employee;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Setter
@Getter
@Component
public abstract class CalculatorAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Employee employee;
    protected BigDecimal minimunSalary = new BigDecimal("1518.00");
    protected BigDecimal hourSalary;
    protected BigDecimal vtDiscountValue;
    protected BigDecimal inss;
    protected final BigDecimal fgtsPercentage = new BigDecimal("0.08");
    protected BigDecimal netSalary;
    protected BigDecimal irrf;
    protected BigDecimal valeDaily;
    protected final BigDecimal dangerPercentage = new BigDecimal("0.30");
    protected Integer hoursMonth;
    protected Integer hoursDay;
    protected Integer daysWorked;
    protected Integer daysWorkedPerWeek;
    protected BigDecimal unhealthyConditions;
    protected DegreeUnhealthiness degreeOfUnhealthiness;
}