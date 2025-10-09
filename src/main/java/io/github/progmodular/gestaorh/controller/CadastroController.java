package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.model.entities.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CadastroController {

    @GetMapping("/cadastro")
    public String cadastro()
    {
        return "cadastro";
    }

//    @PostMapping(value = "/cadastro")
//    public String userRegister(@Valid User user, BindingResult result)
//    {
//        if(result.hasErrors())
//            return "redirect:/cadastro";
//
//        ur.save(user);
//        return "redirect:/login";
//    }
}
