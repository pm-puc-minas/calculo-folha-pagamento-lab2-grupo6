//package io.github.progmodular.gestaorh.controller;
//
//import io.github.progmodular.gestaorh.model.entities.User;
//import io.github.progmodular.gestaorh.infra.security.AuthenticationService;
//import io.github.progmodular.gestaorh.infra.web.CookieService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import java.io.UnsupportedEncodingException;
//
//@RestController
//public class LoginController {
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @GetMapping("/login")
//    public String login()
//    {
//        return "login";
//    }
//
//    @GetMapping("/")
//    public String index(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
//        model.addAttribute("name",CookieService.getCookie(request,"userName"));
//        return "index";
//    }
//
//    @PostMapping(value = "/login")
//    public String loginUser(
//            @RequestParam String email,
//            @RequestParam String password,
//            Model model,
//            HttpServletResponse response) throws UnsupportedEncodingException {
//
//        // Tentativa de login como Employee primeiro
//        User loggedUser = authenticationService.login(email, password, "EMPLOYEE");
//        String userType = "EMPLOYEE";
//
//
//        // Se não encontrou como Employee, tenta como Payroll
//        if (loggedUser == null) {
//            loggedUser = authenticationService.login(email, password, "PAYROLL");
//            userType = "PAYROLL";
//        }
//
//        if (loggedUser != null) {
//            CookieService.setCookie(response, "usuarioId", String.valueOf(loggedUser.getId()), 10000);
//            CookieService.setCookie(response, "userName", loggedUser.getName(), 10000);
//            CookieService.setCookie(response, "userType", userType, 10000);
//            CookieService.setCookie(response, "userEmail", loggedUser.getEmail(), 10000);
//
//            return switch (userType) {
//                case "EMPLOYEE" -> "redirect:/";
//                case "PAYROLL" -> "redirect:/";
//                default -> "redirect:/";
//            };
//        }
//
//        model.addAttribute("error", "Email ou senha inválidos");
//        return "login";
//    }
//
//
//
//}
