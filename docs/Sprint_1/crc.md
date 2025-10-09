# Funcionario

| Responsabilidades                                                             | Colaborações                     |
| ----------------------------------------------------------------------------- | -------------------------------- |
| Armazenar dados do colaborador (nome, CPF, cargo, salário, dependentes, etc.) | FolhaPagamento, CalculadoraFolha |

# Folha de Pagamento

| Responsabilidades                                             | Colaborações                                    |
| ------------------------------------------------------------- | ----------------------------------------------- |
| Centralizar cálculos (proventos, descontos e salário líquido) | Funcionario , CalculadoraFolha , RelatorioFolha |
| Gerenciar dados da folha                                      |                                                 |

# Calculadora Folha

| Responsabilidades                                   | Colaborações                |
| --------------------------------------------------- | --------------------------- |
| Calcular salário hora                               | Funcionario, FolhaPagamento |
| Calcular adicionais (periculosidade, insalubridade) |                             |
| Calcular benefícios (VT, VA)                        |                             |
| Calcular descontos (INSS, FGTS, IRRF)               |                             |
| Calcular salário líquido                            |                             |

# RelatorioFolha

| Responsabilidades                     | Colaborações                |
| ------------------------------------- | --------------------------- |
| Gerar relatório da folha de pagamento | Funcionario, FolhaPagamento |
| Exibir os cálculos de forma detalhada |                             |

# Usuario

| Responsabilidades                                   | Colaborações   |
| --------------------------------------------------- | -------------- |
| Autenticar acesso ao sistema                        | FolhaPagamento |
| Permitir somente usuários logados acessarem a folha |                |
