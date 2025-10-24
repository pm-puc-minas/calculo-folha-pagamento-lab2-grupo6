package io.github.progmodular.gestaorh.service.reportservice;

import io.github.progmodular.gestaorh.service.paymentservice.dados.IFolhaPagamentoDados;

public class RelatorioFolha {

    private final IFormatadorRelatorio formatador;
    public RelatorioFolha(IFormatadorRelatorio formatador) {
        this.formatador = formatador;
    }

    public String gerarRelatorio(IFolhaPagamentoDados dados) {

        return formatador.formatar(dados);
    }
}
