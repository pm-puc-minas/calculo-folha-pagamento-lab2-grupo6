package io.github.progmodular.gestaorh.controller;

import io.github.progmodular.gestaorh.model.entities.User;
import io.github.progmodular.gestaorh.repository.UserRepository;
import io.github.progmodular.gestaorh.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

    @Autowired
    private UserRepository ur;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        model.addAttribute("name",CookieService.getCookie(request,"userName"));
        return "index";
    }

    @PostMapping(value = "/login")
    public String loginUser(User user, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        User loggedUser = this.ur.login(user.getEmail(),user.getPassword());

        if(loggedUser != null)
        {
            CookieService.setCookie(response,"usuarioId",String.valueOf(loggedUser.getId()),10000);
            CookieService.setCookie(response,"userName",String.valueOf(loggedUser.getName()),10000);
            return "redirect:/";
        }

        model.addAttribute("error","Invalid User");
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
