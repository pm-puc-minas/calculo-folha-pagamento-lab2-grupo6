package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.dto.SheetResponse;
import io.github.progmodular.gestaorh.model.entities.SheetReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SheetReportRepository extends JpaRepository<SheetReport,Long> {
    List<SheetReport> findByEmployeeId(Long employeeId);
    List<SheetReport> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);
    @Modifying
    @Query("DELETE FROM SheetReport s WHERE s.id = :id AND s.month = :month AND s.year = :year AND s.employee.id = :employeeId")
    int deleteByIdAndMonthAndYearAndEmployeeId(@Param("id") Long id,
                                               @Param("month") int month,
                                               @Param("year") int year,
                                               @Param("employeeId") Long employeeId);

    Optional<PayrollResult> findTopByEmployeeIdOrderByCalculationDateDesc(Long employeeId);

}
