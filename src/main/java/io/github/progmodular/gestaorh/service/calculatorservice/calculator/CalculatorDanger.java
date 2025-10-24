package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorDanger extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorDanger(BigDecimal salarioBruto)
    {
        this.salarioBruto = salarioBruto;
    }
    public BigDecimal calculator() {
        return salarioBruto.multiply(this.dangerPercentage);
    }
}
