package io.github.progmodular.gestaorh.service.configcalculator.calculator;

import io.github.progmodular.gestaorh.service.configcalculator.CalculatorAbstract;
import io.github.progmodular.gestaorh.service.configcalculator.CalculatorInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorINSS extends CalculatorAbstract implements CalculatorInterface {

    private static final BigDecimal LIMITE_FAIXA1 = new BigDecimal("1302.00");
    private static final BigDecimal LIMITE_FAIXA2 = new BigDecimal("2571.29");
    private static final BigDecimal LIMITE_FAIXA3 = new BigDecimal("3856.94");
    private static final BigDecimal LIMITE_FAIXA4 = new BigDecimal("7507.49");

    private static final BigDecimal ALIQUOTA_7_5 = new BigDecimal("0.075");
    private static final BigDecimal ALIQUOTA_9 = new BigDecimal("0.09");
    private static final BigDecimal ALIQUOTA_12 = new BigDecimal("0.12");
    private static final BigDecimal ALIQUOTA_14 = new BigDecimal("0.14");
    private static final BigDecimal TETO_INSS_FAIXA4 = new BigDecimal("7507.49");


    private static final BigDecimal TETO_1 = LIMITE_FAIXA1.multiply(ALIQUOTA_7_5);
    private static final BigDecimal TETO_2_DIF = LIMITE_FAIXA2.subtract(LIMITE_FAIXA1);
    private static final BigDecimal TETO_2 = TETO_1.add(TETO_2_DIF.multiply(ALIQUOTA_9));
    private static final BigDecimal TETO_3_DIF = LIMITE_FAIXA3.subtract(LIMITE_FAIXA2);
    private static final BigDecimal TETO_3 = TETO_2.add(TETO_3_DIF.multiply(ALIQUOTA_12));
    private static final BigDecimal TETO_4_DIF = TETO_INSS_FAIXA4.subtract(LIMITE_FAIXA3);

    public CalculatorINSS(BigDecimal salarioBruto)
    {
        this.salarioBruto = salarioBruto;
    }


    public BigDecimal calculator() {

        BigDecimal inss;


        if (salarioBruto.compareTo(LIMITE_FAIXA1) <= 0) {

            inss = salarioBruto.multiply(ALIQUOTA_7_5)
                    .setScale(2, RoundingMode.HALF_UP);


        } else if (salarioBruto.compareTo(LIMITE_FAIXA2) <= 0) {

            BigDecimal diferenca = salarioBruto.subtract(LIMITE_FAIXA1);
            inss = TETO_1.add(diferenca.multiply(ALIQUOTA_9))
                    .setScale(2, RoundingMode.HALF_UP);


        } else if (salarioBruto.compareTo(LIMITE_FAIXA3) <= 0) {

            BigDecimal diferenca = salarioBruto.subtract(LIMITE_FAIXA2);
            inss = TETO_2.add(diferenca.multiply(ALIQUOTA_12))
                    .setScale(2, RoundingMode.HALF_UP);


        } else if (salarioBruto.compareTo(LIMITE_FAIXA4) <= 0) {

            BigDecimal diferenca = salarioBruto.subtract(LIMITE_FAIXA3);
            inss = TETO_3.add(diferenca.multiply(ALIQUOTA_14))
                    .setScale(2, RoundingMode.HALF_UP);


        } else {

            inss = TETO_3.add(TETO_4_DIF.multiply(ALIQUOTA_14))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return inss;
    }
}
