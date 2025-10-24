package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorHoursSalary extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorHoursSalary(BigDecimal salarioBruto, int diasTrabalhadosSemana, int horasDias)
    {
        this.salarioBruto = salarioBruto;
        this.diasTrabalhadosSemana = diasTrabalhadosSemana;
        this.horasDias = horasDias;
    }
    public BigDecimal calculator() {
        double jornadaSemanal = horasDias * diasTrabalhadosSemana;
        double jornadaMensal = jornadaSemanal * 5;
        return salarioBruto.divide(new BigDecimal(String.valueOf(jornadaMensal)),2, RoundingMode.HALF_UP);
    }
}
