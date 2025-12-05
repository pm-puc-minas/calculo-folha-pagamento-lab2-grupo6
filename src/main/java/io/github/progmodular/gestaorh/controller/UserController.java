    package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.dto.UserDTO;
import io.github.progmodular.gestaorh.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.model.Enum.UserType;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("users")
@Tag(name = "Gestão de Usuários (CRUD)", description = "Endpoints para criar, buscar, atualizar e deletar usuários Employee e PayrollAdmin.")
@CrossOrigin(origins = "*")
public class UserController {

        @Autowired
        UserService userService;

        @GetMapping("{id}")
        @Operation(summary = "Busca Usuário por ID",
                description = "Procura por um usuário (Employee ou PayrollAdmin) usando sua chave primária.",
                responses = {
                        @ApiResponse(responseCode = "200", description = "Sucesso. Retorna os dados do usuário."),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
                })
        public ResponseEntity<UserDTOResponse> get(@PathVariable("id") Long id) {
            Optional<User> optionalUser = userService.getById(id);
            User user = optionalUser.get();

            if (user instanceof PayrollAdmin payrollAdmin) {
                UserDTOResponse dto = new UserDTOResponse(
                        payrollAdmin.getId(),
                        payrollAdmin.getName(),
                        payrollAdmin.getEmail(),
                        payrollAdmin.getUserType(),
                        payrollAdmin.getIsAdmin());
                return ResponseEntity.ok(dto);
            }
            if (user instanceof Employee employee) {
                UserDTOResponse dto = new UserDTOResponse(
                        employee.getId(),
                        employee.getUserType(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getGrossSalary(),
                        employee.getCpf(),
                        employee.getPosition(),
                        employee.getDependents(),
                        employee.getHoursWorkedMonth(),
                        employee.getDaysWorked(),
                        employee.getActualVTCost(),
                        employee.getDegreeUnhealthiness(),
                        employee.getHasDanger(),
                        employee.getIsAdmin());
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        }

    @PostMapping
    @Operation(summary = "Cria Novo Usuário",
            description = "Cria um novo registro de usuário (Employee ou PayrollAdmin) com base no DTO de entrada. Retorna a URI do novo recurso.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso. Retorna o header Location."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos (erro de validação).")
            })
    public ResponseEntity<Object> create(@RequestBody UserDTO userdto, BindingResult bindingResult) {

        User user = userService.checkUser(userdto);

        // Verificando se o usuário é do tipo EMPLOYEE
        if (userdto.userType() == UserType.EMPLOYEE) {
            // Criação de Employee com os dados fornecidos
            Employee employee = userdto.setEmployee();
            userService.saveUser(employee);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(employee.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }

        // Verificando se o usuário é do tipo PAYROLL_ADMIN
        if (userdto.userType() == UserType.PAYROLL_ADMIN) {
            // Criação de PayrollAdmin com os dados fornecidos
            PayrollAdmin payrollAdmin = userdto.setPayroll();
            userService.saveUser(payrollAdmin);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(payrollAdmin.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/employee/{id}")
    @Operation(summary = "Atualiza dados adicionais do Employee",
            description = "Atualiza dados como horas trabalhadas, dias trabalhados, custo VT, grau de insalubridade e se possui periculosidade.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização bem-sucedida."),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos.")
            })
    public ResponseEntity<Void> updateEmployeeData(@PathVariable("id") Long id, @RequestBody Employee employee) {
        Optional<User> userOptional = userService.getById(id);

        if (userOptional.isEmpty() || !(userOptional.get() instanceof Employee)) {
            return ResponseEntity.notFound().build();
        }

        Employee existingEmployee = (Employee) userOptional.get();

        // Atualizando os dados do Employee
        existingEmployee.setHoursWorkedMonth(employee.getHoursWorkedMonth());
        existingEmployee.setDaysWorked(employee.getDaysWorked());
        existingEmployee.setActualVTCost(employee.getActualVTCost());
        existingEmployee.setDegreeUnhealthiness(employee.getDegreeUnhealthiness());
        existingEmployee.setHasDanger(employee.getHasDanger());

        userService.updateById(existingEmployee, id);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{id}")
        @Operation(summary = "Deleta Usuário por ID",
                description = "Remove permanentemente um registro de usuário usando sua chave primária.",
                responses = {
                        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso (Sem Conteúdo)."),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
                })
        public ResponseEntity<UserDTOResponse> delete(@PathVariable("id") Long id) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("{id}")
        @Operation(summary = "Atualiza Usuário por ID",
                description = "Substitui completamente os dados do usuário especificado pelo DTO de entrada (PUT).",
                responses = {
                        @ApiResponse(responseCode = "204", description = "Atualização bem-sucedida (Sem Conteúdo)."),
                        @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos.")
                })
        public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
            Optional<User> userOptional = userService.getById(id);

            User user = userOptional.get();

            if (user instanceof Employee employee) {
                employee.setName(dto.name());
                employee.setEmail(dto.email());
                employee.setPassword(dto.password());

                employee.setGrossSalary(dto.grossSalary());
                employee.setCpf(dto.cpf());
                employee.setPosition(dto.position());
                employee.setDependents(dto.dependents());
                employee.setHoursWorkedMonth(dto.hoursWorkedMonth());
                employee.setDaysWorked(dto.daysWorked());
                employee.setActualVTCost(dto.actualVTCost());
                employee.setDegreeUnhealthiness(dto.degreeUnhealthiness());
                employee.setHasDanger(dto.hasDanger());
            }

            if (user instanceof PayrollAdmin payrollAdmin) {
                payrollAdmin.setName(dto.name());
                payrollAdmin.setEmail(dto.email());
                payrollAdmin.setPassword(dto.password());
            }

            userService.updateById(user, id);
            return ResponseEntity.noContent().build();
        }
    }