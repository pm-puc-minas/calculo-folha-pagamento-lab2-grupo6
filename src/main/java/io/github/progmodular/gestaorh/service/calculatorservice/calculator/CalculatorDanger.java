package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

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