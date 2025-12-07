package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.dto.UserDTO;
import io.github.progmodular.gestaorh.dto.UserDTOResponse;
import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.model.entities.PayrollAdmin;
import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@Tag(name = "Gestão de Usuários (CRUD)", description = "Endpoints para criar, buscar, atualizar e deletar usuários Employee e PayrollAdmin.")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    // Instância local para uso no controller, garantindo criptografia
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista com todos os usuários do sistema.")
    public ResponseEntity<List<UserDTOResponse>> getAll() {
        List<User> users = userService.findAll();

        List<UserDTOResponse> dtos = users.stream().map(user -> {
            if (user instanceof PayrollAdmin payrollAdmin) {
                return new UserDTOResponse(
                        payrollAdmin.getId(),
                        payrollAdmin.getName(),
                        payrollAdmin.getEmail(),
                        payrollAdmin.getUserType(),
                        payrollAdmin.getIsAdmin());
            } else if (user instanceof Employee employee) {
                return new UserDTOResponse(
                        employee.getId(),
                        employee.getUserType(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getGrossSalary(),
                        employee.getCpf(),
                        employee.getPosition(),
                        employee.getDependents(),
//                        employee.getHoursWorkedMonth(),
                        employee.getDaysWorked(),
                        employee.getActualVTCost(),
                        employee.getDegreeUnhealthiness(),
                        employee.getHasDanger(),
                        employee.getIsAdmin());
            }
            return null;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{id}")
    @Operation(summary = "Busca Usuário por ID",
            description = "Procura por um usuário (Employee ou PayrollAdmin) usando sua chave primária.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso. Retorna os dados do usuário."),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
            })
    public ResponseEntity<UserDTOResponse> get(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isEmpty()) return ResponseEntity.notFound().build();

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
//                    employee.getHoursWorkedMonth(),
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
            description = "Cria um novo registro de usuário. Retorna a URI do novo recurso.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos.")
            })
    public ResponseEntity<Object> create(@RequestBody UserDTO userdto) {
        User user = userService.checkUser(userdto);
        userService.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    @Operation(summary = "Login do Usuário", responses = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido."),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas.")
    })
    public ResponseEntity<Object> login(@RequestBody UserDTO loginDto) {
        try {
            User user = userService.authenticate(loginDto.email(), loginDto.password());
            if (user == null) {
                return ResponseEntity.status(400).body("Credenciais inválidas.");
            }

            UserDTOResponse userDtoResponse = new UserDTOResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getUserType(),
                    user.getIsAdmin()
            );
            return ResponseEntity.ok(userDtoResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    @PatchMapping("/employee/{id}")
    public ResponseEntity<Void> updateEmployeeData(@PathVariable("id") Long id, @RequestBody Employee employee) {
        Optional<User> userOptional = userService.getById(id);

        if (userOptional.isEmpty() || !(userOptional.get() instanceof Employee)) {
            return ResponseEntity.notFound().build();
        }

        Employee existingEmployee = (Employee) userOptional.get();
//        existingEmployee.setHoursWorkedMonth(employee.getHoursWorkedMonth());
        existingEmployee.setDaysWorked(employee.getDaysWorked());
        existingEmployee.setActualVTCost(employee.getActualVTCost());
        existingEmployee.setDegreeUnhealthiness(employee.getDegreeUnhealthiness());
        existingEmployee.setHasDanger(employee.getHasDanger());

        userService.updateById(existingEmployee, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTOResponse> delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza Usuário por ID",
            description = "Substitui os dados do usuário. Se a senha não for enviada, mantém a atual.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Atualização bem-sucedida."),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
            })
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        Optional<User> userOptional = userService.getById(id);
        if (userOptional.isEmpty()) return ResponseEntity.notFound().build();

        User user = userOptional.get();

        // 1. Lógica de Criptografia: Só prepara a nova senha SE ela foi enviada
        String encodedPassword = null;
        if (dto.password() != null && !dto.password().isBlank()) {
            encodedPassword = passwordEncoder.encode(dto.password());
        }

        // 2. Atualização para Employee
        if (user instanceof Employee employee) {
            employee.setName(dto.name());
            employee.setEmail(dto.email());

            // IMPORTANTE: Só atualiza a senha se encodedPassword não for nulo
            if (encodedPassword != null) {
                employee.setPassword(encodedPassword);
            }

            // Atualiza os outros campos (Verifique se o DTO pode vir nulo nesses campos numéricos)
            // Se o DTO vier com valores nulos/zeros indesejados, faça ifs similares
            employee.setGrossSalary(dto.grossSalary());
            employee.setCpf(dto.cpf());
            employee.setPosition(dto.position());
            employee.setDependents(dto.dependents());

            // Dados da folha geralmente não mudam na edição de perfil, mas se quiser permitir:

            // Booleanos primitivos no DTO podem vir false se omitidos, cuidado.
            // Se no DTO for Boolean (wrapper), use if(dto.hasDanger() != null)
            employee.setHasDanger(dto.hasDanger());
        }

        // 3. Atualização para PayrollAdmin
        if (user instanceof PayrollAdmin payrollAdmin) {
            payrollAdmin.setName(dto.name());
            payrollAdmin.setEmail(dto.email());

            // IMPORTANTE: Só atualiza a senha se encodedPassword não for nulo
            if (encodedPassword != null) {
                payrollAdmin.setPassword(encodedPassword);
            }
        }

        userService.updateById(user, id);
        return ResponseEntity.noContent().build();
    }
}