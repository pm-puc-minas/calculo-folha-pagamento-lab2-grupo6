package io.github.progmodular.gestaorh.infra.config.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorDiscountValueTransport extends CalculatorAbstract implements ICalculatorInterface {

    private static final BigDecimal ALIQUOTA_VT = new BigDecimal("0.06");

    public CalculatorDiscountValueTransport(Employee employee)
    {
        this.employee = employee;
    }

    public BigDecimal calculator() {

        BigDecimal descontoMaximo = this.employee.getGrossSalary().multiply(ALIQUOTA_VT);

        BigDecimal realCoustVT = this.employee.getActualVTCost();

        BigDecimal vtDiscountValue;

        if (realCoustVT.compareTo(descontoMaximo) < 0) {
            vtDiscountValue = realCoustVT;
        } else {
            vtDiscountValue = descontoMaximo;
        }

        this.vtDiscountValue = vtDiscountValue;
        return vtDiscountValue.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getCalculationType() {
        return "VALUE_TRANSPORT";
    }
}