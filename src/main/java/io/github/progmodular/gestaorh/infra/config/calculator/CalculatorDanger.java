package io.github.progmodular.gestaorh.infra.config.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;

import java.math.BigDecimal;

public class CalculatorDanger extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorDanger(Employee employee) {
        this.employee = employee;
    }

    @Override
    public BigDecimal calculator() {
        if (this.employee == null) {
            throw new IllegalStateException("Employee não pode ser nulo");
        }
        if (this.employee.getGrossSalary() == null) {
            throw new IllegalStateException("Salário bruto não pode ser nulo");
        }

        return this.employee.getGrossSalary().multiply(dangerPercentage);
    }

    @Override
    public String getCalculationType() {
        return "DANGER";
    }
}