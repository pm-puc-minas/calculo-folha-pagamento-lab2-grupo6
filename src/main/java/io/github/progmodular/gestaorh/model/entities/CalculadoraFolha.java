package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
public class CalculadoraFolha {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private double salarioBruto;
    @Setter
    @Getter
    private int horasMensais;
    @Setter
    @Getter
    private double salarioHora;
    @Getter
    @Setter
    private double insalubridade;
    @Getter
    @Setter
    private double valeTransporte;
    @Getter
    @Setter
    private double valeAlimentacao;
    @Getter
    @Setter
    private double inss;
    @Getter
    @Setter
    private double fgts;
    @Getter
    @Setter
    private double irrf;
    @Getter
    @Setter
    private double salarioLiquido;
    @Getter
    @Setter
    private int dependentes;

    public CalculadoraFolha() {
    }

   
    public CalculadoraFolha(double salarioBruto, int horasMensais) {
        this.salarioBruto = salarioBruto;
        this.horasMensais = horasMensais;
    }

    
    public double calcularSalarioHora(int horasTrabalhadasPorDia, int diasTrabalhadosPorSemana) {
        double jornadaSemanal = horasTrabalhadasPorDia * diasTrabalhadosPorSemana;
        double jornadaMensal = jornadaSemanal * 5;
        salarioHora = salarioBruto / jornadaMensal;
        return salarioHora;
    }

    
    public double calcularPericulosidade() {
        salarioBruto = salarioBruto * 0.3;
        return salarioBruto;
    }

   
    public double calcularInsalubridade(String grauInsalubridade) {
        grauInsalubridade.toLowerCase();
        switch (grauInsalubridade) {
            case "baixo":
                insalubridade = salarioBruto * 0.1;
            case "medio":
                insalubridade = salarioBruto * 0.2;
                break;
            case "alto":
                insalubridade = salarioBruto * 0.4;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + grauInsalubridade);
        }
        salarioBruto += insalubridade;
        setInsalubridade(insalubridade);
        return salarioBruto;
    }

   
    public double calcularValeTransporte() {
        double descontoMaximo = salarioBruto * 0.06;

        if (valeTransporte < descontoMaximo) {
            this.valeTransporte = descontoMaximo;
            return valeTransporte;
        } else {
            this.valeTransporte = descontoMaximo;
            return descontoMaximo;
        }
    }

   
    public double calcularValeAlimentacao(double valeDiario, int diasTrabalhados) {
        valeAlimentacao = valeDiario * diasTrabalhados;
        return valeAlimentacao;
    }

    
    public double calcularINSS() {
        double salario = salarioBruto;
        double inss = 0;

        if (salario <= 1302.00) {
            inss = salario * 0.075;
        } else if (salario <= 2571.29) {
            inss = (1302.00 * 0.075) + ((salario - 1302.00) * 0.09);
        } else if (salario <= 3856.94) {
            inss = (1302.00 * 0.075) + ((2571.29 - 1302.00) * 0.09) + ((salario - 2571.29) * 0.12);
        } else if (salario <= 7507.49) {
            inss = (1302.00 * 0.075) + ((2571.29 - 1302.00) * 0.09) + ((3856.94 - 2571.29) * 0.12) + ((salario - 3856.94) * 0.14);
        } else {
            inss = (1302.00 * 0.075) + ((2571.29 - 1302.00) * 0.09) + ((3856.94 - 2571.29) * 0.12) + ((7507.49 - 3856.94) * 0.14);
        }

        return inss;
    }

   
    public double calcularFGTS() {
        fgts = salarioBruto * 0.08;
        return fgts;
    }

    public double calcularIRRF() {
        double salarioBase = salarioBruto - inss;
        double deducaoDependentes = dependentes * 189.59;
        double baseCalculo = salarioBase - deducaoDependentes;
        double irrf = 0;
        double aliquota = 0;
        double deducao = 0;

        if (baseCalculo <= 1903.98) {
            aliquota = 0;
            deducao = 0;
        } else if (baseCalculo <= 2826.65) {
            aliquota = 0.075;
            deducao = 142.80;
        } else if (baseCalculo <= 3751.05) {
            aliquota = 0.15;
            deducao = 354.80;
        } else if (baseCalculo <= 4664.68) {
            aliquota = 0.225;
            deducao = 636.13;
        } else {
            aliquota = 0.275;
            deducao = 869.36;
        }

        irrf = (baseCalculo * aliquota) - deducao;

        if (irrf < 0) {
            irrf = 0;
        }

        this.irrf = irrf;
        return irrf;
    }

   
    public double calcularSalarioLiquido() {
        salarioLiquido = salarioBruto - (inss + irrf + valeTransporte);
        return salarioLiquido;
    }
}

   

