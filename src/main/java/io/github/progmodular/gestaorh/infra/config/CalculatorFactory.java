package io.github.progmodular.gestaorh.infra.config;

import io.github.progmodular.gestaorh.infra.config.calculator.*;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.infra.config.calculator.ICalculatorInterface;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculatorFactory {

    public List<ICalculatorInterface> createAllCalculators(Employee employee) {
        return List.of(
                new CalculatorDanger(employee),
                new CalculatorFgts(employee),
                new CalculatorInss(employee),
                new CalculatorIrrf(employee),
                new CalculatorNetSalary(employee),
                new CalculatorUnhealthiness(employee),
                new CalculatorDiscountValueTransport(employee)
        );
    }

    public ICalculatorInterface createCalculator(String type, Employee employee) {
            return switch (type) {
                case "DANGER" -> new CalculatorDanger(employee);
                case "FGTS" -> new CalculatorFgts(employee);
                case "INSS" -> new CalculatorInss(employee);
                case "IRRF" -> new CalculatorIrrf(employee);
                case "NET_SALARY" -> new CalculatorNetSalary(employee);
                case "UNHEALTHY" -> new CalculatorUnhealthiness(employee);
                case "VALUE_TRANSPORT" -> new CalculatorDiscountValueTransport(employee);
                default -> throw new IllegalStateException("Unexpected value: " + type);

            };
    }
}
