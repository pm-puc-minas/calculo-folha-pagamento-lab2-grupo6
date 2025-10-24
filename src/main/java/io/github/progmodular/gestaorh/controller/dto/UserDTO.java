package io.github.progmodular.gestaorh.controller.dto;

import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;

import java.math.BigDecimal;

public record UserDTO(String name,
                      String email,
                      String password,
                      UserType userType,
                      BigDecimal grossSalary,
                      String cpf,
                      String position,
                      Integer hoursWorked,
                      Integer daysWorked,
                      Boolean isAdmin
                      )
{
    public UserType getUserType()
    {
        return this.userType;
    }
    public Employee setEmployee()
    {
        Employee employee = new Employee();
        employee.setUserType(this.userType);
        employee.setName(this.name);
        employee.setEmail(this.email);
        employee.setPassword(this.password);
        employee.setIsAdmin(this.isAdmin);

        employee.setGrossSalary(this.grossSalary);
        employee.setCpf(this.cpf);
        employee.setPosition(this.position);
        employee.setHoursWorked(this.hoursWorked);
        employee.setDaysWorked(this.daysWorked);

        return employee;
    }

    public PayrollAdmin setPayroll()
    {
        PayrollAdmin payrollAdmin = new PayrollAdmin();
        payrollAdmin.setUserType(this.userType);
        payrollAdmin.setName(this.name);
        payrollAdmin.setEmail(this.email);
        payrollAdmin.setPassword(this.password);

        payrollAdmin.setIsAdmin(this.isAdmin);

        return payrollAdmin;
    }
}


