package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payroll_users")
@DiscriminatorValue("payroll_user")
public class PayrollAdmin extends User
{
    @Getter
    @Setter
    @NotNull
    private Boolean isAdmin = true;

    @Override
    public String getUserType() {
        return "PAYROLL";
    }
}
