package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;
import io.github.progmodular.gestaorh.service.calculatorservice.DegreeUnhealthiness;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUnhealthiness extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorUnhealthiness(Employee employee)
    {
        this.employee = employee;
    }
    public BigDecimal calculator() {

        BigDecimal unhealthyConditions = minimunSalary.multiply(employee.getDegreeUnhealthiness().getPercentage());

        return this.employee.getGrossSalary().add(unhealthyConditions)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getCalculationType() {
        return "UNHEALTHY";
    }
}
