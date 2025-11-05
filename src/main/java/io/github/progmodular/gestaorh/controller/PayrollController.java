package io.github.progmodular.gestaorh.controller;
import io.github.progmodular.gestaorh.controller.dto.PayrollRequest;
import io.github.progmodular.gestaorh.model.entities.PayrollResult;
import io.github.progmodular.gestaorh.service.PayrollOrchestrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    private final PayrollOrchestrationService payrollService;

    public PayrollController(PayrollOrchestrationService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<PayrollResult> calculatePayroll(@RequestBody PayrollRequest request) {
        try {
            PayrollResult result = payrollService.calculateCompletePayroll(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

//    @GetMapping("/employee/{employeeId}/history")
//    public ResponseEntity<List<PayrollResult>> getPayrollHistory(
//            @PathVariable Long employeeId,
//            @RequestParam(required = false) Integer year,
//            @RequestParam(required = false) Integer month) {
//
//        try {
//            List<PayrollResult> history;
//
//            if (year != null && month != null) {
//                history = payrollService.getPayrollByMonthAndYear(employeeId, month, year);
//            } else {
//                history = payrollService.getPayrollHistory(employeeId);
//            }
//
//            return ResponseEntity.ok(history);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PutMapping("/recalculate/{payrollId}")
//    public ResponseEntity<PayrollResult> recalculatePayroll(@PathVariable Long payrollId) {
//        try {
//            PayrollResult result = payrollService.recalculatePayroll(payrollId);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }


//    @PostMapping("/calculate-specific")
//    public ResponseEntity<BigDecimal> calculateSpecific(
//            @RequestParam String calculationType,
//            @RequestBody PayrollRequest request) {
//
//        try {
//            BigDecimal result = payrollService.calculateSpecific(calculationType, request);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }


//    @DeleteMapping("/{payrollId}")
//    public ResponseEntity<Void> deletePayroll(@PathVariable Long payrollId) {
//        try {
//            payrollService.deletePayroll(payrollId);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
    }
