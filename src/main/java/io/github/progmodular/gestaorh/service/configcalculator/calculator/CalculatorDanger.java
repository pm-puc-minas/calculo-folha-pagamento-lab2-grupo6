package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.CalculatorInterface;

import java.math.BigDecimal;

public class CalculatorDanger extends CalculatorAbstract implements CalculatorInterface {

    public CalculatorDanger(BigDecimal salarioBruto)
    {
        this.salarioBruto = salarioBruto;
    }
    public BigDecimal calculator() {
        return salarioBruto.multiply(new BigDecimal("0.3"));
    }
}
