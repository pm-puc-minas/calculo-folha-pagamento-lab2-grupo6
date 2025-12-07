package io.github.progmodular.gestaorh.infra.config.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorNetSalary extends CalculatorAbstract implements ICalculatorInterface {

    private static final BigDecimal DAYS_IN_MONTH = new BigDecimal("30");

    public CalculatorNetSalary(Employee employee) {
        super();
        this.employee = employee;
    }

    public BigDecimal calculator() {
        BigDecimal daysWorked = new BigDecimal(this.employee.getDaysWorked());

        BigDecimal proportionalGrossSalary = this.employee.getGrossSalary()
                .divide(DAYS_IN_MONTH, 2, RoundingMode.HALF_UP)
                .multiply(daysWorked)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal inss = new CalculatorInss(employee).calculator();
        BigDecimal irff = new CalculatorIrrf(employee).calculator();
        BigDecimal valueTransportDiscount = new CalculatorDiscountValueTransport(employee).calculator();

        BigDecimal sumTotalDiscount = inss.add(irff).add(valueTransportDiscount);

        netSalary = proportionalGrossSalary.subtract(sumTotalDiscount);

        return netSalary.max(BigDecimal.ZERO);
    }

    @Override
    public String getCalculationType() {
        return "NET_SALARY";
    }
}