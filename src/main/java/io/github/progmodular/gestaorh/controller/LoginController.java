package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Autowired
    private UserRepository ur;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro()
    {
        return "cadastro";
    }

    @PostMapping(value = "/cadastro")
    public String userRegister(@Valid User user, BindingResult result)
    {
        if(result.hasErrors())
            return "redirect:/cadastro";

        ur.save(user);
        return "redirect:/login";
    }
}
