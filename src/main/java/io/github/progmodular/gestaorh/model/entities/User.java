package io.github.progmodular.gestaorh.model.entities;

import io.github.progmodular.gestaorh.model.Enum.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name="creation_date_time",nullable = false)
    private LocalDateTime creationDateTime;

    @LastModifiedDate
    @Column(name="last_date_time",nullable = false)
    private LocalDateTime lastUpdateDateTime;

    @PrePersist
    public void setDefaultValues() {
        if (this.isAdmin == null) {
            this.isAdmin = false;
        }
    }

    @Column(name="USER_TYPE", length = 100, nullable = false,insertable = false, updatable = false)
    private UserType userType;

}
