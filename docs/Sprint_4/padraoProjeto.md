# Refatoração com Padrão de Projeto
## Padrão Escolhido: Padrão Factory (ou Factory Method)

### O que é a refatoração?
A Refatoração é o processo de reestruturar um código de software existente sem alterar seu comportamento externo (funcionalidade). Seu principal objetivo é melhorar a legibilidade, manutenibilidade e extensibilidade do código, tornando-o mais limpo, coeso e flexível, geralmente aplicando princípios de design, como o SOLID.

### Objetivo da refatoração no contexto da folha de pagamento
No sistema de folha de pagamento, onde existem diversas regras e cálculos complexos (INSS, IRRF, adicionais), o objetivo da refatoração com o Padrão Factory é:
1.	Desacoplamento: Separar o código cliente (o módulo que usa os cálculos) da lógica de criação das classes de cálculo (INSS, Periculosidade, etc.).
2.	Flexibilidade e Extensibilidade (OCP): Facilitar a adição de um novo tipo de cálculo (ex: um novo imposto ou um novo adicional) sem precisar modificar o código que faz a chamada dos cálculos. A complexidade de instanciar a classe correta fica encapsulada na Factory.
3.	Organização: Centralizar a lógica de criação em uma única classe (CalculatorFactory), garantindo um fluxo de programa mais limpo e melhor controle sobre as dependências.
      Passo a passo para implementar o padrão escolhido no sistema
      A implementação se concentra em criar uma interface comum para todos os cálculos e delegar a responsabilidade de criação para a classe Factory.
      Estrutura Necessária:
1.	Interface Comum (Calculator): Define o contrato (método) que todas as classes de cálculo devem seguir.
2.	Classes Concretas (INSSCalculator, etc.): Implementam a interface Calculator, contendo a lógica específica para cada cálculo.
3.	Classe Factory (CalculatorFactory): Centraliza a lógica de criação e retorna as instâncias das classes concretas conforme a necessidade.
      Sequência de Implementação:
1.	Definir a Interface/Classe Abstrata: Criar a interface ICalculator com o método, por exemplo, double calcular(Salario salarioBase).
2.	Isolar as Classes Concretas: Garantir que cada cálculo (INSS, Periculosidade) seja uma classe separada que implemente ICalculator.
3.	Criar a Factory: Implementar a classe CalculatorFactory com um método (pode ser estático, como um Simple Factory, ou um método de instância, como um Factory Method simplificado) que recebe um parâmetro de tipo (ex: String tipoCalculo) e decide qual classe concreta instanciar e retornar.
      o	Exemplo de método na Factory: ICalculator getCalculator(TipoCalculo tipo).
4.	Refatorar o Código Cliente: Substituir todas as chamadas de instâncias diretas (new INSSCalculator()) no código principal pela chamada à Factory (factory.getCalculator("INSS")).

### Qual(is) princípio(s) SOLID são atendidos com essa refatoração

O **Padrão Factory** é fundamental para atender a dois princípios do SOLID:


| **Princípio**               | **Atendimento com o Padrão Factory**                                                                                                                                                                                   | 
|---------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| SRP (Single Responsibility Principle) | A responsabilidade de Cálculo é separada da responsabilidade de Criação (instanciação). <br> Cada classe de cálculo cuida apenas da sua lógica, e a Factory cuida apenas de instanciar a classe correta.               |  
|OCP (Open/Closed Principle)| O código fica aberto para extensão (adicionar um novo cálculo é fácil: basta criar a nova classe e alterar apenas a Factory) e fechado para modificação (o código cliente que usa a Factory não precisa ser alterado). |



**Exemplo prático (Em Java)**

Abaixo está um esboço em Java ilustrar o uso da CalculatorFactory.
Java
public class CalculatorFactory {

    public List<ICalculatorInterface> createAllCalculators(Employee employee) {
        return List.of(
                new CalculatorDanger(employee),
                new CalculatorFgts(employee),
                new CalculatorHoursSalary(employee),
                new CalculatorInss(employee),
                new CalculatorIrrf(employee),
                new CalculatorNetSalary(employee),
                new CalculatorUnhealthiness(employee),
                new CalculatorDiscountValueTransport(employee)
        );
    }

    public ICalculatorInterface createCalculator(String type, Employee employee) {
            return switch (type) {
                case "DANGER" -> new CalculatorDanger(employee);
                case "FGTS" -> new CalculatorFgts(employee);
                case "HOURS_SALARY" -> new CalculatorHoursSalary(employee);
                case "INSS" -> new CalculatorInss(employee);
                case "IRRF" -> new CalculatorIrrf(employee);
                case "NET_SALARY" -> new CalculatorNetSalary(employee);
                case "UNHEALTHY" -> new CalculatorUnhealthiness(employee);
                case "VALUE_TRANSPORT" -> new CalculatorDiscountValueTransport(employee);
                default -> throw new IllegalStateException("Unexpected value: " + type);

            };
    }
}
