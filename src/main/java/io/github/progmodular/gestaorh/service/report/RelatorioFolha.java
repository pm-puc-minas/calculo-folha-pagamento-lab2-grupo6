package io.github.progmodular.gestaorh.service.report;

import io.github.progmodular.gestaorh.service.payment.dados.IFolhaPagamentoDados;

public class RelatorioFolha {

    private final IFormatadorRelatorio formatador;
    public RelatorioFolha(IFormatadorRelatorio formatador) {
        // Isso garante o baixo acoplamento (RFN)
        this.formatador = formatador;
    }

    public String gerarRelatorio(IFolhaPagamentoDados dados) {

        return formatador.formatar(dados);
    }
}
