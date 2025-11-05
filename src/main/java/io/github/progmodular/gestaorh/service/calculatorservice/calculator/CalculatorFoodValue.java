package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


public class CalculatorFoodValue extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorFoodValue(int diasTrabalhados, BigDecimal valeDiario)
    {
        this.daysWorked = diasTrabalhados;
        this.valeDaily = valeDiario;
    }
    public BigDecimal calculator() {
        return valeDaily.multiply(new BigDecimal(String.valueOf(daysWorked)));
    }

    @Override
    public String getCalculationType() {
        return "FOOD_VALUE";
    }
}
