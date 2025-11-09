package io.github.progmodular.gestaorh.service;


import io.github.progmodular.gestaorh.dto.PayrollReportDTO;
import io.github.progmodular.gestaorh.model.entities.PayrollReport;
import io.github.progmodular.gestaorh.model.entities.Payroll;
import io.github.progmodular.gestaorh.repository.PayrollReportRepository;
import io.github.progmodular.gestaorh.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PayrollReportService {

    private final PayrollReportRepository payrollReportRepository;
    private final PayrollRepository payrollRepository;

    public PayrollReportDTO generateReport (Long employeeId) {
        Payroll latestResult = payrollRepository
                .findTopByEmployeeIdOrderByCalculationDateDesc(employeeId)
                .orElseThrow(() -> new RuntimeException("No payroll found for employee id " + employeeId));

        PayrollReport report = new PayrollReport();
        report.setPayrollResult(latestResult);
        report.setGenerationDate(LocalDateTime.now());

        PayrollReport saved = payrollReportRepository.save(report);

        return PayrollReportDTO.from(latestResult);
    }

    public PayrollReportDTO getLatestReport (Long employeeId) {
        Payroll result = payrollRepository
                .findTopByEmployeeIdOrderByCalculationDateDesc(employeeId)
                .orElseThrow(() -> new RuntimeException("No payroll found for employee id " + employeeId));

        return PayrollReportDTO.from(result);
    }

    public void deleteReport(Long employeeId, Integer month, Integer year) {
        Payroll result = (Payroll) payrollRepository
                .findByEmployeeIdAndMonthAndYear(employeeId, month, year);
        if (((java.util.List<?>) result).isEmpty()) {
            throw new RuntimeException("No payroll found for employee id " + employeeId + " in " + month + "/" + year);
        }

        payrollRepository.delete(result);
    }
}
