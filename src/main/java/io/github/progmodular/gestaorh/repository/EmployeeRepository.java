package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends AbstractUserRepository<Employee>{
}
