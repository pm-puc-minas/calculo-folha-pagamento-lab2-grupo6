package io.github.progmodular.gestaorh.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResumo {
    private int totalFuncionarios;
    private int totalFolhasGeradas;
    private double totalPagoNoMes;
}
