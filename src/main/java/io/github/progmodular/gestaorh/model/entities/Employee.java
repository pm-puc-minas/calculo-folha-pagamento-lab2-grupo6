package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("employee_user")
@Getter
@Setter
public class Employee extends User {

    private Double grossSalary;

    private String cpf;

    private String position;

    private Double hoursWorked;

    private int daysWorked;

    @Override
    public String getUserType() {
        return "EMPLOYEE";
    }
}
