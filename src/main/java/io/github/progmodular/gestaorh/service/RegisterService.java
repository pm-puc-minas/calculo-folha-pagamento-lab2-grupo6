package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee)
    {
        return employeeRepository.save(employee);
    }

}
