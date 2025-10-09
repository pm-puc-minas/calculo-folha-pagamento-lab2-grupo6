package io.github.progmodular.gestaorh.infra.security;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.PayrollRepository;
import io.github.progmodular.gestaorh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public User login(String email, String password, String userType) {
        return switch (userType.toUpperCase()) {
            case "EMPLOYEE" -> employeeRepository.findByEmailAndPassword(email, password);
            case "PAYROLL" -> payrollRepository.findByEmailAndPassword(email, password);
            default -> null;
        };
    }

    public PayrollAdmin loginPayroll(String email, String password) {
        return payrollRepository.findByEmailAndPassword(email, password);
    }

    public Employee loginEmployee(String email, String password) {
        return employeeRepository.findByEmailAndPassword(email, password);
    }
}
