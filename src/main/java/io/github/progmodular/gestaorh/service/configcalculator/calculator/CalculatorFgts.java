package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorFgts extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorFgts(BigDecimal salarioBruto)
    {
        this.salarioBruto = salarioBruto;
    }

    public BigDecimal calculator() {
        return salarioBruto.multiply(this.fgtsPercentage);
    }
}
