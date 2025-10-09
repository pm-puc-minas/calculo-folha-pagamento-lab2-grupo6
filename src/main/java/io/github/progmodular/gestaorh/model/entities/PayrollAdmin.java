package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payroll_users")
public class PayrollAdmin extends User
{
    @Getter
    @Setter
    @NotEmpty
    private Boolean isAdmin = true;

    @Column(name = "payroll_level")
    private String payrollLevel;

    @Override
    public String getUserType() {
        return "PAYROLL";
    }
}
