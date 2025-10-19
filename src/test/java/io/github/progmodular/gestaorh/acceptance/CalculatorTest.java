package io.github.progmodular.gestaorh.acceptance;

import io.github.progmodular.gestaorh.service.configcalculator.GrauInsalubridade;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorDanger;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorFgts;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorFoodValue;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorHoursSalary;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorInss;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorIrff;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorNetSalary;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorUnhealthiness;
import io.github.progmodular.gestaorh.service.configcalculator.calculator.CalculatorValueTransport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    @DisplayName("Teste CalculatorDanger - cálculo de periculosidade")
    void testCalculatorDanger() {

        BigDecimal salarioBruto = new BigDecimal("3000.00");
        CalculatorDanger calculator = new CalculatorDanger(salarioBruto);


        BigDecimal resultado = calculator.calculator();
        BigDecimal expected = new BigDecimal("900.00");


        assertEquals(0, expected.compareTo(resultado),
                "O cálculo de periculosidade deve ser 30% do salário bruto");
    }

    @Test
    @DisplayName("Teste CalculatorFgts - cálculo do FGTS")
    void testCalculatorFgts() {

        BigDecimal salarioBruto = new BigDecimal("2500.00");
        CalculatorFgts calculator = new CalculatorFgts(salarioBruto);


        BigDecimal resultado = calculator.calculator();
        BigDecimal expected = new BigDecimal("200.00");


        assertEquals(0, expected.compareTo(resultado),
                "O cálculo do FGTS deve ser 8% do salário bruto");
    }

    @Test
    @DisplayName("Teste CalculatorFoodValue - cálculo do vale alimentação")
    void testCalculatorFoodValue() {

        int diasTrabalhados = 22;
        BigDecimal valeDiario = new BigDecimal("15.00");
        CalculatorFoodValue calculator = new CalculatorFoodValue(diasTrabalhados, valeDiario);


        BigDecimal resultado = calculator.calculator();
        BigDecimal expected = new BigDecimal("330.00");


        assertEquals(0, expected.compareTo(resultado),
                "O cálculo do vale alimentação deve ser dias trabalhados * vale diário");
    }
    // Uso errôneo de assertEquals
    @Test
    @DisplayName("Teste CalculatorHoursSalary - cálculo do salário por hora")
    void testCalculatorHoursSalary() {

        BigDecimal salarioBruto = new BigDecimal("2200.00");
        int diasTrabalhadosSemana = 5;
        int horasDias = 8;
        CalculatorHoursSalary calculator = new CalculatorHoursSalary(salarioBruto, diasTrabalhadosSemana, horasDias);


        BigDecimal resultado = calculator.calculator();
        BigDecimal expected = new BigDecimal("11.00");


        assertEquals(0, expected.compareTo(resultado),
                "O cálculo do salário por hora deve estar correto");
    }

    @Test
    @DisplayName("Teste CalculatorInss - cálculo do INSS para diferentes faixas")
    void testCalculatorInss() {

         CalculatorInss calculator1 = new CalculatorInss(new BigDecimal("1300.00"));
         BigDecimal resultado1 = calculator1.calculator();
         BigDecimal expected1 = new BigDecimal("97.50");
         assertEquals(expected1, resultado1, "INSS faixa 1 deve ser 7.5%");


         CalculatorInss calculator2 = new CalculatorInss(new BigDecimal("2000.00"));
         BigDecimal resultado2 = calculator2.calculator();

         BigDecimal expected2 = new BigDecimal("160.20");
         assertEquals(expected2, resultado2, "INSS faixa 2 deve calcular corretamente");


         CalculatorInss calculator3 = new CalculatorInss(new BigDecimal("3500.00"));
         BigDecimal resultado3 = calculator3.calculator();

         BigDecimal expected3 = new BigDecimal("323.06");
         assertEquals(expected3, resultado3, "INSS faixa 3 deve calcular corretamente");


         CalculatorInss calculator4 = new CalculatorInss(new BigDecimal("5000.00"));
         BigDecimal resultado4 = calculator4.calculator();

         BigDecimal expected4 = new BigDecimal("525.92");
         assertEquals(expected4, resultado4, "INSS faixa 4 deve calcular corretamente");


         CalculatorInss calculator5 = new CalculatorInss(new BigDecimal("8000.00"));
         BigDecimal resultado5 = calculator5.calculator();

         BigDecimal expected5 = new BigDecimal("876.97");
         assertEquals(expected5, resultado5, "INSS acima do teto deve aplicar valor máximo");
    }

    @Test
    @DisplayName("Teste CalculatorIrff - cálculo do IRRF para diferentes faixas")
    void testCalculatorIrff() {


        CalculatorIrff calculator1 = new CalculatorIrff(
                new BigDecimal("2000.00"), new BigDecimal("150.00"), 0);
        BigDecimal resultado1 = calculator1.calculator();
        BigDecimal expected1 = BigDecimal.ZERO;

        assertEquals(0, expected1.compareTo(resultado1), "IRRF deve ser zero para base de cálculo até 1903.98");

        CalculatorIrff calculator2 = new CalculatorIrff(
                new BigDecimal("3000.00"), new BigDecimal("200.00"), 1);
        BigDecimal resultado2 = calculator2.calculator();

        BigDecimal expected2 = new BigDecimal("52.98");
        assertEquals(expected2, resultado2.setScale(2, RoundingMode.HALF_UP), "IRRF faixa 7.5% deve calcular corretamente");

        CalculatorIrff calculator3 = new CalculatorIrff(
                new BigDecimal("4000.00"), new BigDecimal("300.00"), 0);
        BigDecimal resultado3 = calculator3.calculator();

        BigDecimal expected3 = new BigDecimal("200.20");
        assertEquals(expected3, resultado3.setScale(2, RoundingMode.HALF_UP), "IRRF faixa 15% deve calcular corretamente");
    }

    @Test
    @DisplayName("Teste CalculatorNetSalary - cálculo do salário líquido")
    void testCalculatorNetSalary() {

        BigDecimal salarioBruto = new BigDecimal("3000.00");
        BigDecimal inss = new BigDecimal("200.00");
        BigDecimal irrf = new BigDecimal("100.00");
        BigDecimal valeTransporte = new BigDecimal("150.00");

        CalculatorNetSalary calculator = new CalculatorNetSalary(salarioBruto, inss, irrf, valeTransporte);


        BigDecimal resultado = calculator.calculator();
        BigDecimal expected = new BigDecimal("2550.00");


        assertEquals(0, expected.compareTo(resultado),
                "O salário líquido deve ser salário bruto menos descontos");
    }

    @Test
    @DisplayName("Teste CalculatorUnhealthiness - cálculo de insalubridade para diferentes graus")
    void testCalculatorUnhealthiness() {

        CalculatorUnhealthiness calculator1 = new CalculatorUnhealthiness(
                GrauInsalubridade.MINIMO, new BigDecimal("2000.00"));
        BigDecimal resultado1 = calculator1.calculator();
        BigDecimal expected1 = new BigDecimal("2200.00");
        assertEquals(0, expected1.compareTo(resultado1),
                "Insalubridade grau mínimo deve adicionar 10%");


        CalculatorUnhealthiness calculator2 = new CalculatorUnhealthiness(
                GrauInsalubridade.MEDIO, new BigDecimal("2000.00"));
        BigDecimal resultado2 = calculator2.calculator();
        BigDecimal expected2 = new BigDecimal("2400.00");
        assertEquals(0, expected2.compareTo(resultado2),
                "Insalubridade grau médio deve adicionar 20%");


        CalculatorUnhealthiness calculator3 = new CalculatorUnhealthiness(
                GrauInsalubridade.MAXIMO, new BigDecimal("2000.00"));
        BigDecimal resultado3 = calculator3.calculator();
        BigDecimal expected3 = new BigDecimal("2800.00");
        assertEquals(0, expected3.compareTo(resultado3),
                "Insalubridade grau máximo deve adicionar 40%");
    }

    @Test
    @DisplayName("Teste CalculatorValueTransport - cálculo do vale transporte")
    void testCalculatorValueTransport() {

        CalculatorValueTransport calculator1 = new CalculatorValueTransport(
                new BigDecimal("2000.00"), new BigDecimal("100.00"));
        BigDecimal resultado1 = calculator1.calculator();
        BigDecimal expected1 = new BigDecimal("120.00");
        assertEquals(0, expected1.compareTo(resultado1),
                "Vale transporte deve ser 6% do salário quando valor informado é menor");


        CalculatorValueTransport calculator2 = new CalculatorValueTransport(
                new BigDecimal("2000.00"), new BigDecimal("200.00"));
        BigDecimal resultado2 = calculator2.calculator();
        BigDecimal expected2 = new BigDecimal("120.00");
        assertEquals(0, expected2.compareTo(resultado2),
                "Vale transporte deve limitar a 6% do salário quando valor informado é maior");
    }

    @Test
    @DisplayName("Teste valores nulos e negativos")
    void testValoresInvalidos() {

        CalculatorFgts calculator1 = new CalculatorFgts(BigDecimal.ZERO);
        BigDecimal resultado1 = calculator1.calculator();

        assertEquals(0, resultado1.compareTo(BigDecimal.ZERO),
                "FGTS de salário zero deve ser zero");


        CalculatorFoodValue calculator2 = new CalculatorFoodValue(0, new BigDecimal("15.00"));
        BigDecimal resultado2 = calculator2.calculator();

        assertEquals(0, resultado2.compareTo(BigDecimal.ZERO),
                "Vale alimentação com zero dias deve ser zero");
    }
}