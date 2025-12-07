package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.dto.UserDTO;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.IUserRepository;
import io.github.progmodular.gestaorh.repository.PayrollRepository;
import io.github.progmodular.gestaorh.validator.UserValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;


    @Autowired
    PayrollRepository payrollRepository;

    @Autowired
    UserValidator userValidator;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + email);
        }
        String role = user.getIsAdmin() ? "ADMIN" : "USER";
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();
    }

    public void saveUser(User user) {
        userValidator.validateOnCreation(user);
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public Optional<User> getById(Long id) {
        userValidator.isUserExistById(id);
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;
        if (!passwordEncoder.matches(password, user.getPassword())) return null;
        return user;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Transactional
    public void deleteById(Long id) {
        userValidator.isUserExistById(id);
        payrollRepository.deleteAllByEmployeeId(id);
        userRepository.deleteById(id);
    }


    public void updateById(User user, Long id) {
        userValidator.isUserExistById(id);
        userRepository.save(user);
    }

    public User checkUser(UserDTO userdto) {
        userValidator.nullValidation(userdto);
        if(userdto.userType() == UserType.EMPLOYEE) {
            return userdto.setEmployee();
        } else if(userdto.userType() == UserType.PAYROLL_ADMIN) {
            return userdto.setPayroll();
        }
        return null;
    }
}