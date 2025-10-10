package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.CalculatorInterface;
import io.github.progmodular.gestaorh.service.configcalculator.GrauInsalubridade;

import java.math.BigDecimal;

public class CalculatorUnhealthiness extends CalculatorAbstract implements CalculatorInterface {

    public CalculatorUnhealthiness(GrauInsalubridade grauInsalubridade,BigDecimal salarioBruto)
    {
        this.grauInsalubridade = grauInsalubridade;
        this.salarioBruto = salarioBruto;
    }
    public BigDecimal calculator() {

        this.insalubridade = salarioBruto.multiply(grauInsalubridade.getPercentual());
        return salarioBruto.add(this.insalubridade);
    }
}
