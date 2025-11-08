package io.github.progmodular.gestaorh.infra.config.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;

import java.math.BigDecimal;

public class CalculatorFgts extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorFgts(Employee employee) {
        this.employee = employee;
    }

    @Override
    public BigDecimal calculator() {
        return this.employee.getGrossSalary().multiply(this.fgtsPercentage);
    }

    @Override
    public String getCalculationType() {
        return "FGTS";
    }
}
