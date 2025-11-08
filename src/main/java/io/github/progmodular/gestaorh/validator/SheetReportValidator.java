package io.github.progmodular.gestaorh.validator;

import io.github.progmodular.gestaorh.dto.ErrorField;
import io.github.progmodular.gestaorh.dto.SheetRequest;
import io.github.progmodular.gestaorh.exceptions.handle.*;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.SheetReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SheetReportValidator {

    @Autowired
    private SheetReportRepository sheetReportRepository;

    public void isEmployee(User user)
    {
        if(user instanceof PayrollAdmin)
        {
            throw new PayrollEmployeesOnlyException("Payroll_admin user cannot have a payroll.");
        }
    }

    public void isEmployeeExistById(Long employeeId) {
        if(sheetReportRepository.findById(employeeId).isEmpty())
        {
            throw new UserNotExistException("Employee provided not exist in the system");
        }
    }

    public void isPayrollExistByEmployeeIdMonthYear(Long employeeId, int month, int year) {
        if(sheetReportRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year).isEmpty())
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

    public void validateOnCreation(SheetRequest request) {
        if(!(sheetReportRepository.findByEmployeeIdAndMonthAndYear(request.employeeId(),request.month(),request.year()).isEmpty()))
        {
            throw new DuplicaValueException("There is already a payroll for this employee this month and year.");
        }
    }

    public void nullValidation(SheetRequest sheetRequest) {
        List<ErrorField> errors = new ArrayList<>();

        if (sheetRequest.employeeId() == null) {
            errors.add(new ErrorField("employeeId", "employeeId is a mandatory field."));
        }
        if (sheetRequest.month() == null) {
            errors.add(new ErrorField("month", "month is a mandatory field."));
        }
        if (sheetRequest.year() == null) {
            errors.add(new ErrorField("year", "year is a mandatory field."));
        }
        if (!errors.isEmpty()) {
            throw new ValidationListException(errors);
        }
    }
}
