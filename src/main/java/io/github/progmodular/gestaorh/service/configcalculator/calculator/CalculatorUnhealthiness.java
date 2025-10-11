package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.ICalculatorInterface;
import io.github.progmodular.gestaorh.service.configcalculator.GrauInsalubridade;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorUnhealthiness extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorUnhealthiness(GrauInsalubridade grauInsalubridade, BigDecimal salarioBruto)
    {
        this.grauInsalubridade = grauInsalubridade;
        this.salarioBruto = salarioBruto;
    }
    public BigDecimal calculator() {

        this.insalubridade = salarioBruto.multiply(grauInsalubridade.getPercentual());

        return salarioBruto.add(this.insalubridade)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
