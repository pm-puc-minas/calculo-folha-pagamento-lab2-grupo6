# Lógica Funcional Geral

Este documento resume a arquitetura de software, o fluxo funcional e a aplicação dos padrões de projeto que governam o **Sistema de Gestão de RH**.

---

## 1. Fluxo de Trabalho e Modelagem de Usuários

O sistema é centrado em três perfis de usuário, definidos pela **Herança** da classe abstrata `User`, que guiam todo o fluxo de trabalho da folha de pagamento.

| Perfil | Tipo de Usuário | Funções Chave | Endpoint de Acesso |
| :--- | :--- | :--- | :--- |
| **Administrador** | `PAYROLL_ADMIN`| Cria o primeiro `PAYROLL_ADMIN` (RH) via script 
e gerencia configurações globais. | `POST /users/login` |
| **Funcionário** | `EMPLOYEE` | **Consulta** seu próprio contracheque e histórico de pagamentos. | `POST /users/login` |

O fluxo inicia com a **rota direta para o login**, onde o sistema valida o perfil para direcionar o usuário para o painel de funções correto. O **Admin** é criado via script SQL de `INSERT` inicial no banco.

---

## 2. O Coração da Lógica: Módulo de Processamento Salarial

O cálculo da folha de pagamento é a funcionalidade mais crítica do sistema e é gerenciada pelo **módulo `Calculators`**.

### 2.1. Arquitetura de Cálculos (Padrões de Projeto)

O design deste módulo segue rigorosos padrões de projeto para garantir que o código seja **extensível, manutenível e desacoplado**:

* **Padrão Strategy:** O módulo implementa o Strategy Pattern, onde cada componente do salário (INSS, IRRF, FGTS, Adicionais) é uma **estratégia isolada**. Todos implementam a mesma interface (`ICalculatorInterface`), permitindo que a orquestração do serviço trate todos os cálculos de forma **polimórfica**. 
* **Padrão Factory:** A responsabilidade de **criação e instanciação** de todas essas estratégias é delegada a uma classe **`CalculatorFactory`**. Isso garante que o módulo de alto nível (o serviço que executa a folha) não dependa de classes concretas, atendendo aos princípios **SRP (Single Responsibility Principle)** e **OCP (Open/Closed Principle)**.

### 2.2. O Ciclo de Execução e Persistência

1.  **Início:** O `PAYROLL_ADMIN` envia o comando de cálculo via `POST /api/payroll/calculate`.
2.  **Criação:** A `CalculatorFactory` cria a lista completa de objetos `ICalculatorInterface` necessária para o processamento.
3.  **Execução:** O sistema itera sobre essa lista, executando o método `calcular()` em cada objeto.
4.  **Saída:** O resultado final é o salário líquido e todos os descontos/adicionais, que são armazenados como um registro na entidade **`Payroll`** (o Contracheque).

---

## 3. Qualidade, Testes e Segurança

### 3.1. Prova de Qualidade (JUnit)

A estabilidade e precisão dos resultados são garantidas por uma suíte completa de testes de unidade focada no módulo `Calculators`.

* **Validação da Precisão:** Os testes verificam a aplicação correta das fórmulas, incluindo a gestão de **valores limites (Boundary Testing)** e a robustez contra **valores nulos e negativos**.
* **Cobertura:** Todos os cálculos, como `Teste CalculatorFgts`, `Teste CalculatorInss`, `Teste CalculatorIrff`, e `Teste CalculatorNetSalary`, foram validados com sucesso.



### 3.2. Referência da API (Comunicação)

O Frontend (React) se comunica com o Backend (Spring Boot) através de Controllers dedicados, que são a camada de acesso à lógica:

* **`UserController`:** Gerencia login (`/users/login`) e criação/gestão de perfis.
* **`PayrollController`:** Contém a rota crítica de processamento (`/api/payroll/calculate`) e histórico.

Detalhamento de outras rotas está no documento **`Api_endpoints.md`**.
