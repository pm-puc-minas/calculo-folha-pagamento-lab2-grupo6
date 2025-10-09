package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.model.entities.CalculadoraFolha;
import io.github.progmodular.gestaorh.model.entities.FolhaPagamento;
import io.github.progmodular.gestaorh.model.entities.Funcionario;
import io.github.progmodular.gestaorh.repository.FolhaPagamentoRepository;
import io.github.progmodular.gestaorh.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Serviço que gerencia as operações da folha de pagamento
@Service
public class FolhaPagamentoService {

    @Autowired
    private FolhaPagamentoRepository folhaPagamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Processa folha de um funcionário específico
    @Transactional
    public FolhaPagamento processarFolhaFuncionario(Long funcionarioId, Integer mesReferencia, 
                                                   Integer anoReferencia, BigDecimal salarioBase) {

        // Busca o funcionário no banco
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(funcionarioId);
        if (funcionarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Funcionário não encontrado com ID: " + funcionarioId);
        }

        Funcionario funcionario = funcionarioOpt.get();

        // Verifica se já tem folha neste mês
        Optional<FolhaPagamento> folhaExistente = folhaPagamentoRepository
            .findByFuncionarioAndMesReferenciaAndAnoReferencia(funcionario, mesReferencia, anoReferencia);

        if (folhaExistente.isPresent()) {
            throw new IllegalStateException("Já existe folha processada para este funcionário no período " 
                + mesReferencia + "/" + anoReferencia);
        }

        // Cria uma nova folha
        FolhaPagamento folha = new FolhaPagamento();
        folha.setFuncionario(funcionario);
        folha.setMesReferencia(mesReferencia);
        folha.setAnoReferencia(anoReferencia);
        folha.setSalarioBase(salarioBase);
        folha.setDependentes(0);

        // Usa a calculadora para fazer os cálculos
        CalculadoraFolha calculadora = new CalculadoraFolha(salarioBase.doubleValue(), 220);
        folha.processarFolha(calculadora);

        // Salva no banco de dados
        return folhaPagamentoRepository.save(folha);
    }

    /**
     * Processa a folha de pagamento para todos os funcionários ativos
     * 
     * @param mesReferencia Mês de referência
     * @param anoReferencia Ano de referência
     * @return Lista das folhas processadas
     */
    @Transactional
    public List<FolhaPagamento> processarFolhaTodosFuncionarios(Integer mesReferencia, Integer anoReferencia) {

        // Buscar funcionários que ainda não têm folha no período
        List<Funcionario> funcionariosSemFolha = folhaPagamentoRepository
            .findFuncionariosSemFolhaNoPeriodo(mesReferencia, anoReferencia);

        return funcionariosSemFolha.stream()
            .map(funcionario -> {
                // Para demo, usando salário base padrão de R$ 2000,00
                // Em produção, isso viria do cadastro do funcionário
                BigDecimal salarioBase = new BigDecimal("2000.00");

                FolhaPagamento folha = new FolhaPagamento();
                folha.setFuncionario(funcionario);
                folha.setMesReferencia(mesReferencia);
                folha.setAnoReferencia(anoReferencia);
                folha.setSalarioBase(salarioBase);

                CalculadoraFolha calculadora = new CalculadoraFolha(salarioBase.doubleValue(), 220);
                folha.processarFolha(calculadora);

                return folhaPagamentoRepository.save(folha);
            })
            .toList();
    }

    /**
     * Busca a folha de pagamento de um funcionário em um período específico
     * 
     * @param funcionarioId ID do funcionário
     * @param mesReferencia Mês de referência
     * @param anoReferencia Ano de referência
     * @return Optional da folha encontrada
     */
    public Optional<FolhaPagamento> buscarFolhaPorFuncionarioEPeriodo(Long funcionarioId, 
                                                                     Integer mesReferencia, Integer anoReferencia) {

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(funcionarioId);
        if (funcionarioOpt.isEmpty()) {
            return Optional.empty();
        }

        return folhaPagamentoRepository.findByFuncionarioAndMesReferenciaAndAnoReferencia(
            funcionarioOpt.get(), mesReferencia, anoReferencia);
    }

