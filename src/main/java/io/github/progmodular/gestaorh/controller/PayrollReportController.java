package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.controller.dto.PayrollReportDTO;
import io.github.progmodular.gestaorh.service.PayrollReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PayrollReportController {

    private final PayrollReportService payrollReportService;

    @PostMapping("/report/{employeeId}")
    public ResponseEntity<PayrollReportDTO> generateReport(@PathVariable Long employeeId) {
        PayrollReportDTO report = payrollReportService.generateReport(employeeId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/report/{employeeId}")
    public ResponseEntity<PayrollReportDTO> getLatestReport(@PathVariable Long employeeId) {
        PayrollReportDTO report = payrollReportService.getLatestReport(employeeId);
        return ResponseEntity.ok(report);
    }



}
