package io.github.progmodular.gestaorh.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.service.calculatorservice.DegreeUnhealthiness;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTOResponse(Long id,
                              String name,
                              String email,
                              String password,
                              UserType userType,
                              BigDecimal grossSalary,
                              String cpf,
                              String position,
                              Integer dependents,
                              Integer hoursWorkedMonth,
                              Integer daysWorked,
                              BigDecimal actualVTCost,
                              DegreeUnhealthiness degreeUnhealthiness,
                              Boolean isAdmin
                      )
{


    public UserDTOResponse(Long id, String name, String email, UserType userType, Boolean isAdmin) {
        this(id,name,email,null,userType,null,null,null,null,null,null,null,null,isAdmin);
    }

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
                           Boolean isAdmin) {
        this(id,name,email,null,userType,grossSalary,cpf,position,dependents,hoursWorkedMonth,daysWorked,actualVTCost,degreeUnhealthiness,isAdmin);
    }
}


