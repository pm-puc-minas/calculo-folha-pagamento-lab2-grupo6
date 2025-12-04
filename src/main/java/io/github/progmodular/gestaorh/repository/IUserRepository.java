package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    //Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Employee save(Employee employee);

    User findByEmail(String email);

}
