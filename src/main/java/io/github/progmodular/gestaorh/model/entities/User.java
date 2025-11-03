package io.github.progmodular.gestaorh.model.entities;

import io.github.progmodular.gestaorh.model.Enum.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name="id")
    private Long id;

    @Column(name="name",length = 100)
    @NotBlank(message = "Name can not be null or blank")
    private String name;

    @Column(name="email",length = 100)
    @NotBlank(message = "Email can not be null or blank")
    private String email;

    @Column(name="password",length = 100)
    @NotBlank(message = "Password can not be null or blank")
    private String password;

    @Column(name="is_admin",columnDefinition = "boolean default false")
    @NotNull(message = "IsAdmin can not be null or blank")
    private Boolean isAdmin;

    @CreatedDate
    @Column(name="creation_date_time")
    @NotNull(message = "creation date can not be null or blank")
    private LocalDateTime creationDateTime;

    @LastModifiedDate
    @Column(name="last_date_time")
    @NotNull(message = "Last update date can not be null or blank")
    private LocalDateTime lastUpdateDateTime;

    @PrePersist
    public void setDefaultValues() {
        if (this.isAdmin == null) {
            this.isAdmin = false;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name="USER_TYPE", length = 100,insertable = false, updatable = false)
    @NotNull(message = "UserType can not be null or blank")
    private UserType userType;

}
