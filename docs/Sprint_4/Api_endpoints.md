# 🌐 Referência da API (Endpoints)

Os Endpoints são o ponto de comunicação HTTP entre o Frontend e o Backend, definidos nos Controllers (rota base: porta 8080).

## 1. Gestão de Usuários (`UserController`)

Rota Base: **`/users`**

| Método | Rota Completa | Função Lógica |
| :--- | :--- | :--- |
| `POST` | `/users/login` | **Autenticação no Sistema.** |
| `POST` | `/users` | **Criação de Usuário.** Cria `Employee` ou `PayrollAdmin`. |
| `PATCH` | `/users/employee/{id}` | Atualiza dados específicos de um `Employee` (dias trabalhados, custos, etc.). |
| `GET` | `/users` | Lista todos os usuários. |
| `DELETE`| `/users/{id}` | Deleta um usuário. |

## 2. Execução da Folha de Pagamento (`PayrollController`)

Rota Base: **`/api/payroll`**

| Método | Rota Completa | Função Lógica |
| :--- | :--- | :--- |
| `POST` | `/api/payroll/calculate` | **Dispara o Cálculo Completo.** Aciona o módulo de cálculos e armazena o contracheque. |
| `GET` | `/api/payroll/employee/{employeeId}/history` | **Histórico de Contrasteques.** Busca holerites por funcionário (com filtros opcionais). |
| `PUT` | `/api/payroll/recalculate/payroll/{payrollId}/month/{month}/year/{year}` | **Recalcula um contracheque** específico. |

## 3. Relatórios Gerenciais (`PayrollReportController`)

Rota Base: **`/api/payroll/report`**

| Método | Rota Completa | Função Lógica |
| :--- | :--- | :--- |
| `GET` | `/api/payroll/report/{employeeId}` | Busca o relatório de folha de pagamento **mais recente**. |
| `POST` | `/api/payroll/report/{employeeId}` | Gera um novo relatório. |
