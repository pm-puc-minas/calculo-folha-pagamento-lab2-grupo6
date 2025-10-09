package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractUserRepository<T extends User> extends JpaRepository<T, Long> {

    T findByEmail(String email);
    T findByEmailAndPassword(String email, String password);

}