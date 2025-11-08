package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.PayrollReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollReportRepository extends JpaRepository<PayrollReport,Integer> {
    PayrollReport save(PayrollReport report);
}
