package io.github.progmodular.gestaorh.controller;

import org.springframework.stereotype.Controller;
import io.github.progmodular.gestaorh.service.DashboardService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("info", dashboardService.getResumoDashboard());
        return "dashboard";
    }

}
