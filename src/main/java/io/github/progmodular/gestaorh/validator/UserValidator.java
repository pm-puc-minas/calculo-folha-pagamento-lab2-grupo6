package io.github.progmodular.gestaorh.validator;

import io.github.progmodular.gestaorh.controller.exceptions.DuplicaValueException;
import io.github.progmodular.gestaorh.controller.exceptions.UserNotExistException;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


}
