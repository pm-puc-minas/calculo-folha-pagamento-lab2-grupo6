package io.github.progmodular.gestaorh.service.calculatorservice;

import lombok.Getter;

import java.math.BigDecimal;

public enum GrauInsalubridade {

    MINIMO("Mínimo", new BigDecimal("0.10")),

    MEDIO("Médio", new BigDecimal("0.20")),

    MAXIMO("Máximo", new BigDecimal("0.40"));

    @Getter
    private final String descricao;
    @Getter
    private final BigDecimal percentual;

    GrauInsalubridade(String descricao, BigDecimal percentual) {
        this.descricao = descricao;
        this.percentual = percentual;
    }

}
