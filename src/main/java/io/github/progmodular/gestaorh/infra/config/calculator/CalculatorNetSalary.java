package io.github.progmodular.gestaorh.infra.config.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;

import java.math.BigDecimal;

public class CalculatorNetSalary extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorNetSalary(Employee employee) {
        super();
        this.employee = employee;
    }

    public BigDecimal calculator() {
        BigDecimal inss = new CalculatorInss(employee).calculator();
        BigDecimal irff = new CalculatorIrrf(employee).calculator();
        BigDecimal valueTransportDiscount = new CalculatorDiscountValueTransport(employee).calculator();
        BigDecimal sumTotalDiscount = inss.add(irff).add(valueTransportDiscount);
        netSalary = this.employee.getGrossSalary().subtract(sumTotalDiscount);
        return netSalary;
    }

    @Override
    public String getCalculationType() {
        return "NET_SALARY";
    }
}
