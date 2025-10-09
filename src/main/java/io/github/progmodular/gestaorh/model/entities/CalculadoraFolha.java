package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Representa uma calculadora de folha de pagamento.
 * Contém métodos para calcular salário líquido, INSS, IRRF,
 * FGTS, vale transporte, vale alimentação, periculosidade e insalubridade.
 */
@Entity
public class CalculadoraFolha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double salarioBruto;
    private int horasMensais;
    private double salarioHora;
    private double insalubridade;
    private double valeTransporte;
    private double valeAlimentacao;
    private double inss;
    private double fgts;
    private double irrf;
    private double salarioLiquido;
    private int dependentes;

    public CalculadoraFolha() {
    }

    /**
     * Construtor com salário bruto e horas mensais.
     *
     * @param salarioBruto  Salário bruto do funcionário.
     * @param horasMensais  Quantidade de horas trabalhadas por mês.
     */
    public CalculadoraFolha(double salarioBruto, int horasMensais) {
        this.salarioBruto = salarioBruto;
        this.horasMensais = horasMensais;
    }

    /**
     * Calcula o valor da hora trabalhada com base na jornada diária e semanal.
     *
     * @param horasTrabalhadasPorDia    Horas trabalhadas por dia.
     * @param diasTrabalhadosPorSemana  Dias trabalhados por semana.
     * @return Valor da hora trabalhada.
     */
    public double calcularSalarioHora(int horasTrabalhadasPorDia, int diasTrabalhadosPorSemana) {
        double jornadaSemanal = horasTrabalhadasPorDia * diasTrabalhadosPorSemana;
        double jornadaMensal = jornadaSemanal * 5;
        salarioHora = salarioBruto / jornadaMensal;
        return salarioHora;
    }

    /**
     * Calcula o adicional de periculosidade (30% sobre o salário bruto).
     *
     * @return Salário bruto com adicional de periculosidade.
     */
    public double calcularPericulosidade() {
        salarioBruto = salarioBruto * 0.3;
        return salarioBruto;
    }

    /**
     * Calcula o adicional de insalubridade com base no grau informado.
     * Os percentuais são:
     * - Baixo: 10%
     * - Médio: 20%
     * - Alto: 40%
     *
     * @param grauInsalubridade Grau de insalubridade ("baixo", "medio", "alto").
     * @return Salário bruto com adicional de insalubridade.
     * @throws IllegalArgumentException Se o grau informado for inválido.
     */
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

    /**
     * Calcula o desconto de vale-transporte (máximo de 6% do salário bruto).
     *
     * @return Valor descontado de vale-transporte.
     */
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

    /**
     * Calcula o valor total do vale alimentação.
     *
     * @param valeDiario       Valor diário do vale.
     * @param diasTrabalhados  Quantidade de dias trabalhados no mês.
     * @return Valor total do vale alimentação.
     */
    public double calcularValeAlimentacao(double valeDiario, int diasTrabalhados) {
        valeAlimentacao = valeDiario * diasTrabalhados;
        return valeAlimentacao;
    }

    /**
     * Calcula o desconto do INSS de acordo com a tabela progressiva.
     *
     * @return Valor descontado de INSS.
     */
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

    /**
     * Calcula o valor do FGTS (8% do salário bruto).
     *
     * @return Valor do FGTS.
     */
    public double calcularFGTS() {
        fgts = salarioBruto * 0.08;
        return fgts;
    }

    /**
     * Calcula o desconto do IRRF com base no salário bruto,
     * INSS e número de dependentes.
     *
     * @return Valor descontado de IRRF.
     */
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

    /**
     * Calcula o salário líquido com base nos descontos de INSS, IRRF e vale-transporte.
     *
     * @return Salário líquido.
     */
    public double calcularSalarioLiquido() {
        salarioLiquido = salarioBruto - (inss + irrf + valeTransporte);
        return salarioLiquido;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public int getHorasMensais() {
        return horasMensais;
    }

    public void setHorasMensais(int horasMensais) {
        this.horasMensais = horasMensais;
    }

    public double getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(double salarioHora) {
        this.salarioHora = salarioHora;
    }

    public double getInsalubridade() {
        return insalubridade;
    }

   
