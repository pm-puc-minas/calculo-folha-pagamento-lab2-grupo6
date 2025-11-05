package io.github.progmodular.gestaorh.service.calculatorservice;

import lombok.Getter;

import java.math.BigDecimal;

public enum DegreeUnhealthiness {

    MINIMUM("Minimum", new BigDecimal("0.10")),

    AVERAGE("Average", new BigDecimal("0.20")),

    MAXIMUM("Maximum", new BigDecimal("0.40"));

    @Getter
    private final String description;
    @Getter
    private final BigDecimal percentage;

    DegreeUnhealthiness(String description, BigDecimal percentage) {
        this.description = description;
        this.percentage = percentage;
    }

}
