package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorValueTransport extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorValueTransport(BigDecimal salarioBruto, BigDecimal valeTransporte)
    {
        this.salarioBruto = salarioBruto;
        this.valeTransporte = valeTransporte;
    }

    public BigDecimal calculator() {
        BigDecimal descontoMaximo = salarioBruto.multiply(new BigDecimal("0.06"));

        if (valeTransporte.compareTo(descontoMaximo) < 0) {
            this.valeTransporte = descontoMaximo;
            return valeTransporte;
        } else {
            this.valeTransporte = descontoMaximo;
            return descontoMaximo;
        }
    }
}
