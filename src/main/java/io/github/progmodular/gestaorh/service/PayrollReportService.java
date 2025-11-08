package io.github.progmodular.gestaorh.service;


import io.github.progmodular.gestaorh.model.entities.PayrollReport;
import io.github.progmodular.gestaorh.model.entities.PayrollResult;
import io.github.progmodular.gestaorh.repository.PayrollReportRepository;
import io.github.progmodular.gestaorh.repository.PayrollResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PayrollReportService {

    private final PayrollReportRepository payrollReportRepository;
    private final PayrollResultRepository payrollResultRepository;

    public PayrollReportDTO generateReport (Long employeeId) {
        PayrollResult latestResult = payrollResultRepository
                .findTopByEmployeeIdOrderByCalulationDateDesc(employeeId)
                .orElseThrow(() -> new RuntimeException("No payroll found for employee id " + employeeId));

        PayrollReport report = new PayrollReport();
        report.setPayrollResult(latestResult);
        report.setGenerationDate(LocalDateTime.now());

        PayrollReport saved = payrollReportRepository.save(report);

        return PayrollReportDTO.from(latestResult);
    }

    public PayrollReportDTO getLatestReport (Long employeeId) {
        PayrollResult result = payrollResultRepository
                .findTopByEmployeeIdOrderByCalulationDateDesc(employeeId)
                .orElseThrow(() -> new RuntimeException("No payroll found for employee id " + employeeId));

        return PayrollReportDTO.from(result);
    }
}
