package io.github.progmodular.gestaorh.service;

import io.github.progmodular.gestaorh.model.entities.DashboardResumo;
import io.github.progmodular.gestaorh.repository.DashboardRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardResumo getResumoDashboard() {
        return dashboardRepository.getResumoDashboard();
    }
}
