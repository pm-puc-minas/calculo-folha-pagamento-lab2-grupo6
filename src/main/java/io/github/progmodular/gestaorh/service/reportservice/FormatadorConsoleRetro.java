package io.github.progmodular.gestaorh.service.reportservice;

import io.github.progmodular.gestaorh.service.paymentservice.dados.IFolhaPagamentoDados;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatadorConsoleRetro implements IFormatadorRelatorio {

  //Locale é um comando obsoleto, seria interessante tentar usar um mais recente
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public String formatar(IFolhaPagamentoDados dados) {


        return "╔══════════════════════════════════════════╗\n" +
                "║           DEMONSTRATIVO DE PAGAMENTO           ║\n" +
                "╠══════════════════════════════════════════╣\n" +
                "║ Colaborador: " + dados.getNomeFuncionario() + "\n" +
                "║ Cargo: " + dados.getCargoFuncionario() + "\n" +
                "╠══════════════════════════════════════════╣\n" +
                "║           PROVENTOS E DESCONTOS          ║\n" +
                "╠══════════════════════════════════════════╣\n" +
                "║ SALÁRIO BRUTO: " + nf.format(dados.getSalarioBruto()) + " (PROVENTO)\n" +
                "║ Desconto INSS: " + nf.format(dados.getDescontoINSS()) + " (DESCONTO)\n" +
                "║ Desconto IRRF: " + nf.format(dados.getDescontoIRRF()) + " (DESCONTO)\n" +
                "╠══════════════════════════════════════════╣\n" +
                "║ Total Descontos: " + nf.format(dados.getTotalDescontos()) + "\n" +
                "║ Salário LÍQUIDO: " + nf.format(dados.getSalarioLiquido()) + "\n" +
                "╚══════════════════════════════════════════╝\n";
    }
}
