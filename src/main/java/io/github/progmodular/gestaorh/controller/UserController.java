package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.controller.dto.UserDTO;
import io.github.progmodular.gestaorh.controller.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.service.UserService;
import io.github.progmodular.gestaorh.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
                        employee.getHoursWorkedMonth(),
                        employee.getDaysWorked(),
                        employee.getIsAdmin());
                return ResponseEntity.ok(dto);
            }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserDTO userdto, BindingResult bindingResult)
    {

            User user = userService.checkUser(userdto);


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
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,@RequestBody UserDTO dto)
    {
        Optional<User> userOptional = userService.getById(id);

        User user = userOptional.get();

        if(user instanceof Employee employee)
        {
            employee.setName(dto.name());
            employee.setEmail(dto.email());
            employee.setPassword(dto.password());
            employee.setGrossSalary(dto.grossSalary());
            employee.setCpf(dto.cpf());
            employee.setPosition(dto.position());
            employee.setHoursWorkedMonth(dto.hoursWorkedMonth());
            employee.setDaysWorked(dto.daysWorked());
        }

        if(user instanceof PayrollAdmin payrollAdmin)
        {
            payrollAdmin.setName(dto.name());
            payrollAdmin.setEmail(dto.email());
            payrollAdmin.setPassword(dto.password());
        }

        userService.updateById(user,id);
        return ResponseEntity.noContent().build();
    }
}
