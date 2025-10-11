package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.ICalculatorInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorInss extends CalculatorAbstract implements ICalculatorInterface {

    private static final BigDecimal LIMITE_FAIXA1 = new BigDecimal("1320.00");
    private static final BigDecimal LIMITE_FAIXA2 = new BigDecimal("2571.29");
    private static final BigDecimal LIMITE_FAIXA3 = new BigDecimal("3856.94");
    private static final BigDecimal LIMITE_FAIXA4 = new BigDecimal("7507.49");

    private static final BigDecimal ALIQUOTA_7_5 = new BigDecimal("0.075");
    private static final BigDecimal ALIQUOTA_9 = new BigDecimal("0.09");
    private static final BigDecimal ALIQUOTA_12 = new BigDecimal("0.12");
    private static final BigDecimal ALIQUOTA_14 = new BigDecimal("0.14");

    public CalculatorInss(BigDecimal salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public BigDecimal calculator() {
        BigDecimal inss;

        if (salarioBruto.compareTo(LIMITE_FAIXA1) <= 0) {
            inss = salarioBruto.multiply(ALIQUOTA_7_5);

        } else if (salarioBruto.compareTo(LIMITE_FAIXA2) <= 0) {
            BigDecimal valorFaixa1 = LIMITE_FAIXA1.multiply(ALIQUOTA_7_5);
            BigDecimal diferencaFaixa2 = salarioBruto.subtract(LIMITE_FAIXA1);
            BigDecimal valorFaixa2 = diferencaFaixa2.multiply(ALIQUOTA_9);
            inss = valorFaixa1.add(valorFaixa2);

        } else if (salarioBruto.compareTo(LIMITE_FAIXA3) <= 0) {
            BigDecimal valorFaixa1 = LIMITE_FAIXA1.multiply(ALIQUOTA_7_5);
            BigDecimal valorFaixa2 = LIMITE_FAIXA2.subtract(LIMITE_FAIXA1).multiply(ALIQUOTA_9);
            BigDecimal diferencaFaixa3 = salarioBruto.subtract(LIMITE_FAIXA2);
            BigDecimal valorFaixa3 = diferencaFaixa3.multiply(ALIQUOTA_12);
            inss = valorFaixa1.add(valorFaixa2).add(valorFaixa3);

        } else if (salarioBruto.compareTo(LIMITE_FAIXA4) <= 0) {
            BigDecimal valorFaixa1 = LIMITE_FAIXA1.multiply(ALIQUOTA_7_5);
            BigDecimal valorFaixa2 = LIMITE_FAIXA2.subtract(LIMITE_FAIXA1).multiply(ALIQUOTA_9);
            BigDecimal valorFaixa3 = LIMITE_FAIXA3.subtract(LIMITE_FAIXA2).multiply(ALIQUOTA_12);
            BigDecimal diferencaFaixa4 = salarioBruto.subtract(LIMITE_FAIXA3);
            BigDecimal valorFaixa4 = diferencaFaixa4.multiply(ALIQUOTA_14);
            inss = valorFaixa1.add(valorFaixa2).add(valorFaixa3).add(valorFaixa4);

        } else {
            BigDecimal valorFaixa1 = LIMITE_FAIXA1.multiply(ALIQUOTA_7_5);
            BigDecimal valorFaixa2 = LIMITE_FAIXA2.subtract(LIMITE_FAIXA1).multiply(ALIQUOTA_9);
            BigDecimal valorFaixa3 = LIMITE_FAIXA3.subtract(LIMITE_FAIXA2).multiply(ALIQUOTA_12);
            BigDecimal valorFaixa4 = LIMITE_FAIXA4.subtract(LIMITE_FAIXA3).multiply(ALIQUOTA_14);
            inss = valorFaixa1.add(valorFaixa2).add(valorFaixa3).add(valorFaixa4);
        }

        return inss.setScale(2, RoundingMode.HALF_UP);
    }
}