package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
}
