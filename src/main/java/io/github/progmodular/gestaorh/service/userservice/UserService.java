package io.github.progmodular.gestaorh.service.userservice;

import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

}
