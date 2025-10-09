package io.github.progmodular.gestaorh.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("payroll_user")
public class PayrollAdmin extends User
{
    @Getter
    @Setter
    @NotEmpty
    private Boolean isAdmin = true;

    @Override
    public String getUserType() {
        return "PAYROLL";
    }
}
