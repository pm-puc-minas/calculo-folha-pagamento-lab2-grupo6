package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.ICalculatorInterface;

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
