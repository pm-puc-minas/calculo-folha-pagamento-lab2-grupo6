package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorFoodValue extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorFoodValue(Employee employee) {
        this.employee = employee;
    }

    @Override
    public BigDecimal calculator() {
        if (this.employee == null) {
            throw new IllegalStateException("Employee não pode ser nulo");
        }
        if (daysWorked == null) {
            throw new IllegalStateException("Dias trabalhados não podem ser nulos");
        }
        if (valeDaily == null) {
            throw new IllegalStateException("Vale diario não pode ser nulo");
        }
        BigDecimal daysWorkedInBigDecimal = new BigDecimal(daysWorked);

        return valeDaily.multiply(daysWorkedInBigDecimal);
    }

    @Override
    public String getCalculationType() {
        return "FOOD_VALUE";
    }
}