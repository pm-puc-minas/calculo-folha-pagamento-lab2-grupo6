package com.folha.pagamento.relatorio;

import com.folha.pagamento.dados.IFolhaPagamentoDados;

/**
 * Classe principal de sua responsabilidade.
 * Ela usa a injeção de dependência para se manter desacoplada.
 */
public class RelatorioFolha {

    // A classe principal só conhece o contrato do formatador, não a implementação.
    private final IFormatadorRelatorio formatador;

    // Construtor: O formatador (MS-DOS) é injetado aqui.
    public RelatorioFolha(IFormatadorRelatorio formatador) {
        // Isso garante o baixo acoplamento (RFN)
        this.formatador = formatador;
    }

    /**
     * Gera o relatório da folha de pagamento usando o formatador configurado.
     * @param dados O objeto com os dados calculados (que deve implementar IFolhaPagamentoDados).
     * @return O relatório formatado como String.
     */
    public String gerarRelatorio(IFolhaPagamentoDados dados) {

        return formatador.formatar(dados);
    }
}