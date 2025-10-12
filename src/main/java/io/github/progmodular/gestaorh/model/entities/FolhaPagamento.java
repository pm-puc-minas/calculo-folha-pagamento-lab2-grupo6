package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Classe que representa a folha de pagamento do funcionário
@Entity
@Table(name = "folha_pagamento")
@NoArgsConstructor
@AllArgsConstructor
public class FolhaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Funcionario funcionario;

    @Column(name = "mes_referencia", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Integer mesReferencia;

    @Column(name = "ano_referencia", nullable = false)
    @NotNull
    @Getter
    @Setter
    private Integer anoReferencia;

    @Column(name = "data_processamento")
    @Getter
    @Setter
    private LocalDate dataProcessamento;

    // Salário base do funcionário
    @Column(name = "salario_base", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal salarioBase;

    @Column(name = "horas_trabalhadas")
    @Getter
    @Setter
    private Integer horasTrabalhadas;

    @Column(name = "dependentes")
    @Getter
    @Setter
    private Integer dependentes = 0;

    // Valores que o funcionário recebe
    @Column(name = "salario_bruto", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal salarioBruto;

    @Column(name = "horas_extras", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal horasExtras = BigDecimal.ZERO;

    @Column(name = "adicional_periculosidade", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal adicionalPericulosidade = BigDecimal.ZERO;

    @Column(name = "adicional_insalubridade", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal adicionalInsalubridade = BigDecimal.ZERO;

    @Column(name = "vale_transporte", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal valeTransporte = BigDecimal.ZERO;

    @Column(name = "vale_alimentacao", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal valeAlimentacao = BigDecimal.ZERO;

    // Valores que são descontados do salário
    @Column(name = "desconto_inss", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal descontoINSS = BigDecimal.ZERO;

    @Column(name = "desconto_irrf", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal descontoIRRF = BigDecimal.ZERO;

    @Column(name = "desconto_fgts", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal descontoFGTS = BigDecimal.ZERO;

    @Column(name = "desconto_vale_transporte", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal descontoValeTransporte = BigDecimal.ZERO;

    // Totais finais
    @Column(name = "total_proventos", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal totalProventos = BigDecimal.ZERO;

    @Column(name = "total_descontos", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal totalDescontos = BigDecimal.ZERO;

    @Column(name = "salario_liquido", precision = 10, scale = 2)
    @Getter
    @Setter
    private BigDecimal salarioLiquido = BigDecimal.ZERO;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private StatusFolha status = StatusFolha.PROCESSANDO;

    // Método que faz todos os cálculos da folha
    public void processarFolha(CalculadoraFolha calculadoraFolha) {
        if (funcionario == null || salarioBase == null) {
            throw new IllegalStateException("Funcionário e salário base são obrigatórios para processar a folha");
        }

        // Prepara a calculadora
        calculadoraFolha.setSalarioBruto(salarioBase.doubleValue());
        calculadoraFolha.setDependentes(dependentes != null ? dependentes : 0);

        // Faz os cálculos
        calcularProventos(calculadoraFolha);
        calcularBeneficios(calculadoraFolha);
        calcularDescontos(calculadoraFolha);
        calcularTotais();

        // Salva a data e marca como processada
        this.dataProcessamento = LocalDate.now();
        this.status = StatusFolha.PROCESSADA;
    }

    // Calcula o que o funcionário vai receber
    private void calcularProventos(CalculadoraFolha calculadoraFolha) {
        this.salarioBruto = salarioBase;

        // Adicional de periculosidade (30% do salário)
        if (adicionalPericulosidade.compareTo(BigDecimal.ZERO) > 0) {
            double periculosidade = calculadoraFolha.calcularPericulosidade();
            this.adicionalPericulosidade = BigDecimal.valueOf(periculosidade - salarioBase.doubleValue());
            this.salarioBruto = BigDecimal.valueOf(periculosidade);
        }

        // Adicional de insalubridade
        if (adicionalInsalubridade.compareTo(BigDecimal.ZERO) > 0) {
            double insalubridade = calculadoraFolha.calcularInsalubridade("medio");
            this.adicionalInsalubridade = BigDecimal.valueOf(calculadoraFolha.getInsalubridade());
            this.salarioBruto = BigDecimal.valueOf(insalubridade);
        }

        // Calcula valor da hora se informado
        if (horasTrabalhadas != null && horasTrabalhadas > 0) {
            double salarioHora = calculadoraFolha.calcularSalarioHora(8, 5);
            calculadoraFolha.setSalarioHora(salarioHora);
        }
    }

    // Calcula os benefícios (vales)
    private void calcularBeneficios(CalculadoraFolha calculadoraFolha) {
        // Vale alimentação (R$ 25 por dia, 22 dias úteis)
        if (valeAlimentacao.compareTo(BigDecimal.ZERO) > 0) {
            double valorValeAlimentacao = calculadoraFolha.calcularValeAlimentacao(25.0, 22);
            this.valeAlimentacao = BigDecimal.valueOf(valorValeAlimentacao);
        }

        // Vale transporte (será descontado depois)
        if (valeTransporte.compareTo(BigDecimal.ZERO) > 0) {
            double valorValeTransporte = calculadoraFolha.calcularValeTransporte();
            this.valeTransporte = BigDecimal.valueOf(valorValeTransporte);
        }
    }

    // Calcula os descontos obrigatórios
    private void calcularDescontos(CalculadoraFolha calculadoraFolha) {
        // INSS (previdência)
        double inss = calculadoraFolha.calcularINSS();
        this.descontoINSS = BigDecimal.valueOf(inss);
        calculadoraFolha.setInss(inss);

        // IRRF (imposto de renda)
        double irrf = calculadoraFolha.calcularIRRF();
        this.descontoIRRF = BigDecimal.valueOf(irrf);

        // FGTS (não desconta do funcionário, é obrigação da empresa)
        double fgts = calculadoraFolha.calcularFGTS();
        this.descontoFGTS = BigDecimal.valueOf(fgts);

        // Desconto do vale transporte (máximo 6% do salário)
        if (valeTransporte.compareTo(BigDecimal.ZERO) > 0) {
            double descontoVT = Math.min(
                salarioBruto.doubleValue() * 0.06,
                valeTransporte.doubleValue()
            );
            this.descontoValeTransporte = BigDecimal.valueOf(descontoVT);
        }
    }

    // Soma tudo e calcula o salário final
    private void calcularTotais() {
        // Soma tudo que o funcionário recebe
        this.totalProventos = salarioBruto
            .add(horasExtras)
            .add(adicionalPericulosidade)
            .add(adicionalInsalubridade)
            .add(valeAlimentacao);

        // Soma tudo que é descontado
        this.totalDescontos = descontoINSS
            .add(descontoIRRF)
            .add(descontoValeTransporte);

        // Calcula o salário que vai para a conta do funcionário
        this.salarioLiquido = totalProventos.subtract(totalDescontos);
    }

    // Cria um texto com o resumo da folha
    public String gerarResumoFolha() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("=== FOLHA DE PAGAMENTO ===\n");
        resumo.append("Funcionário: ").append(funcionario.getName()).append("\n");
        resumo.append("Período: ").append(mesReferencia).append("/").append(anoReferencia).append("\n");
        resumo.append("\n--- PROVENTOS ---\n");
        resumo.append("Salário Base: R$ ").append(salarioBase).append("\n");
        if (adicionalPericulosidade.compareTo(BigDecimal.ZERO) > 0) {
            resumo.append("Ad. Periculosidade: R$ ").append(adicionalPericulosidade).append("\n");
        }
        if (adicionalInsalubridade.compareTo(BigDecimal.ZERO) > 0) {
            resumo.append("Ad. Insalubridade: R$ ").append(adicionalInsalubridade).append("\n");
        }
        if (valeAlimentacao.compareTo(BigDecimal.ZERO) > 0) {
            resumo.append("Vale Alimentação: R$ ").append(valeAlimentacao).append("\n");
        }
        resumo.append("TOTAL PROVENTOS: R$ ").append(totalProventos).append("\n");

        resumo.append("\n--- DESCONTOS ---\n");
        resumo.append("INSS: R$ ").append(descontoINSS).append("\n");
        resumo.append("IRRF: R$ ").append(descontoIRRF).append("\n");
        if (descontoValeTransporte.compareTo(BigDecimal.ZERO) > 0) {
            resumo.append("Vale Transporte: R$ ").append(descontoValeTransporte).append("\n");
        }
        resumo.append("TOTAL DESCONTOS: R$ ").append(totalDescontos).append("\n");

        resumo.append("\n--- LÍQUIDO ---\n");
        resumo.append("SALÁRIO LÍQUIDO: R$ ").append(salarioLiquido).append("\n");
        resumo.append("Status: ").append(status).append("\n");

        return resumo.toString();
    }

    // Status possíveis da folha
    public enum StatusFolha {
        PROCESSANDO,
        PROCESSADA,
        APROVADA,
        PAGA,
        CANCELADA
    }
}