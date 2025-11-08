package io.github.progmodular.gestaorh.model.entities;

import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.infra.config.calculator.DegreeUnhealthiness;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User {

    public Employee() {
        super();
        this.setUserType(UserType.EMPLOYEE);
    }

    @Column(name="grossSalary")
    private BigDecimal grossSalary;

    @Column(name="cpf")
    private String cpf;

    @Column(name="position")
    private String position;

    @Column(name="dependents")
    private Integer dependents;

    @Column(name="hoursWorkedMonth")
    private Integer hoursWorkedMonth;

    @Column(name="daysWorked")
    private Integer daysWorked;

    @Column(name="actualVTCost")
    private BigDecimal actualVTCost;

    @Column(name="degreeUnhealthiness")
    private DegreeUnhealthiness degreeUnhealthiness;

}