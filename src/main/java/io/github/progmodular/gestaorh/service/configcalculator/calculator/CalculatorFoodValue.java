package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.CalculatorInterface;

import java.math.BigDecimal;

public class CalculatorFoodValue extends CalculatorAbstract implements CalculatorInterface {

    public CalculatorFoodValue(int diasTrabalhados,BigDecimal valeDiario)
    {
        this.diasTrabalhados = diasTrabalhados;
        this.valeDiario = valeDiario;
    }
    public BigDecimal calculator() {
        return valeDiario.multiply(new BigDecimal(String.valueOf(diasTrabalhados)));
    }
}
