package io.github.progmodular.gestaorh.model.entities;

import io.github.progmodular.gestaorh.model.Enum.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@Table(name = "users")
public abstract class User {

    public User() {
        this.isAdmin = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name="name",length = 100,nullable = false)
    private String name;

    @Column(name="email",length = 100,nullable = false)
    private String email;

    @Column(name="password",length = 100,nullable = false)
    private String password;

    @Column(name="is_admin",nullable = false,columnDefinition = "boolean default false")
    private Boolean isAdmin;

    @PrePersist
    public void setDefaultValues() {
        if (this.isAdmin == null) {
            this.isAdmin = false;
        }
    }

    @Column(name="USER_TYPE", length = 100, nullable = false,insertable = false, updatable = false)
    private UserType userType;

}
