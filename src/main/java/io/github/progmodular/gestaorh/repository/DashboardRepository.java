package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.DashboardResumo;
import io.github.progmodular.gestaorh.model.entities.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DashboardRepository {

    private final List<Employee> employees = new ArrayList<>();

    public DashboardRepository() {
        Employee e1 = new Employee();
        e1.setName("Maria Silva");
        e1.setEmail("maria@empresa.com");
        e1.setGrossSalary(4200.00);
        e1.setCpf("123.456.789-00");
        e1.setPosition("Analista de RH");
        e1.setHoursWorked(160.0);
        e1.setDaysWorked(22);

        Employee e2 = new Employee();
        e2.setName("João Pereira");
        e2.setEmail("joao@empresa.com");
        e2.setGrossSalary(3800.00);
        e2.setCpf("987.654.321-00");
        e2.setPosition("Assistente Administrativo");
        e2.setHoursWorked(168.0);
        e2.setDaysWorked(21);

        Employee e3 = new Employee();
        e3.setName("Ana Souza");
        e3.setEmail("ana@empresa.com");
        e3.setGrossSalary(5200.00);
        e3.setCpf("456.789.123-00");
        e3.setPosition("Coordenadora de DP");
        e3.setHoursWorked(172.0);
        e3.setDaysWorked(22);

        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
    }

    public DashboardResumo getResumoDashboard() {
        int totalFuncionarios = employees.size();
        int totalFolhasGeradas = totalFuncionarios;
        double totalPago = employees.stream().mapToDouble(Employee::getGrossSalary).sum();

        return new DashboardResumo(totalFuncionarios, totalFolhasGeradas, totalPago);
    }

    public List<Employee> getFuncionarios() {
        return employees;
    }
}
