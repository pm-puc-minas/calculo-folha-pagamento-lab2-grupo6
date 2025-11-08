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
    public ReponseEntity<PayrollReportDTO> generateReport(@PathVariable Long employeeId) {
        PayrollReportDTO report = payrollReportService.generateReport(employeeId);
        return ResponseEntity.ok(report);
    }


}
