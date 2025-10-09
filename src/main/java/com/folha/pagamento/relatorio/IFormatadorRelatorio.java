package com.folha.pagamento.relatorio;

import com.folha.pagamento.dados.IFolhaPagamentoDados;

public interface IFormatadorRelatorio {


    String formatar(IFolhaPagamentoDados dados);
}