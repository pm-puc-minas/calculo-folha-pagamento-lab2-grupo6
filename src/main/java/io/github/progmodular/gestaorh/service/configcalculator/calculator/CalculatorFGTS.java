package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.CalculatorInterface;

import java.math.BigDecimal;

public class CalculatorFGTS extends CalculatorAbstract implements CalculatorInterface {

    public CalculatorFGTS(BigDecimal salarioBruto)
    {
        this.salarioBruto = salarioBruto;
    }

    public BigDecimal calculator() {
        fgts = salarioBruto.multiply(new BigDecimal("0.08"));
        return fgts;
    }
}
