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
public class Employee extends User {

    @Getter
    @Setter
    private Double grossSalary;

    @Getter
    @Setter
    private String cpf;

    @Getter
    @Setter
    private String position;

    @Getter
    @Setter
    private Double hoursWorked;

    @Getter
    @Setter
    private int daysWorked;

    @Override
    public String getUserType() {
        return "EMPLOYEE";
    }
}
