package io.github.progmodular.gestaorh.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraFolhaTest {

    private CalculadoraFolha calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraFolha(5000.00, 160); // Exemplo: R$5000 bruto, 160 horas/mês
    }

    @Test
    void testCalcularSalarioHora() {
        double salarioHora = calculadora.calcularSalarioHora(8, 5); // 8h/dia, 5 dias/semana
        assertEquals(31.25, salarioHora, 0.01);
    }

    @Test
    void testCalcularPericulosidade() {
        double salarioComPericulosidade = calculadora.calcularPericulosidade();
        assertEquals(6500.00, salarioComPericulosidade, 0.01); // 5000 + 30%
    }

    @Test
    void testCalcularInsalubridadeBaixo() {
        double novoSalario = calculadora.calcularInsalubridade("baixo");
        assertEquals(5500.00, novoSalario, 0.01); // 5000 + 10%
    }

    @Test
    void testCalcularInsalubridadeMedio() {
        calculadora.setSalarioBruto(5000.00);
        double novoSalario = calculadora.calcularInsalubridade("medio");
        assertEquals(6000.00, novoSalario, 0.01); // 5000 + 20%
    }

    @Test
    void testCalcularInsalubridadeAlto() {
        calculadora.setSalarioBruto(5000.00);
        double novoSalario = calculadora.calcularInsalubridade("alto");
        assertEquals(7000.00, novoSalario, 0.01); // 5000 + 40%
    }

    @Test
    void testCalcularValeTransporte() {
        calculadora.setValeTransporte(100.00); // valor menor que 6%
        double vt = calculadora.calcularValeTransporte();
        assertEquals(300.00, vt, 0.01); // 6% de 5000
    }

    @Test
    void testCalcularValeAlimentacao() {
        double va = calculadora.calcularValeAlimentacao(20.00, 22);
        assertEquals(440.00, va, 0.01);
    }

    @Test
    void testCalcularINSS() {
        calculadora.setSalarioBruto(3000.00);
        double inss = calculadora.calcularINSS();
        assertEquals(330.64, inss, 0.01); // calculado em faixas
    }

    @Test
    void testCalcularFGTS() {
        double fgts = calculadora.calcularFGTS();
        assertEquals(400.00, fgts, 0.01); // 8% de 5000
    }

    @Test
    void testCalcularIRRF() {
        calculadora.setInss(330.64); // Simulando valor de INSS
        calculadora.setDependentes(2); // 2 dependentes
        double irrf = calculadora.calcularIRRF();
        assertEquals(84.41, irrf, 0.01); // Calculado com base no INSS + dependentes
    }

    @Test
    void testCalcularSalarioLiquido() {
        calculadora.setInss(330.64);
        calculadora.setIrrf(84.41);
        calculadora.setValeTransporte(300.00);
        double liquido = calculadora.calcularSalarioLiquido();
        assertEquals(4284.95, liquido, 0.01); // 5000 - (INSS + IRRF + VT)
    }

    @Test
    void testCalcularInsalubridadeValorInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calculadora.calcularInsalubridade("muito alto");
        });
        assertEquals("Unexpected value: muito alto", exception.getMessage());
    }
}
