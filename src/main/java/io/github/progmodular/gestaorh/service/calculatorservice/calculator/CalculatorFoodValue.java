package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorFoodValue extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorFoodValue(int diasTrabalhados, BigDecimal valeDiario)
    {
        this.diasTrabalhados = diasTrabalhados;
        this.valeDiario = valeDiario;
    }
    public BigDecimal calculator() {
        return valeDiario.multiply(new BigDecimal(String.valueOf(diasTrabalhados)));
    }
}
