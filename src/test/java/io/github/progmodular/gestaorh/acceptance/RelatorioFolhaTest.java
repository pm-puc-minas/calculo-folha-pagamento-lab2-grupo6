package io.github.progmodular.gestaorh.acceptance;

import io.github.progmodular.gestaorh.acceptance.mockdatas.MockFolhaPagamentoDados;
import io.github.progmodular.gestaorh.service.payment.dados.IFolhaPagamentoDados;
import io.github.progmodular.gestaorh.service.report.FormatadorConsoleRetro;
import io.github.progmodular.gestaorh.service.report.RelatorioFolha;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;



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