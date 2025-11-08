package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.PayrollResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PayrollResultRepository extends JpaRepository<PayrollResult,Long> {
    List<PayrollResult> findByEmployeeId(Long employeeId);
    List<PayrollResult> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);
    @Modifying
    @Query("DELETE FROM PayrollResult p WHERE p.id = :id AND p.month = :month AND p.year = :year AND p.employee.id = :employeeId")
    int deleteByIdAndMonthAndYearAndEmployeeId(@Param("id") Long id,
                                               @Param("month") int month,
                                               @Param("year") int year,
                                               @Param("employeeId") Long employeeId);

    Optional<PayrollResult> findTopByEmployeeIdOrderByCalculationDateDesc(Long employeeId);

}
