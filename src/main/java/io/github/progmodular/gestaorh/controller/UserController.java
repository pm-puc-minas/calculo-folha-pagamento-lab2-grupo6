package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.controller.dto.UserDTO;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDTO userdto)
    {
        User user;

        UserType currentlyUserType = userdto.getUserType();
        if(currentlyUserType == UserType.EMPLOYEE)
        {
            user = userdto.setEmployee();
        }
        else if(currentlyUserType == UserType.PAYROLL_ADMIN)
        {
            user = userdto.setPayroll();
        }
        else {
            throw new IllegalArgumentException("Tipo de usuário inválido ou não mapeado para instanciação.");
        }

        userService.saveUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
