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
    protected BigDecimal fgts;
    protected BigDecimal salarioLiquido;
    protected BigDecimal irrf;
    protected BigDecimal valeDiario;
    protected int horasMensais;
    protected int horasDias;
    protected int diasTrabalhados;
    protected int diasTrabalhadosSemana;
    protected int dependentes;
    protected GrauInsalubridade grauInsalubridade;

}
