package io.github.progmodular.gestaorh.infra.config.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorHoursSalary extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorHoursSalary(Employee employee)
    {
        this.employee = employee;
    }

    @Override
    public BigDecimal calculator() {
        return this.employee.getGrossSalary().divide(new BigDecimal(this.employee.getHoursWorkedMonth()),2, RoundingMode.HALF_UP);
    }

    @Override
    public String getCalculationType() {
        return "HOURS_SALARY";
    }
}
