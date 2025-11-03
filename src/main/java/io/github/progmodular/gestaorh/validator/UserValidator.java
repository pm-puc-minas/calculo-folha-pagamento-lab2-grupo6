package io.github.progmodular.gestaorh.validator;

import io.github.progmodular.gestaorh.controller.dto.ErrorField;
import io.github.progmodular.gestaorh.controller.dto.UserDTO;
import io.github.progmodular.gestaorh.controller.exceptions.DuplicaValueException;
import io.github.progmodular.gestaorh.controller.exceptions.UserNotExistException;
import io.github.progmodular.gestaorh.controller.exceptions.ValidationListException;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public void nullValidation(User user)
    {
        List<ErrorField> errors = new ArrayList<>();

        if (user.getName() == null || user.getName().trim().isEmpty())
        {
            errors.add(new ErrorField("name", "Name is a mandatory field."));
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty())
        {
            errors.add(new ErrorField("email", "Email is a mandatory field."));
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty())
        {
            errors.add(new ErrorField("password", "Password is a mandatory field."));
        }
        if (user.getIsAdmin() == null)
        {
            errors.add(new ErrorField("isAdmin", "isAdmin is a mandatory field."));
        }
        System.out.println("DEBUG: Iniciando validação - userType: " + user.getUserType());

        if (user.getUserType() == null)
        {
            errors.add(new ErrorField("userType", "userType is a mandatory field."));
        }

//        if (!(user.getUserType().equals(UserType.EMPLOYEE) || user.getUserType().equals(UserType.PAYROLL_ADMIN)))
//        {
//            errors.add(new ErrorField("userType", "userType " + user.getUserType() + "is not a valid value."));
//        }


        if(user instanceof  Employee employee)
        {
            if (employee.getGrossSalary() == null)
            {
                errors.add(new ErrorField("grossSalary", "Gross salary is a mandatory field."));
            }

            if (employee.getCpf() == null || employee.getCpf().trim().isEmpty())
            {
                errors.add(new ErrorField("cpf", "CPF is a mandatory field."));
            }

            if (employee.getPosition() == null || employee.getPosition().trim().isEmpty())
            {
                errors.add(new ErrorField("position", "Position is a mandatory field."));
            }

            if (employee.getHoursWorked() == null)
            {
                errors.add(new ErrorField("hoursWorked", "hoursWorked is a mandatory field."));
            }

            if (employee.getDaysWorked() == null)
            {
                errors.add(new ErrorField("daysWorked", "daysWorked is a mandatory field."));
            }


        }
        if (!errors.isEmpty()) {
            throw new ValidationListException(errors);
        }
    }

    public void nullValidation(UserDTO user)
    {
        System.out.println("DEBUG: Iniciando validação - userType: " + (user.userType() == null));

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

            if (user.hoursWorked() == null)
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
