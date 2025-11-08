package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.controller.dto.PayrollRequest;
import io.github.progmodular.gestaorh.controller.dto.PayrollResponseDTO;
import io.github.progmodular.gestaorh.infra.config.CalculatorFactory;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.PayrollResult;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import io.github.progmodular.gestaorh.repository.PayrollResultRepository;
import io.github.progmodular.gestaorh.service.calculatorservice.ICalculatorInterface;
import io.github.progmodular.gestaorh.service.calculatorservice.calculator.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PayrollOrchestrationService {

    @Autowired
    private IUserRepository employeeRepository;
    @Autowired
    private PayrollResultRepository payrollResultRepository;
    @Autowired
    private CalculatorFactory calculatorFactory;
    @Autowired
    private PayrollReportService payrollReportService;


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
        if (calculator instanceof CalculatorDanger) {
            result.setDangerAllowance(value);
        } else if (calculator instanceof CalculatorDiscountValueTransport ) {
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

    public PayrollResult recalculatePayroll (Long employeeId, int month, int year)
    {
        List<PayrollResult> payrollResult = payrollResultRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year);
        Employee employee = payrollResult.getFirst().getEmployee();
        PayrollRequest payrollRequest = new PayrollRequest(employee.getId(),month,year);

        PayrollResult result = executeCalculations(employee, calculatorFactory.createAllCalculators(employee),payrollRequest);

        result.setId(payrollResult.getFirst().getId());
        return payrollResultRepository.save(result);
    }

    public void deletePayrollByIdMonthYearEmployeeId(Long id, int month, int year, Long employeeid) {
        int deletedCount = payrollResultRepository.deleteByIdAndMonthAndYearAndEmployeeId(id, month, year, employeeid);

        if (deletedCount == 0) {
            throw new IllegalArgumentException("ERRO AO DELETAR RELATORIO: Registro não encontrado");
        }
    }

    public PayrollResponseDTO getPayrollById(Long id) {
        var payroll = payrollResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));
        return new PayrollResponseDTO(payroll);
    }

}
