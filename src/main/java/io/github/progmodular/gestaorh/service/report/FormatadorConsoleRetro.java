package io.github.progmodular.gestaorh.service.report;

import io.github.progmodular.gestaorh.service.payment.dados.IFolhaPagamentoDados;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatadorConsoleRetro implements IFormatadorRelatorio {

    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public String formatar(IFolhaPagamentoDados dados) {
        StringBuilder sb = new StringBuilder();

    
        sb.append("╔══════════════════════════════════════════╗\n");
        sb.append("║           DEMONSTRATIVO DE PAGAMENTO           ║\n");
        sb.append("╠══════════════════════════════════════════╣\n");

        sb.append("║ Colaborador: ").append(dados.getNomeFuncionario()).append("\n");
        sb.append("║ Cargo: ").append(dados.getCargoFuncionario()).append("\n");

        sb.append("╠══════════════════════════════════════════╣\n");
        sb.append("║           PROVENTOS E DESCONTOS          ║\n");
        sb.append("╠══════════════════════════════════════════╣\n");

        sb.append("║ SALÁRIO BRUTO: ").append(nf.format(dados.getSalarioBruto())).append(" (PROVENTO)\n");
        sb.append("║ Desconto INSS: ").append(nf.format(dados.getDescontoINSS())).append(" (DESCONTO)\n");
        sb.append("║ Desconto IRRF: ").append(nf.format(dados.getDescontoIRRF())).append(" (DESCONTO)\n");
        sb.append("╠══════════════════════════════════════════╣\n");
        sb.append("║ Total Descontos: ").append(nf.format(dados.getTotalDescontos())).append("\n");

        sb.append("║ Salário LÍQUIDO: ").append(nf.format(dados.getSalarioLiquido())).append("\n");

        sb.append("╚══════════════════════════════════════════╝\n");

        return sb.toString();
    }
}
