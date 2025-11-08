package io.github.progmodular.gestaorh.validator;

import io.github.progmodular.gestaorh.dto.ErrorField;
import io.github.progmodular.gestaorh.dto.UserDTO;
import io.github.progmodular.gestaorh.exceptions.handle.DeleteErroException;
import io.github.progmodular.gestaorh.exceptions.handle.DuplicaValueException;
import io.github.progmodular.gestaorh.exceptions.handle.UserNotExistException;
import io.github.progmodular.gestaorh.exceptions.handle.ValidationListException;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator
{
    @Autowired
    private IUserRepository userRepository;

    public void validateOnCreation(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent())
        {
            throw new DuplicaValueException("Email is already used by another user.");
        }
    }
    public void isUserExistById(Long id) {
        if(userRepository.findById(id).isEmpty())
        {
            throw new UserNotExistException("User provided not exist in the system");
        }
    }

    public void deleteValidator(int deletedCount)
    {
        if (deletedCount == 0) {
            throw new DeleteErroException("ERROR DELETING: Record not found");
        }
    }

    public void nullValidation(UserDTO user)
    {

        List<ErrorField> errors = new ArrayList<>();


        if (user.name() == null || user.name().trim().isEmpty())
        {
            errors.add(new ErrorField("name", "Name is a mandatory field."));
        }
        if (user.email() == null || user.email().trim().isEmpty())
        {
            errors.add(new ErrorField("email", "Email is a mandatory field."));
        }
        if (user.password() == null || user.password().trim().isEmpty())
        {
            errors.add(new ErrorField("password", "Password is a mandatory field."));
        }
        if (user.isAdmin() == null)
        {
            errors.add(new ErrorField("isAdmin", "isAdmin is a mandatory field."));
        }

        if (user.userType() == null)
        {
            errors.add(new ErrorField("userType", "userType is a mandatory field."));
        }


        if(user.userType() == UserType.EMPLOYEE)
        {
            if (user.grossSalary() == null)
            {
                errors.add(new ErrorField("grossSalary", "Gross salary is a mandatory field."));
            }

            if (user.cpf() == null || user.cpf().trim().isEmpty())
            {
                errors.add(new ErrorField("cpf", "CPF is a mandatory field."));
            }

            if (user.position() == null || user.position().trim().isEmpty())
            {
                errors.add(new ErrorField("position", "Position is a mandatory field."));
            }

            if (user.hoursWorkedMonth() == null)
            {
                errors.add(new ErrorField("hoursWorked", "hoursWorked is a mandatory field."));
            }

            if (user.daysWorked() == null)
            {
                errors.add(new ErrorField("daysWorked", "daysWorked is a mandatory field."));
            }

        }
        if (!errors.isEmpty()) {
            throw new ValidationListException(errors);
        }
    }




}
