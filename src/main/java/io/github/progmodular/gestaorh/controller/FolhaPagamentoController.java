package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.model.entities.FolhaPagamento;
import io.github.progmodular.gestaorh.service.FolhaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Controller que expõe as APIs da folha de pagamento
@RestController
@RequestMapping("/api/folha-pagamento")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem
public class FolhaPagamentoController {

    @Autowired
    private FolhaPagamentoService folhaPagamentoService;

    // API para processar folha de um funcionário
    @PostMapping("/processar-funcionario")
    public ResponseEntity<?> processarFolhaFuncionario(@RequestBody ProcessarFolhaRequest request) {
        
        try {
            FolhaPagamento folha = folhaPagamentoService.processarFolhaFuncionario(
                request.getFuncionarioId(),
                request.getMesReferencia(),
                request.getAnoReferencia(),
                request.getSalarioBase()
            );

            return ResponseEntity.ok(new FolhaPagamentoResponse(folha));
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro interno ao processar folha: " + e.getMessage()));
        }
    }

    /**
     * Processa a folha para todos os funcionários de um período
     * 
     * POST /api/folha-pagamento/processar-periodo
     */
    @PostMapping("/processar-periodo")
    public ResponseEntity<?> processarFolhaPeriodo(@RequestBody ProcessarPeriodoRequest request) {
        
        try {
            // Verificar se pode processar o período
            if (!folhaPagamentoService.podeProcessarFolhaPeriodo(request.getMesReferencia(), request.getAnoReferencia())) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Não é possível processar folha para períodos futuros"));
            }

            List<FolhaPagamento> folhas = folhaPagamentoService.processarFolhaTodosFuncionarios(
                request.getMesReferencia(),
                request.getAnoReferencia()
            );

            List<FolhaPagamentoResponse> responses = folhas.stream()
                .map(FolhaPagamentoResponse::new)
                .toList();

            return ResponseEntity.ok(responses);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao processar folhas do período: " + e.getMessage()));
        }
    }

    /**
     * Busca folha de um funcionário por período
     * 
     * GET /api/folha-pagamento/funcionario/{funcionarioId}/periodo
     */
    @GetMapping("/funcionario/{funcionarioId}/periodo")
    public ResponseEntity<?> buscarFolhaPorPeriodo(
            @PathVariable Long funcionarioId,
            @RequestParam Integer mes,
            @RequestParam Integer ano) {
        
        try {
            Optional<FolhaPagamento> folhaOpt = folhaPagamentoService
                .buscarFolhaPorFuncionarioEPeriodo(funcionarioId, mes, ano);

            if (folhaOpt.isPresent()) {
                return ResponseEntity.ok(new FolhaPagamentoResponse(folhaOpt.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao buscar folha: " + e.getMessage()));
        }
    }

    /**
     * Busca histórico de folhas de um funcionário
     * 
     * GET /api/folha-pagamento/funcionario/{funcionarioId}/historico
     */
    @GetMapping("/funcionario/{funcionarioId}/historico")
    public ResponseEntity<?> buscarHistoricoFuncionario(@PathVariable Long funcionarioId) {
        
        try {
            List<FolhaPagamento> historico = folhaPagamentoService.buscarHistoricoFolhasFuncionario(funcionarioId);
            
            List<FolhaPagamentoResponse> responses = historico.stream()
                .map(FolhaPagamentoResponse::new)
                .toList();

            return ResponseEntity.ok(responses);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao buscar histórico: " + e.getMessage()));
        }
    }

    /**
     * Busca folhas de um período específico
     * 
     * GET /api/folha-pagamento/periodo
     */
    @GetMapping("/periodo")
    public ResponseEntity<?> buscarFolhasPeriodo(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {
        
        try {
            List<FolhaPagamento> folhas = folhaPagamentoService.buscarFolhasPorPeriodo(mes, ano);
            
            List<FolhaPagamentoResponse> responses = folhas.stream()
                .map(FolhaPagamentoResponse::new)
                .toList();

            return ResponseEntity.ok(responses);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao buscar folhas do período: " + e.getMessage()));
        }
    }

    /**
     * Gera resumo consolidado de um período
     * 
     * GET /api/folha-pagamento/resumo-periodo
     */
    @GetMapping("/resumo-periodo")
    public ResponseEntity<?> gerarResumoConsolidado(
            @RequestParam Integer mes,
            @RequestParam Integer ano) {
        
        try {
            String resumo = folhaPagamentoService.gerarResumoConsolidadoPeriodo(mes, ano);
            Double total = folhaPagamentoService.calcularTotalFolhaPeriodo(mes, ano);
            
            ResumoConsolidadoResponse response = new ResumoConsolidadoResponse();
            response.setResumoTexto(resumo);
            response.setTotalPeriodo(total);
            response.setMesReferencia(mes);
            response.setAnoReferencia(ano);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao gerar resumo: " + e.getMessage()));
        }
    }

    /**
     * Atualiza status de uma folha
     * 
     * PUT /api/folha-pagamento/{folhaId}/status
     */
    @PutMapping("/{folhaId}/status")
    public ResponseEntity<?> atualizarStatusFolha(
            @PathVariable Long folhaId,
            @RequestBody AtualizarStatusRequest request) {
        
        try {
            FolhaPagamento folhaAtualizada = folhaPagamentoService.atualizarStatusFolha(
                folhaId, request.getNovoStatus());
            
            return ResponseEntity.ok(new FolhaPagamentoResponse(folhaAtualizada));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro ao atualizar status: " + e.getMessage()));
        }
    }

    // ===== DTOs =====

    public static class ProcessarFolhaRequest {
        private Long funcionarioId;
        private Integer mesReferencia;
        private Integer anoReferencia;
        private BigDecimal salarioBase;

        // Getters e Setters
        public Long getFuncionarioId() { return funcionarioId; }
        public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }
        public Integer getMesReferencia() { return mesReferencia; }
        public void setMesReferencia(Integer mesReferencia) { this.mesReferencia = mesReferencia; }
        public Integer getAnoReferencia() { return anoReferencia; }
        public void setAnoReferencia(Integer anoReferencia) { this.anoReferencia = anoReferencia; }
        public BigDecimal getSalarioBase() { return salarioBase; }
        public void setSalarioBase(BigDecimal salarioBase) { this.salarioBase = salarioBase; }
    }

    public static class ProcessarPeriodoRequest {
        private Integer mesReferencia;
        private Integer anoReferencia;

        // Getters e Setters
        public Integer getMesReferencia() { return mesReferencia; }
        public void setMesReferencia(Integer mesReferencia) { this.mesReferencia = mesReferencia; }
        public Integer getAnoReferencia() { return anoReferencia; }
        public void setAnoReferencia(Integer anoReferencia) { this.anoReferencia = anoReferencia; }
    }

    public static class AtualizarStatusRequest {
        private FolhaPagamento.StatusFolha novoStatus;

        // Getters e Setters
        public FolhaPagamento.StatusFolha getNovoStatus() { return novoStatus; }
        public void setNovoStatus(FolhaPagamento.StatusFolha novoStatus) { this.novoStatus = novoStatus; }
    }

    public static class FolhaPagamentoResponse {
        private Long id;
        private String funcionarioNome;
        private Integer mesReferencia;
        private Integer anoReferencia;
        private BigDecimal salarioBase;
        private BigDecimal salarioLiquido;
        private BigDecimal totalProventos;
        private BigDecimal totalDescontos;
        private FolhaPagamento.StatusFolha status;
        private LocalDate dataProcessamento;

        public FolhaPagamentoResponse(FolhaPagamento folha) {
            this.id = folha.getId();
            this.funcionarioNome = folha.getFuncionario().getName();
            this.mesReferencia = folha.getMesReferencia();
            this.anoReferencia = folha.getAnoReferencia();
            this.salarioBase = folha.getSalarioBase();
            this.salarioLiquido = folha.getSalarioLiquido();
            this.totalProventos = folha.getTotalProventos();
            this.totalDescontos = folha.getTotalDescontos();
            this.status = folha.getStatus();
            this.dataProcessamento = folha.getDataProcessamento();
        }

        // Getters
        public Long getId() { return id; }
        public String getFuncionarioNome() { return funcionarioNome; }
        public Integer getMesReferencia() { return mesReferencia; }
        public Integer getAnoReferencia() { return anoReferencia; }
        public BigDecimal getSalarioBase() { return salarioBase; }
        public BigDecimal getSalarioLiquido() { return salarioLiquido; }
        public BigDecimal getTotalProventos() { return totalProventos; }
        public BigDecimal getTotalDescontos() { return totalDescontos; }
        public FolhaPagamento.StatusFolha getStatus() { return status; }
        public LocalDate getDataProcessamento() { return dataProcessamento; }
    }

    public static class ResumoConsolidadoResponse {
        private String resumoTexto;
        private Double totalPeriodo;
        private Integer mesReferencia;
        private Integer anoReferencia;

        // Getters e Setters
        public String getResumoTexto() { return resumoTexto; }
        public void setResumoTexto(String resumoTexto) { this.resumoTexto = resumoTexto; }
        public Double getTotalPeriodo() { return totalPeriodo; }
        public void setTotalPeriodo(Double totalPeriodo) { this.totalPeriodo = totalPeriodo; }
        public Integer getMesReferencia() { return mesReferencia; }
        public void setMesReferencia(Integer mesReferencia) { this.mesReferencia = mesReferencia; }
        public Integer getAnoReferencia() { return anoReferencia; }
        public void setAnoReferencia(Integer anoReferencia) { this.anoReferencia = anoReferencia; }
    }

    public static class ErrorResponse {
        private String erro;
        private LocalDate timestamp;

        public ErrorResponse(String erro) {
            this.erro = erro;
            this.timestamp = LocalDate.now();
        }

        // Getters
        public String getErro() { return erro; }
        public LocalDate getTimestamp() { return timestamp; }
    }
}