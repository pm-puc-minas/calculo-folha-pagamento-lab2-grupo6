package io.github.progmodular.gestaorh.service.report;

import io.github.progmodular.gestaorh.service.payment.dados.IFolhaPagamentoDados;

public interface IFormatadorRelatorio {


    String formatar(IFolhaPagamentoDados dados);
}