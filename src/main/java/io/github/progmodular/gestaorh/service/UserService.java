package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.controller.dto.UserDTO;
import io.github.progmodular.gestaorh.controller.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

    public Optional<User> getById(Long id)
    {
        return userRepository.findById(id);
    }

    public User checkUser(UserDTO userdto,UserType currentlyUserType) {
        User user = null;
        if(currentlyUserType == UserType.EMPLOYEE)
        {
            return  userdto.setEmployee();
        }
        else if(currentlyUserType == UserType.PAYROLL_ADMIN)
        {
            return userdto.setPayroll();
        }
        
        return user;
    }
}
