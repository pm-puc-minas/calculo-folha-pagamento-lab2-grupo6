package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.PayrollResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollResultRepository extends JpaRepository<PayrollResult,Long> {
    List<PayrollResult> findByEmployeeId(Long employeeId);
    List<PayrollResult> findByEmployeeIdAndMonthAndYear(Long clienteId, int month, int year);
}
