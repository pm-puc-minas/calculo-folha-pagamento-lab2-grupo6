package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.model.entities.Employee;
import io.github.progmodular.gestaorh.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PostMapping
    public Employee create(@RequestBody Employee employee)
    {
        return registerService.createEmployee(employee);
    }
}
