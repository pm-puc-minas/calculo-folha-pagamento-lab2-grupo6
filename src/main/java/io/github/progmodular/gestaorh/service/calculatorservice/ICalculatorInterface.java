package io.github.progmodular.gestaorh.service.calculatorservice;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface ICalculatorInterface {
     BigDecimal calculator();
    String getCalculationType();
}
