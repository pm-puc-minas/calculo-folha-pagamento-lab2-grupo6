package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payroll_reports")
@Getter
@Setter
public class PayrollReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "result_id")
    private Payroll payrollResult;

    private LocalDateTime generationDate;
}
