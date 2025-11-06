package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;

import java.math.BigDecimal;

public class CalculatorIrrf extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorIrrf(Employee employee)
    {
        super();
        this.employee = employee;
    }

    public BigDecimal calculator() {

        BigDecimal salarioBase = this.employee.getGrossSalary().subtract(new CalculatorInss(employee).calculator() );
        Double deducaoDependentes = this.employee.getDependents() * 200.00;
        BigDecimal baseCalculo = salarioBase.subtract(new BigDecimal(String.valueOf(deducaoDependentes)));
        BigDecimal irrf;
        BigDecimal aliquota ;
        BigDecimal deducao;

        if (baseCalculo.compareTo(new BigDecimal("2400.00")) <= 0 ) {
            aliquota = new BigDecimal(0);
            deducao = new BigDecimal(0);
        } else if (baseCalculo.compareTo(new BigDecimal("3200.00")) <= 0) {
            aliquota = new BigDecimal("0.075");
            deducao = new BigDecimal("180.00");
        } else if (baseCalculo.compareTo(new BigDecimal("4300.00")) <= 0){
            aliquota = new BigDecimal("0.15");
            deducao = new BigDecimal("400.00");
        } else if (baseCalculo.compareTo(new BigDecimal("5300.00")) <= 0) {
            aliquota = new BigDecimal("0.225");
            deducao = new BigDecimal("730.00");
        } else {
            aliquota = new BigDecimal("0.275");
            deducao = new BigDecimal("1000.00");
        }
        BigDecimal mult = baseCalculo.multiply(aliquota);
        irrf = mult.subtract(deducao);
        System.out.println("debug"+irrf);
        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = new BigDecimal("0");
        }

        return irrf;
    }

    @Override
    public String getCalculationType() {
        return "IRRF";
    }
}
