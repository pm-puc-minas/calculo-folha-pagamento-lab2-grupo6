package io.github.progmodular.gestaorh.controller;
import io.github.progmodular.gestaorh.controller.dto.PayrollRequest;
import io.github.progmodular.gestaorh.model.entities.PayrollResult;
import io.github.progmodular.gestaorh.service.PayrollOrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    @Autowired
    private PayrollOrchestrationService payrollService;


    @GetMapping("/employee/{employeeId}/history")
    public ResponseEntity<List<PayrollResult>> getPayrollHistory(
            @PathVariable Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {

        try {
            List<PayrollResult> history;

            if (year != null && month != null) {
                history = payrollService.getPayrollByMonthAndYear(employeeId, month, year);
            } else {
                history = payrollService.getPayrollHistory(employeeId);
            }

            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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


    @PutMapping("/recalculate/{payrollId}/{month}/{year}")
    public ResponseEntity<PayrollResult> recalculatePayroll(@PathVariable("payrollId") Long payrollId,
                                                            @PathVariable("month") int month,
                                                            @PathVariable("year") int year) {
        try {
            PayrollResult result = payrollService.recalculatePayroll(payrollId, month, year);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


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


    @DeleteMapping("{id}/{month}/{year}/employee/{employeeId}")
    public ResponseEntity<Void> deletePayroll(@PathVariable("id") Long id,
                                              @PathVariable("month") int month,
                                              @PathVariable("year") int year,
                                              @PathVariable("employeeId") Long employeeId) {
        try {
            payrollService.deletePayrollByIdMonthYearEmployeeId(id, month, year, employeeId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
