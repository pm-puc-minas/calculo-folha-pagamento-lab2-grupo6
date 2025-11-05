package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;
import org.springframework.stereotype.Component;

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
