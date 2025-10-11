## Documentação das classes Calculators

### Visão Geral

O módulo calculator é a principal funcionalidade do sistema de folha de pagamento, responsável por isolar e gerenciar todas as regras de negócio relacionadas a cálculos (descontos, adicionais e salário líquido).

A arquitetura utiliza o padrão Strategy (Estratégia), garantindo que cada tipo de cálculo (INSS, IRRF, Periculosidade, etc.) seja uma classe independente e intercambiável, promovendo baixo acoplamento e alta coesão.

#### 2. Princípios de Orientação a Objetos Aplicados

A estrutura demonstra uma aplicação clara dos conceitos de POO, essenciais para a organização modular:

##### 2.1. Interfaces (ICalculatorInterface)

Função: Define o contrato obrigatório para qualquer classe que realize um cálculo.

Implementação: Todas as classes concretas de cálculo (Ex: CalculatorInss, CalculatorIrff) implementam esta interface.

Método Obrigatório: Possui o método calcular() (ou similar) que todas as classes devem implementar para retornar o valor do cálculo.

Benefício: Permite que o código de chamada manipule diferentes calculadoras de forma polimórfica, tratando-as genericamente como ICalculatorInterface.

##### 2.2. Classe Abstrata (CalculatorAbstract)

Função: Atua como a Superclasse para a maioria dos cálculos. Seu propósito é agrupar e fornecer os atributos comuns e funcionalidades básicas (ou parcialmente implementadas) necessárias para realizar os cálculos.

Atributos Comuns: Contém atributos essenciais que a maioria das subclasses precisará (ex: salário base, dependentes, alíquotas, etc.).

Benefício (Herança e Reuso): As classes concretas estendem (extends) CalculatorAbstract, reutilizando a definição de atributos e evitando a duplicação de código. Além disso, por ser abstrata, impede a sua própria instanciação.

##### 2.3. Classes Concretas (Estratégias de Cálculo)

Função: Implementam a lógica específica de cada cálculo.

Estrutura: Cada classe é responsável por uma única regra de cálculo.

Exemplos: CalculatorInss calcula apenas o INSS, CalculatorNetSalary calcula o salário líquido, etc.

Aplicação de Herança: Todas estendem (extends) CalculatorAbstract para acesso aos dados base.

Aplicação de Polimorfismo: Todas implementam a ICalculatorInterface, fornecendo sua versão específica do método calcular().

#### 3. Detalhamento dos Componente

| **Componente**               | **Tipo** | **Função**|**Conceitos Aplicados**|
|---------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **ICalculatorInterface** | Interface |Define o contrato de cálculo. | Interfaces, Polimorfismo. |
| **CalculatorAbstract**    | Classe Abstrata|Fornece atributos e estado comuns para todos os cálculos. |Classe Abstrata, Herança, Reuso. | 
| **GrauInsalubridade**     | Enumeração |Provavelmente armazena valores e alíquotas fixas de insalubridade. |Tipagem Segura. |
|Calculator... (Todas as outras) | Classes Concretas | Contêm a lógica de negócio específica. | Herança, Implementação de Interface, Polimorfismo.|
