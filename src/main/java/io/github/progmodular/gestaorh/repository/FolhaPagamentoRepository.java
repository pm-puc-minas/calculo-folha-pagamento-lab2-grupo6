package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.FolhaPagamento;
import io.github.progmodular.gestaorh.model.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de dados da entidade FolhaPagamento.
 * Fornece métodos para consultas personalizadas relacionadas à folha de pagamento.
 */
@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {

    /**
     * Busca folhas de pagamento por funcionário
     * @param funcionario O funcionário
     * @return Lista de folhas de pagamento do funcionário
     */
    List<FolhaPagamento> findByFuncionario(Funcionario funcionario);

    /**
     * Busca folhas de pagamento por funcionário ordenadas por data decrescente
     * @param funcionario O funcionário
     * @return Lista de folhas ordenadas pela mais recente
     */
    List<FolhaPagamento> findByFuncionarioOrderByAnoReferenciaDescMesReferenciaDesc(Funcionario funcionario);

    /**
     * Busca folha de pagamento específica por funcionário, mês e ano
     * @param funcionario O funcionário
     * @param mesReferencia O mês de referência
     * @param anoReferencia O ano de referência
     * @return Optional da folha encontrada
     */
    Optional<FolhaPagamento> findByFuncionarioAndMesReferenciaAndAnoReferencia(
            Funcionario funcionario, Integer mesReferencia, Integer anoReferencia);

    /**
     * Busca todas as folhas de pagamento de um mês e ano específicos
     * @param mesReferencia O mês de referência
     * @param anoReferencia O ano de referência
     * @return Lista de folhas do período
     */
    List<FolhaPagamento> findByMesReferenciaAndAnoReferencia(Integer mesReferencia, Integer anoReferencia);

    /**
     * Busca folhas por status
     * @param status O status da folha
     * @return Lista de folhas com o status especificado
     */
    List<FolhaPagamento> findByStatus(FolhaPagamento.StatusFolha status);

    /**
     * Verifica se já existe folha para um funcionário em um período específico
     * @param funcionario O funcionário
     * @param mesReferencia O mês de referência
     * @param anoReferencia O ano de referência
     * @return true se já existe folha para o período
     */
    boolean existsByFuncionarioAndMesReferenciaAndAnoReferencia(
            Funcionario funcionario, Integer mesReferencia, Integer anoReferencia);

    /**
     * Calcula o total de salários líquidos pagos em um período
     * @param mesReferencia O mês de referência
     * @param anoReferencia O ano de referência
     * @return Soma total dos salários líquidos
     */
    @Query("SELECT SUM(fp.salarioLiquido) FROM FolhaPagamento fp WHERE fp.mesReferencia = :mes AND fp.anoReferencia = :ano")
    Double calcularTotalSalariosLiquidosPorPeriodo(@Param("mes") Integer mesReferencia, @Param("ano") Integer anoReferencia);

    /**
     * Conta o número de folhas processadas em um período
     * @param mesReferencia O mês de referência
     * @param anoReferencia O ano de referência
     * @return Número de folhas processadas
     */
    Long countByMesReferenciaAndAnoReferenciaAndStatus(
            Integer mesReferencia, Integer anoReferencia, FolhaPagamento.StatusFolha status);

    /**
     * Busca funcionários que ainda não têm folha processada para um período
     * @param mesReferencia O mês de referência
     * @param anoReferencia O ano de referência
     * @return Lista de funcionários sem folha no período
     */
    @Query("SELECT f FROM Funcionario f WHERE f NOT IN " +
           "(SELECT fp.funcionario FROM FolhaPagamento fp WHERE fp.mesReferencia = :mes AND fp.anoReferencia = :ano)")
    List<Funcionario> findFuncionariosSemFolhaNoPeriodo(@Param("mes") Integer mesReferencia, @Param("ano") Integer anoReferencia);
}