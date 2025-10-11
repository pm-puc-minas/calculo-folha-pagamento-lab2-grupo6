## Documentação de testes implementados

Como referenciado anteriormente na [Sprint 1 (testes)](../Sprint_1/testes.md), os testes planejados foram focados na parte mais crítica do sistema, os cálculos salariais, garantindo a precisão e a confiabilidade de todas as parcelas da folha de pagamento.

Os testes foram escritos utilizando o framework JUnit (ou similar) e focam na validação das regras de negócio de cada classe que implementa a ICalculatorInterface.

### Estratégia de Teste

A arquitetura modular do sistema (baseada no padrão Strategy) permite que cada classe de cálculo seja testada isoladamente, garantindo que:

* Regras de Negócio: Cada classe (CalculatorInss, CalculatorIrff, etc.) aplica a fórmula correta e as alíquotas vigentes.

* Valores Limites (Boundary Testing): Os testes verificam a transição entre as faixas de cálculo (ex: alíquotas progressivas do IRRF e INSS), garantindo que os valores nas fronteiras sejam aplicados corretamente.

* Resultados Concretos: Para cada cenário de entrada (salário base, descontos, adicionais), o resultado do método calcular() é comparado com um valor esperado e pré-calculado manualmente.

### Test result

<img width="363" height="311" alt="image" src="https://github.com/user-attachments/assets/0df0bdea-efdc-400d-914d-40edcff437e9" />
