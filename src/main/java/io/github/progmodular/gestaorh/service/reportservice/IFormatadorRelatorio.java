package io.github.progmodular.gestaorh.service.reportservice;

import io.github.progmodular.gestaorh.service.paymentservice.dados.IFolhaPagamentoDados;

public interface IFormatadorRelatorio {


    String formatar(IFolhaPagamentoDados dados);
}