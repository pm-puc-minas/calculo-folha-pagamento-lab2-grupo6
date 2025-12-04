package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  io.github.progmodular.gestaorh.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userService.getByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(401).body("Usuário não encontrado.");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Senha incorreta.");
        }

        UserDTOResponse dto = null;

        if (user instanceof PayrollAdmin admin) {
            dto = new UserDTOResponse(
                    admin.getId(),
                    admin.getName(),
                    admin.getEmail(),
                    admin.getUserType(),
                    admin.getIsAdmin()
            );
        }

        if (user instanceof Employee emp) {
            dto = new UserDTOResponse(
                    emp.getId(),
                    emp.getUserType(),
                    emp.getName(),
                    emp.getEmail(),
                    emp.getGrossSalary(),
                    emp.getCpf(),
                    emp.getPosition(),
                    emp.getDependents(),
                    emp.getHoursWorkedMonth(),
                    emp.getDaysWorked(),
                    emp.getActualVTCost(),
                    emp.getDegreeUnhealthiness(),
                    emp.getHasDanger(),
                    emp.getIsAdmin()
            );
        }

        return ResponseEntity.ok(dto);
    }
}
