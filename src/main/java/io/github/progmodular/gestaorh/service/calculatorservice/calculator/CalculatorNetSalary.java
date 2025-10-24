package io.github.progmodular.gestaorh.service.calculatorservice.calculator;

import io.github.progmodular.gestaorh.service.calculatorservice.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class CalculatorNetSalary extends CalculatorAbstract implements ICalculatorInterface {

    public CalculatorNetSalary(BigDecimal salarioBruto, BigDecimal inss,
                               BigDecimal irrf, BigDecimal valeTransporte) {
        super();

        this.salarioBruto = salarioBruto;
        this.inss = inss;
        this.irrf = irrf;
        this.valeTransporte = valeTransporte;
    }

    public BigDecimal calculator() {
        BigDecimal sumPartial = inss.add(irrf);
        BigDecimal sumTotal = sumPartial.add(valeTransporte);
        salarioLiquido = salarioBruto.subtract(sumTotal);
        return salarioLiquido;
    }
}
