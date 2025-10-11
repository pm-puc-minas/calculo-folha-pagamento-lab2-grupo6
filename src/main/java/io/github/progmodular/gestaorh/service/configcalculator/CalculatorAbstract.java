package io.github.progmodular.gestaorh.service.configcalculator;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public abstract class CalculatorAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected BigDecimal salarioBruto;
    protected BigDecimal salarioHora;
    protected BigDecimal insalubridade;
    protected BigDecimal valeTransporte;
    protected BigDecimal inss;
    protected final BigDecimal fgtsPercentage = new BigDecimal("0.08");
    protected BigDecimal salarioLiquido;
    protected BigDecimal irrf;
    protected BigDecimal valeDiario;
    protected final BigDecimal dangerPercentage = new BigDecimal("0.3");
    protected Integer horasMensais;
    protected Integer horasDias;
    protected Integer diasTrabalhados;
    protected Integer diasTrabalhadosSemana;
    protected Integer dependentes;
    protected GrauInsalubridade grauInsalubridade;

}
