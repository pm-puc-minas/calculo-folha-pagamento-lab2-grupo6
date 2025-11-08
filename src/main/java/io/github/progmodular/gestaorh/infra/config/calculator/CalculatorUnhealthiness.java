package io.github.progmodular.gestaorh.infra.config.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;

import java.math.BigDecimal;

public class CalculatorUnhealthiness extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorUnhealthiness(Employee employee)
    {
        this.employee = employee;
    }
    public BigDecimal calculator() {
        return minimunSalary.multiply(employee.getDegreeUnhealthiness().getPercentage());
    }

    @Override
    public String getCalculationType() {
        return "UNHEALTHY";
    }
}
