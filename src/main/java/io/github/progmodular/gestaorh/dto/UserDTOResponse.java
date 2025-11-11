package io.github.progmodular.gestaorh.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.Enum.DegreeUnhealthiness;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTOResponse(Long id,
                              String name,
                              String email,
                              UserType userType,
                              BigDecimal grossSalary,
                              String cpf,
                              String position,
                              Integer dependents,
                              Integer hoursWorkedMonth,
                              Integer daysWorked,
                              BigDecimal actualVTCost,
                              DegreeUnhealthiness degreeUnhealthiness,
                              Boolean hasDanger,
                              Boolean isAdmin
                      )
{


    //DTO para payrollAdmin
    public UserDTOResponse(Long id,
                           String name,
                           String email,
                           UserType userType,
                           Boolean isAdmin) {
        this(id,name,email,userType,null,null,null,null,null,null,null,null,null,isAdmin);
    }

    //DTO para UserController
    public UserDTOResponse(Long id,
                           UserType userType,
                           String name,
                           String email,
                           BigDecimal grossSalary,
                           String cpf,
                           String position,
                           Integer dependents,
                           Integer hoursWorkedMonth,
                           Integer daysWorked,
                           BigDecimal actualVTCost,
                           DegreeUnhealthiness degreeUnhealthiness,
                           Boolean hasDanger,
                           Boolean isAdmin) {
        this(id,name,email,userType,grossSalary,cpf,position,dependents,hoursWorkedMonth,daysWorked,actualVTCost,degreeUnhealthiness,hasDanger,isAdmin);
    }

    //DTO para PayrollController com parametros especificos removidos
    public UserDTOResponse(Long id,
                           UserType userType,
                           String name,
                           String position,
                           Integer dependents,
                           Integer hoursWorkedMonth,
                           Integer daysWorked,
                           BigDecimal actualVTCost,
                           DegreeUnhealthiness degreeUnhealthiness,
                           Boolean hasDanger,
                           Boolean isAdmin) {
        this(id,name,null,userType,null,null,position,dependents,hoursWorkedMonth,daysWorked,actualVTCost,degreeUnhealthiness,hasDanger,isAdmin);
    }
}


