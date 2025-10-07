package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @NotEmpty
    private String name;

    @Getter
    @Setter
    @NotEmpty
    private String userLogin;

    @Getter
    @Setter
    @NotEmpty
    private String password;

    public User() {
    }
    public User(String userLogin,String password, String name)
    {
        this.userLogin = userLogin;
        this.password = password;
        this.name = name;
    }

}
