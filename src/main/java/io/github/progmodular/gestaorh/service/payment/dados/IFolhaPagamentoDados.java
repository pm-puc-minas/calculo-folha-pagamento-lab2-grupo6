package io.github.progmodular.gestaorh.service.payment.dados;

import java.math.BigDecimal;

public interface IFolhaPagamentoDados {

    // RF10 e RFs de Funcionario
    String getNomeFuncionario();
    String getCargoFuncionario();

    // Proventos e Base
    BigDecimal getSalarioBruto();

    // Descontos (usando BigDecimal - Requisito Extra)
    BigDecimal getDescontoINSS();
    BigDecimal getDescontoIRRF();
    BigDecimal getTotalDescontos();

    // Resultado Final
    BigDecimal getSalarioLiquido();
}