    /**
     * Busca o histórico de folhas de um funcionário
     * 
     * @param funcionarioId ID do funcionário
     * @return Lista ordenada das folhas do funcionário
     */
    public List<FolhaPagamento> buscarHistoricoFolhasFuncionario(Long funcionarioId) {

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(funcionarioId);
        if (funcionarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Funcionário não encontrado com ID: " + funcionarioId);
        }

        return folhaPagamentoRepository.findByFuncionarioOrderByAnoReferenciaDescMesReferenciaDesc(
            funcionarioOpt.get());
    }

    /**
     * Busca todas as folhas de um período específico
     * 
     * @param mesReferencia Mês de referência
     * @param anoReferencia Ano de referência
     * @return Lista das folhas do período
     */
    public List<FolhaPagamento> buscarFolhasPorPeriodo(Integer mesReferencia, Integer anoReferencia) {
        return folhaPagamentoRepository.findByMesReferenciaAndAnoReferencia(mesReferencia, anoReferencia);
    }

    /**
     * Calcula o total de folha de pagamento de um período
     * 
     * @param mesReferencia Mês de referência
     * @param anoReferencia Ano de referência
     * @return Total dos salários líquidos do período
     */
    public Double calcularTotalFolhaPeriodo(Integer mesReferencia, Integer anoReferencia) {
        Double total = folhaPagamentoRepository.calcularTotalSalariosLiquidosPorPeriodo(mesReferencia, anoReferencia);
        return total != null ? total : 0.0;
    }

    /**
     * Atualiza o status de uma folha de pagamento
     * 
     * @param folhaId ID da folha
     * @param novoStatus Novo status
     * @return Folha atualizada
     */
    @Transactional
    public FolhaPagamento atualizarStatusFolha(Long folhaId, FolhaPagamento.StatusFolha novoStatus) {

        Optional<FolhaPagamento> folhaOpt = folhaPagamentoRepository.findById(folhaId);
        if (folhaOpt.isEmpty()) {
            throw new IllegalArgumentException("Folha não encontrada com ID: " + folhaId);
        }

        FolhaPagamento folha = folhaOpt.get();
        folha.setStatus(novoStatus);

        return folhaPagamentoRepository.save(folha);
    }

    /**
     * Gera resumo consolidado da folha do período
     * 
     * @param mesReferencia Mês de referência
     * @param anoReferencia Ano de referência
     * @return String com resumo consolidado
     */
    public String gerarResumoConsolidadoPeriodo(Integer mesReferencia, Integer anoReferencia) {

        List<FolhaPagamento> folhas = buscarFolhasPorPeriodo(mesReferencia, anoReferencia);
        Double totalPeriodo = calcularTotalFolhaPeriodo(mesReferencia, anoReferencia);

        StringBuilder resumo = new StringBuilder();
        resumo.append("=== RESUMO CONSOLIDADO DA FOLHA ===\n");
        resumo.append("Período: ").append(mesReferencia).append("/").append(anoReferencia).append("\n");
        resumo.append("Funcionários processados: ").append(folhas.size()).append("\n");
        resumo.append("Total da folha: R$ ").append(String.format("%.2f", totalPeriodo)).append("\n");
        resumo.append("Data: ").append(LocalDate.now()).append("\n");

        return resumo.toString();
    }

    /**
     * Verifica se é possível processar folha para o período
     * 
     * @param mesReferencia Mês de referência
     * @param anoReferencia Ano de referência
     * @return true se é possível processar
     */
    public boolean podeProcessarFolhaPeriodo(Integer mesReferencia, Integer anoReferencia) {

        // Não permitir processamento de períodos futuros
        LocalDate hoje = LocalDate.now();
        LocalDate periodoReferencia = LocalDate.of(anoReferencia, mesReferencia, 1);

        return !periodoReferencia.isAfter(hoje.withDayOfMonth(1));
    }
}
