package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("employee_user")
public class Employee extends User {

    @Column(name = "grossSalary")
    private Double grossSalary;

    @Override
    public String getUserType() {
        return "EMPLOYEE";
    }
}
