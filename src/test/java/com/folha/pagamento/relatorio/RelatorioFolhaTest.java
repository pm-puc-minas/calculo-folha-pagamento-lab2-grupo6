package com.folha.pagamento.relatorio;

import com.folha.pagamento.dados.IFolhaPagamentoDados;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CLASSE DE MOCK: Simula os dados calculados pelo backend (FolhaPagamento).
 * Garante que o teste seja isolado e independente do código de outros
 */
class MockFolhaPagamentoDados implements IFolhaPagamentoDados {
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

/**
 * Testes unitários para a classe RelatorioFolha
 */
class RelatorioFolhaTest {

    // 1. Injeta os dados mockados no  RelatorioFolha
    private final IFolhaPagamentoDados dadosTeste = new MockFolhaPagamentoDados();
    private final RelatorioFolha relatorio = new RelatorioFolha(new FormatadorConsoleRetro());

    @Test
    void deveGerarRelatorioComSalarioLiquidoCorretamenteFormatado() {
        String resultado = relatorio.gerarRelatorio(dadosTeste);

        // substituir qualquer espaço invisível ('\u00A0')
        // por um espaço normal (' ') antes da comparação.
        String resultadoNormalizado = resultado.replace('\u00A0', ' ');

        //  comparação é feita com a string normalizada, garantindo que o teste passe.
        assertTrue(resultadoNormalizado.contains("R$ 5.780,57"),
                "O relatório deve conter o valor líquido R$ 5.780,57 formatado em Reais.");

        // Verificação de Título e Nome (RF10)
        assertTrue(resultadoNormalizado.contains("DEMONSTRATIVO DE PAGAMENTO"), "Deve ter o título principal.");
        assertTrue(resultadoNormalizado.contains("Colaborador: Alexandre Teste"), "Deve incluir o nome do colaborador.");
    }

    @Test
    void deveUtilizarVisualRetroMSDOS() {

        String resultado = relatorio.gerarRelatorio(dadosTeste);
        assertTrue(resultado.contains("╔"), "Deve ter o caractere MS-DOS '╔' para desenho de caixa.");
    }
}