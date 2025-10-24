package io.github.progmodular.gestaorh.service.paymentservice.dados;

import java.math.BigDecimal;

public interface IFolhaPagamentoDados {

    String getNomeFuncionario();
    String getCargoFuncionario();

    BigDecimal getSalarioBruto();

    BigDecimal getDescontoINSS();
    BigDecimal getDescontoIRRF();
    BigDecimal getTotalDescontos();

    BigDecimal getSalarioLiquido();
}
