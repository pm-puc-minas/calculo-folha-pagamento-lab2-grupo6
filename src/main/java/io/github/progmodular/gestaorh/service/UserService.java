package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.dto.UserDTO;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import io.github.progmodular.gestaorh.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    UserValidator userValidator;

    public void saveUser(User user)
    {
        userValidator.validateOnCreation(user);
        userRepository.save(user);
    }

    public Optional<User> getById(Long id)
    {
        userValidator.isUserExistById(id);
        return userRepository.findById(id);
    }

    public User getByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public void deleteById(Long id)
    {
        userValidator.isUserExistById(id);
        userRepository.deleteById(id);
    }

    public void updateById(User user,Long id)
    {
        userValidator.isUserExistById(id);
        userRepository.save(user);
    }

    public User checkUser(UserDTO userdto) {
        userValidator.nullValidation(userdto);
        User user = null;
        if(userdto.userType() == UserType.EMPLOYEE)
        {
            return  userdto.setEmployee();
        }
        else if(userdto.userType() == UserType.PAYROLL_ADMIN)
        {
            return userdto.setPayroll();
        }
        
        return user;
    }
}
