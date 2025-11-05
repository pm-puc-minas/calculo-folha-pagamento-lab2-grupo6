package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorNetSalary extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorNetSalary(Employee employee) {
        super();
        this.employee = employee;
    }

    public BigDecimal calculator() {
        BigDecimal inss = new CalculatorInss(employee).calculator();
        BigDecimal irff = new CalculatorIrff(employee).calculator();
        BigDecimal sumPartial = inss.add(irff);
        BigDecimal sumTotal = sumPartial.subtract(new CalculatorDiscountValueTransport(employee).calculator());
        netSalary = this.employee.getGrossSalary().subtract(sumTotal);
        return netSalary;
    }

    @Override
    public String getCalculationType() {
        return "NET_SALARY";
    }
}
