package io.github.progmodular.gestaorh.model.entities;

import io.github.progmodular.gestaorh.model.Enum.UserType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("PAYROLL_ADMIN")

public class PayrollAdmin extends User
{
    public PayrollAdmin() {
        super();
        this.setUserType(UserType.PAYROLL_ADMIN);
    }

}
