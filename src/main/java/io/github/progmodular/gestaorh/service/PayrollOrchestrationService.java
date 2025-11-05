package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.controller.dto.PayrollRequest;
import io.github.progmodular.gestaorh.infra.config.CalculatorFactory;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.PayrollResult;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import io.github.progmodular.gestaorh.repository.PayrollResultRepository;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;
import io.github.progmodular.gestaorh.service.calculatorservice.calculator.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PayrollOrchestrationService {

    private final IUserRepository employeeRepository;
    private final PayrollResultRepository payrollResultRepository;
    private final CalculatorFactory calculatorFactory;

    public PayrollOrchestrationService(IUserRepository employeeRepository, PayrollResultRepository payrollResultRepository, CalculatorFactory calculatorFactory) {
        this.employeeRepository = employeeRepository;
        this.payrollResultRepository = payrollResultRepository;
        this.calculatorFactory = calculatorFactory;
    }

    public List<PayrollResult> getPayrollByMonthAndYear (Long employeeId, Integer month, Integer year)
    {
        return payrollResultRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year);
    }

    public List<PayrollResult> getPayrollHistory (Long employeeId)
    {
        return payrollResultRepository.findByEmployeeId(employeeId);
    }

    public PayrollResult calculateCompletePayroll(PayrollRequest request) {
        User user = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee não encontrado"));
//        user.setUserType(UserType.EMPLOYEE);

        if(user instanceof Employee employee)
        {
            List<ICalculatorInterface> calculators = calculatorFactory.createAllCalculators(employee);

            PayrollResult result = executeCalculations(employee, calculators,request);

            return payrollResultRepository.save(result);
        }
        if(user instanceof PayrollAdmin payrollAdmin)
        {
            throw new IllegalArgumentException("payroll admin nao pode possuir folha");
        }

        return null;
    }

    private PayrollResult executeCalculations(Employee employee, List<ICalculatorInterface> calculators,PayrollRequest request) {
        PayrollResult result = new PayrollResult();
        result.setEmployee(employee);
        result.setGrossSalary(employee.getGrossSalary());
        result.setCalculationDate(LocalDateTime.now());
        result.setMonth(request.month());
        result.setYear(request.year());

        for (ICalculatorInterface calculator : calculators) {
            BigDecimal calculationResult = calculator.calculator();
            applyCalculationToResult(result, calculator, calculationResult);
        }

        return result;
    }

    private void applyCalculationToResult(PayrollResult result, ICalculatorInterface calculator, BigDecimal value) {
        if (calculator instanceof CalculatorInss) {
            result.setInssDiscount(value);
        } else if (calculator instanceof CalculatorIrff) {
            result.setIrrfDiscount(value);
        } else if (calculator instanceof CalculatorFgts) {
            result.setFgts(value);
        } else if (calculator instanceof CalculatorDanger) {
            result.setDangerAllowance(value);
        } else if (calculator instanceof CalculatorNetSalary) {
            result.setNetSalary(value);
        }
    }
}
