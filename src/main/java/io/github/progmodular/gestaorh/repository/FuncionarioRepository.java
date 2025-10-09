package io.github.progmodular.gestaorh.repository;

import io.github.progmodular.gestaorh.model.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByEmail(String email);
    Funcionario findByEmail(String email);
}


