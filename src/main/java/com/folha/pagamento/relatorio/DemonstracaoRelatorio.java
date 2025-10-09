package com.folha.pagamento.relatorio;

import com.folha.pagamento.dados.IFolhaPagamentoDados;
import java.math.BigDecimal;

/**

 */
class MainDadosMock implements IFolhaPagamentoDados {
    // Dados Fixos de Exemplo (RF10)
    @Override public String getNomeFuncionario() { return "João do Relatório"; }
    @Override public String getCargoFuncionario() { return "Analista de Sistemas"; }
    @Override public BigDecimal getSalarioBruto() { return new BigDecimal("7507.49"); }

    // Dados Calculados (Que foram testados)
    @Override public BigDecimal getDescontoINSS() { return new BigDecimal("877.24"); }
    @Override public BigDecimal getDescontoIRRF() { return new BigDecimal("849.68"); }
    @Override public BigDecimal getTotalDescontos() { return new BigDecimal("1726.92"); }
    @Override public BigDecimal getSalarioLiquido() { return new BigDecimal("5780.57"); }
}

/**
 *
 *(Main) para demonstrar a execução do (Relatório).
 */
public class DemonstracaoRelatorio {

    public static void main(String[] args) {

        IFolhaPagamentoDados dadosDaFolha = new MainDadosMock();

        IFormatadorRelatorio formatador = new FormatadorConsoleRetro();

        RelatorioFolha geradorDeRelatorio = new RelatorioFolha(formatador);

        String relatorioFormatado = geradorDeRelatorio.gerarRelatorio(dadosDaFolha);


        System.out.println("===============================================");
        System.out.println(relatorioFormatado);
        System.out.println("==================================================");
    }
}