package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorIrff extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorIrff(BigDecimal salarioBruto, BigDecimal inss, int dependetes)
    {
        super();
        this.salarioBruto = salarioBruto;
        this.inss = inss;
        this.dependentes = dependetes;
    }

    public BigDecimal calculator() {
        BigDecimal salarioBase = salarioBruto.subtract(inss);
        double deducaoDependentes = dependentes * 189.59;
        BigDecimal baseCalculo = salarioBase.subtract(new BigDecimal(String.valueOf(deducaoDependentes)));
        BigDecimal irrf;
        BigDecimal aliquota ;
        BigDecimal deducao;

        if (baseCalculo.compareTo(new BigDecimal("1903.98")) <= 0 ) {
             aliquota = new BigDecimal(0);
             deducao = new BigDecimal(0);
        } else if (baseCalculo.compareTo(new BigDecimal("2826.65")) <= 0) {
            aliquota = new BigDecimal("0.075");
            deducao = new BigDecimal("142.80");
        } else if (baseCalculo.compareTo(new BigDecimal("3751.05")) <= 0){
            aliquota = new BigDecimal("0.15");
            deducao = new BigDecimal("354.80");
        } else if (baseCalculo.compareTo(new BigDecimal("4664.68")) <= 0) {
            aliquota = new BigDecimal("0.225");
            deducao = new BigDecimal("636.13");
        } else {
            aliquota = new BigDecimal("0.275");
            deducao = new BigDecimal("869.36");
        }
        BigDecimal mult = baseCalculo.multiply(aliquota);
        irrf = mult.subtract(deducao);

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = new BigDecimal("0");
        }

        this.irrf = irrf;
        return irrf;
    }
}
