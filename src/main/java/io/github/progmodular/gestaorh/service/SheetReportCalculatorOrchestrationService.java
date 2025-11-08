package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.dto.SheetRequest;
import io.github.progmodular.gestaorh.dto.SheetResponse;
import io.github.progmodular.gestaorh.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.infra.config.CalculatorFactory;
import io.github.progmodular.gestaorh.infra.config.calculator.*;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.SheetReport;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import io.github.progmodular.gestaorh.repository.SheetReportRepository;
import io.github.progmodular.gestaorh.infra.config.calculator.ICalculatorInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SheetReportCalculatorOrchestrationService {

    @Autowired
    private IUserRepository employeeRepository;
    @Autowired
    private SheetReportRepository sheetReportRepository;
    @Autowired
    private CalculatorFactory calculatorFactory;

    public List<SheetResponse> getPayrollHistory (Long employeeId)
    {
        List<SheetReport> listReport = sheetReportRepository.findByEmployeeId(employeeId);
        return getListdto(listReport);
    }

    public List<SheetResponse> getPayrollByMonthAndYear (Long employeeId, Integer month, Integer year)
    {
         List<SheetReport> listReport = sheetReportRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year);

         return getListdto(listReport);
    }

    private static List<SheetResponse> getListdto (List<SheetReport> listReport)
    {
        List<SheetResponse> listResponse = new ArrayList<>();
        for(SheetReport lists : listReport)
        {
            UserDTOResponse userDTOResponse = getUserDTOResponse(lists);
            SheetResponse sheetResponse = new SheetResponse
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
            listResponse.add(sheetResponse);
        }
        return  listResponse;
    }

    private static UserDTOResponse getUserDTOResponse(SheetReport lists) {
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
                employee.getIsAdmin());
        return dto;
    }

    public SheetReport calculateCompletePayroll(SheetRequest request) {
        User user = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee não encontrado"));

        if(user instanceof Employee employee)
        {
            List<ICalculatorInterface> calculators = calculatorFactory.createAllCalculators(employee);

            SheetReport result = executeCalculations(employee, calculators,request);
            return sheetReportRepository.save(result);
        }
        if(user instanceof PayrollAdmin payrollAdmin)
        {
            throw new IllegalArgumentException("payroll admin nao pode possuir folha");
        }

        return null;
    }

    private SheetReport executeCalculations(Employee employee, List<ICalculatorInterface> calculators, SheetRequest request) {
        SheetReport result = new SheetReport();
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

    private void applyCalculationToResult(SheetReport result, ICalculatorInterface calculator, BigDecimal value) {
        if (calculator instanceof CalculatorDanger) {
            result.setDangerAllowance(value);
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

    public SheetReport recalculatePayroll (Long employeeId, int month, int year)
    {
        List<SheetReport> sheetReport = sheetReportRepository.findByEmployeeIdAndMonthAndYear(employeeId,month,year);
        Employee employee = sheetReport.getFirst().getEmployee();
        SheetRequest sheetRequest = new SheetRequest(employee.getId(),month,year);

        SheetReport result = executeCalculations(employee, calculatorFactory.createAllCalculators(employee), sheetRequest);

        result.setId(sheetReport.getFirst().getId());
        return sheetReportRepository.save(result);
    }

    public void deletePayrollByIdMonthYearEmployeeId(Long id, int month, int year, Long employeeid) {
        int deletedCount = sheetReportRepository.deleteByIdAndMonthAndYearAndEmployeeId(id, month, year, employeeid);

        if (deletedCount == 0) {
            throw new IllegalArgumentException("ERRO AO DELETAR RELATORIO: Registro não encontrado");
        }
    }
}
