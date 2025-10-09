package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "funcionario")
public class Funcionario extends User {

    @Override
    public String getUserType() {
        return "FUNCIONARIO";
    }
}


