package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;
import org.springframework.stereotype.Component;

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
