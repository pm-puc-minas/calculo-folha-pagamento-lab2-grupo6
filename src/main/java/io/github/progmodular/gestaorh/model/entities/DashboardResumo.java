package io.github.progmodular.gestaorh.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResumo {
    private int totalFuncionarios;
    private int totalFolhasGeradas;
    private BigDecimal totalPagoNoMes;
}
