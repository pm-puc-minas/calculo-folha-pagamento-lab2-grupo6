package io.github.progmodular.gestaorh.service.report;

import io.github.progmodular.gestaorh.service.payment.dados.IFolhaPagamentoDados;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Implementação do formatador com visual retrô MS-DOS (Requisito Extra).
 * Implementa o contrato IFormatadorRelatorio.
 */
public class FormatadorConsoleRetro implements IFormatadorRelatorio {

    // Ferramenta para formatar valores em R$ com separador de milhar (RFN - Usabilidade).
    // Garante que a formatação use o padrão BR (R$, ponto de milhar, vírgula decimal)
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public String formatar(IFolhaPagamentoDados dados) {
        StringBuilder sb = new StringBuilder();

        // --- CABEÇALHO MS-DOS --- (Requisito Extra)
        sb.append("╔══════════════════════════════════════════╗\n");
        sb.append("║           DEMONSTRATIVO DE PAGAMENTO           ║\n");
        sb.append("╠══════════════════════════════════════════╣\n");

        // RF10 - Informações do Colaborador
        sb.append("║ Colaborador: ").append(dados.getNomeFuncionario()).append("\n");
        sb.append("║ Cargo: ").append(dados.getCargoFuncionario()).append("\n");

        sb.append("╠══════════════════════════════════════════╣\n");
        sb.append("║           PROVENTOS E DESCONTOS          ║\n");
        sb.append("╠══════════════════════════════════════════╣\n");

        // --- PROVENTOS --- (RF10)
        // Usamos nf.format() para garantir a formatação R$ 7.507,49
        sb.append("║ SALÁRIO BRUTO: ").append(nf.format(dados.getSalarioBruto())).append(" (PROVENTO)\n");

        // --- DESCONTOS --- (RF10)
        sb.append("║ Desconto INSS: ").append(nf.format(dados.getDescontoINSS())).append(" (DESCONTO)\n");
        sb.append("║ Desconto IRRF: ").append(nf.format(dados.getDescontoIRRF())).append(" (DESCONTO)\n");

        // --- TOTAIS ---
        sb.append("╠══════════════════════════════════════════╣\n");

        // RF10 - Resultados Finais
        sb.append("║ Total Descontos: ").append(nf.format(dados.getTotalDescontos())).append("\n");

        sb.append("║ Salário LÍQUIDO: ").append(nf.format(dados.getSalarioLiquido())).append("\n");

        // --- RODAPÉ
        sb.append("╚══════════════════════════════════════════╝\n");

        return sb.toString();
    }
}