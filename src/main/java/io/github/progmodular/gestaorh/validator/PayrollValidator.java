package io.github.progmodular.gestaorh.validator;

import io.github.progmodular.gestaorh.dto.ErrorField;
import io.github.progmodular.gestaorh.dto.PayrollDTO;
import io.github.progmodular.gestaorh.exceptions.handle.*;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PayrollValidator {

    @Autowired
    private PayrollRepository payrollRepository;

    public void isEmployee(User user)
    {
        if(user instanceof PayrollAdmin)
        {
            throw new PayrollEmployeesOnlyException("Payroll_admin user cannot have a payroll.");
        }
    }

    public void isEmployeeExistById(Long employeeId) {
        if(payrollRepository.findByEmployeeId(employeeId).isEmpty())
        {
            throw new UserNotExistException("Employee provided not exist in the system or not have any payroll active");
        }
    }

    public void isPayrollExistByEmployeeIdMonthYear(Long employeeId, int month, int year) {
        if(payrollRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year).isEmpty())
        {
            throw new UserNotExistException("Payroll with this parameters provided not exist in the system");
        }
    }

    public void deleteValidator(int deletedCount)
    {
        if (deletedCount == 0) {
            throw new DeleteErroException("ERROR DELETING: Record not found");
        }
    }

    public void validateOnCreation(PayrollDTO request) {
        if(!(payrollRepository.findByEmployeeIdAndMonthAndYear(request.employeeId(),request.month(),request.year()).isEmpty()))
        {
            throw new DuplicaValueException("There is already a payroll for this employee this month and year.");
        }
    }

    public void nullValidation(PayrollDTO payrollDTO) {
        List<ErrorField> errors = new ArrayList<>();

        if (payrollDTO.employeeId() == null) {
            errors.add(new ErrorField("employeeId", "employeeId is a mandatory field."));
        }
        if (payrollDTO.month() == null) {
            errors.add(new ErrorField("month", "month is a mandatory field."));
        }
        if (payrollDTO.year() == null) {
            errors.add(new ErrorField("year", "year is a mandatory field."));
        }
        if (!errors.isEmpty()) {
            throw new ValidationListException(errors);
        }
    }
}
