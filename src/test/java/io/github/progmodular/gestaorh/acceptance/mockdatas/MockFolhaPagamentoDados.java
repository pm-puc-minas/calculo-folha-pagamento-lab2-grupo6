package io.github.progmodular.gestaorh.acceptance.mockdatas;

import io.github.progmodular.gestaorh.service.payment.dados.IFolhaPagamentoDados;

import java.math.BigDecimal;

public class MockFolhaPagamentoDados implements IFolhaPagamentoDados {
    // Valores FIXOS (MOCK) para ver se é  testada corretamente
    @Override public String getNomeFuncionario() { return "Alexandre Teste"; }
    @Override public String getCargoFuncionario() { return "Analista de Sistemas"; }
    @Override public BigDecimal getSalarioBruto() { return new BigDecimal("7507.49"); }

    // Valores do Exemplo de Cálculo
    @Override public BigDecimal getDescontoINSS() { return new BigDecimal("877.24"); }
    @Override public BigDecimal getDescontoIRRF() { return new BigDecimal("849.68"); }
    @Override public BigDecimal getTotalDescontos() { return new BigDecimal("1726.92"); }

    // Salário Líquido esperado
    @Override public BigDecimal getSalarioLiquido() { return new BigDecimal("5780.57"); }
}
