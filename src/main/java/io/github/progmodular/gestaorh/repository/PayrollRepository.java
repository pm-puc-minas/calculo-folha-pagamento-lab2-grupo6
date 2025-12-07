package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByEmployeeId(Long employeeId);

    List<Payroll> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);


    @Modifying
    int deleteByIdAndMonthAndYearAndEmployeeId(Long id, int month, int year, Long employeeId);

    Optional<Payroll> findTopByEmployeeIdOrderByCalculationDateDesc(Long employeeId);


    void deleteAllByEmployeeId(Long employeeId);
}