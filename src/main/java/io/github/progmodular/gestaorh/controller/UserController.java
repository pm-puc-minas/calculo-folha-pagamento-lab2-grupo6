package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.controller.dto.UserDTO;
import io.github.progmodular.gestaorh.controller.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDTOResponse> get(@PathVariable("id") Long id)
    {
        Optional<User> optionalUser = userService.getById(id);
        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();

            if(user instanceof PayrollAdmin payrollAdmin)
            {
                UserDTOResponse dto = new UserDTOResponse(
                        payrollAdmin.getId(),
                        payrollAdmin.getName(),
                        payrollAdmin.getEmail(),
                        payrollAdmin.getUserType(),
                        payrollAdmin.getIsAdmin());
                return ResponseEntity.ok(dto);
            }
            if(user instanceof Employee employee)
            {
                UserDTOResponse dto = new UserDTOResponse(
                        employee.getId(),
                        employee.getUserType(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getGrossSalary(),
                        employee.getCpf(),
                        employee.getPosition(),
                        employee.getHoursWorked(),
                        employee.getDaysWorked(),
                        employee.getIsAdmin());
                return ResponseEntity.ok(dto);
            }
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDTO userdto)
    {

        User user = userService.checkUser(userdto,userdto.getUserType());

        if(user == null)
        {
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

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTOResponse> delete(@PathVariable("id") Long id)
    {
        Optional<User> optionalUser = userService.getById(id);

        if(optionalUser.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
