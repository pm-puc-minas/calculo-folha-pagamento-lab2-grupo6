package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.dto.PayrollDTO;
import io.github.progmodular.gestaorh.dto.PayrollDTOResponse;
import io.github.progmodular.gestaorh.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.exceptions.handle.UserNotExistException;
import io.github.progmodular.gestaorh.infra.config.CalculatorFactory;
import io.github.progmodular.gestaorh.infra.config.calculator.*;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.Payroll;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import io.github.progmodular.gestaorh.repository.PayrollRepository;
import io.github.progmodular.gestaorh.infra.config.calculator.ICalculatorInterface;
import io.github.progmodular.gestaorh.validator.PayrollValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PayrollCalculatorOrchestrationService {

    @Autowired
    private IUserRepository employeeRepository;
    @Autowired
    private PayrollRepository payrollRepository;
    @Autowired
    private CalculatorFactory calculatorFactory;
    @Autowired
    private PayrollValidator payrollValidator;

    public List<PayrollDTOResponse> getPayrollHistory (Long employeeId)
    {
        payrollValidator.isEmployeeExistById(employeeId);
        List<Payroll> listReport = payrollRepository.findByEmployeeId(employeeId);
        return getListdto(listReport);
    }

    public List<PayrollDTOResponse> getPayrollByMonthAndYear (Long employeeId, Integer month, Integer year)
    {
        payrollValidator.isPayrollExistByEmployeeIdMonthYear(employeeId,month,year);
         List<Payroll> listReport = payrollRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year);

         return getListdto(listReport);
    }

    private static List<PayrollDTOResponse> getListdto (List<Payroll> listReport)
    {
        List<PayrollDTOResponse> listResponse = new ArrayList<>();
        for(Payroll lists : listReport)
        {
            UserDTOResponse userDTOResponse = getUserDTOResponse(lists);
            PayrollDTOResponse payrollDTOResponse = new PayrollDTOResponse
                    (
                            userDTOResponse,
                            lists.getId(),
                            lists.getMonth(),
                            lists.getYear(),
                            lists.getGrossSalary(),
                            lists.getDangerAllowance(),
                            lists.getValueTransportDiscount(),
                            lists.getFgts(),
                            lists.getHoursSalary(),
                            lists.getInssDiscount(),
                            lists.getIrrfDiscount(),
                            lists.getNetSalary(),
                            lists.getUnhealthyAllowance()
                    );
            listResponse.add(payrollDTOResponse);
        }
        return  listResponse;
    }

    private static UserDTOResponse getUserDTOResponse(Payroll lists) {
        Employee employee = lists.getEmployee();
        UserDTOResponse dto = new UserDTOResponse(
                employee.getId(),
                employee.getUserType(),
                employee.getName(),
                employee.getPosition(),
                employee.getDependents(),
                employee.getHoursWorkedMonth(),
                employee.getDaysWorked(),
                employee.getActualVTCost(),
                employee.getDegreeUnhealthiness(),
                employee.getHasDanger(),
                employee.getIsAdmin());
        return dto;
    }

    public Payroll calculateCompletePayroll(PayrollDTO request) {
        payrollValidator.nullValidation(request);
        payrollValidator.validateOnCreation(request);
        Optional<User> optionalUser = employeeRepository.findById(request.employeeId());
        User user = optionalUser
                .orElseThrow(() -> new UserNotExistException("User provided not exist in the system"));

        payrollValidator.isEmployee(user);
        if(user instanceof Employee employee)
        {
            List<ICalculatorInterface> calculators = calculatorFactory.createAllCalculators(employee);

            Payroll result = executeCalculations(employee, calculators,request);
            return payrollRepository.save(result);
        }

        return null;
    }

    private Payroll executeCalculations(Employee employee, List<ICalculatorInterface> calculators, PayrollDTO request) {
        Payroll result = new Payroll();
        result.setEmployee(employee);
        result.setGrossSalary(employee.getGrossSalary());
        result.setCalculationDate(LocalDateTime.now());
        result.setMonth(request.month());
        result.setYear(request.year());

        for (ICalculatorInterface calculator : calculators) {
            BigDecimal calculationResult = calculator.calculator();
            applyCalculationToResult(result, calculator, calculationResult,employee.getHasDanger());
        }

        return result;
    }

    private void applyCalculationToResult(Payroll result, ICalculatorInterface calculator, BigDecimal value,Boolean hasDanger) {
        if (calculator instanceof CalculatorDanger) {
            if(hasDanger == true)
            {
                result.setDangerAllowance(value);
            }
        } else if (calculator instanceof CalculatorDiscountValueTransport) {
            result.setValueTransportDiscount(value);
        } else if (calculator instanceof CalculatorFgts) {
            result.setFgts(value);
        } else if (calculator instanceof CalculatorHoursSalary) {
            result.setHoursSalary(value);
        } else if (calculator instanceof CalculatorInss) {
            result.setInssDiscount(value);
        } else if (calculator instanceof CalculatorIrrf) {
            result.setIrrfDiscount(value);
        } else if (calculator instanceof CalculatorNetSalary) {
            result.setNetSalary(value);
        } else if (calculator instanceof CalculatorUnhealthiness) {
            result.setUnhealthyAllowance(value);
        }
    }

    public Payroll recalculatePayroll (Long employeeId, int month, int year)
    {
        payrollValidator.isPayrollExistByEmployeeIdMonthYear(employeeId,month,year);
        List<Payroll> sheetReport = payrollRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year);
        Employee employee = sheetReport.getFirst().getEmployee();
        PayrollDTO payrollDTO = new PayrollDTO(employee.getId(),month,year);

        Payroll result = executeCalculations(employee, calculatorFactory.createAllCalculators(employee), payrollDTO);

        result.setId(sheetReport.getFirst().getId());
        return payrollRepository.save(result);
    }

    public void deletePayrollByIdMonthYearEmployeeId(Long id, int month, int year, Long employeeid) {
        int deletedCount = payrollRepository.deleteByIdAndMonthAndYearAndEmployeeId(id, month, year, employeeid);
        payrollValidator.deleteValidator(deletedCount);
    }


}
