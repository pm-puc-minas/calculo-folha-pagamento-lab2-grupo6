package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.Payroll;
import io.github.progmodular.gestaorh.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll,Long> {
    List<Payroll> findByEmployeeId(Long employeeId);
    List<Payroll> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);
    @Modifying
    @Query("DELETE FROM Payroll s WHERE s.id = :id AND s.month = :month AND s.year = :year AND s.employee.id = :employeeId")
    int deleteByIdAndMonthAndYearAndEmployeeId(@Param("id") Long id,
                                               @Param("month") int month,
                                               @Param("year") int year,
                                               @Param("employeeId") Long employeeId);

    Optional<Payroll> findTopByEmployeeIdOrderByCalculationDateDesc(Long employeeId);

}
