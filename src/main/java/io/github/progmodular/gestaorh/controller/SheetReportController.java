package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.dto.SheetRequest;
import io.github.progmodular.gestaorh.dto.SheetResponse;
import io.github.progmodular.gestaorh.model.entities.SheetReport;
import io.github.progmodular.gestaorh.service.SheetReportCalculatorOrchestrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
@Tag(name = "Cálculo e Relatórios de Folha de Pagamento", description = "Endpoints para calcular, recalcular, buscar e excluir registros de holerites (SheetReport).")
public class SheetReportController {

    @Autowired
    private SheetReportCalculatorOrchestrationService payrollService;


    @GetMapping("/employee/{employeeId}/history")
    @Operation(summary = "Busca Histórico de folha de pagamento",
            description = "Retorna o histórico completo de folha de pagamento de um funcionário. Permite filtrar opcionalmente por ano e mês.",
            parameters = {
                    @Parameter(name = "employeeId", description = "ID do Funcionário."),
                    @Parameter(name = "year", description = "Ano para filtrar o histórico (Opcional).", required = false),
                    @Parameter(name = "month", description = "Mês para filtrar o histórico (Opcional).", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso. Retorna a lista de relatórios (pode ser vazia)."),
                    @ApiResponse(responseCode = "404", description = "Funcionário não encontrado ou erro de busca.")
            })
    public ResponseEntity<List<SheetResponse>> getPayrollHistory(
            @PathVariable Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
            List<SheetResponse> history;
            if (year != null && month != null) {
                history = payrollService.getPayrollByMonthAndYear(employeeId, month, year);
            }
            else {
                history = payrollService.getPayrollHistory(employeeId);
            }
            return ResponseEntity.ok(history);

    }

    @PostMapping("/calculate")
    @Operation(summary = "Calcula e Salva Novo folha de pagamento",
            description = "Dispara o cálculo completo do folha de pagamento para um funcionário/período, baseando-se nos dados fornecidos no corpo da requisição.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cálculo concluído. Retorna o SheetReport gerado."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida (falha no cálculo ou dados incompletos).")
            })
    public ResponseEntity<Object> calculatePayroll(@RequestBody SheetRequest request) {
            SheetReport result = payrollService.calculateCompletePayroll(request);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(result.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
    }


    @PutMapping("/recalculate/payroll/{payrollId}/month/{month}/year/{year}")
    @Operation(summary = "Recalcula uma folha de pagamento Existente",
            description = "Recalcula um folha de pagamento específico (identificado pelo ID, Mês e Ano) e atualiza o registro no banco de dados. Não requer corpo (RequestBody).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recálculo bem-sucedido. Retorna o SheetReport atualizado."),
                    @ApiResponse(responseCode = "404", description = "folha de pagamento não encontrado."),
                    @ApiResponse(responseCode = "400", description = "Erro de lógica ou validação no recálculo.")
            })
    public ResponseEntity<SheetReport> recalculatePayroll(@PathVariable("payrollId") Long payrollId,
                                                          @PathVariable("month") int month,
                                                          @PathVariable("year") int year) {
            SheetReport result = payrollService.recalculatePayroll(payrollId, month, year);
            return ResponseEntity.ok(result);
    }


    @DeleteMapping("{id}/month/{month}/year/{year}/employee/{employeeId}")
    @Operation(summary = "Deleta folha de pagamento",
            description = "Exclui o registro de folha de pagamento garantindo a integridade (ID da folha de pagamento + Mês + Ano + ID do Funcionário).",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Exclusão bem-sucedida (No Content)."),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado ou dados inconsistentes."),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
            })
    public ResponseEntity<Void> deletePayroll(@PathVariable("id") Long id,
                                              @PathVariable("month") int month,
                                              @PathVariable("year") int year,
                                              @PathVariable("employeeId") Long employeeId) {
            payrollService.deletePayrollByIdMonthYearEmployeeId(id, month, year, employeeId);
            return ResponseEntity.noContent().build();
    }
}