# FolhaPagamento - Documentação

## O que é

A classe `FolhaPagamento` é responsável por calcular o salário dos funcionários. Ela pega o salário base e calcula todos os valores que o funcionário vai receber e o que vai ser descontado.

## Como funciona

### 1. Dados básicos
- Salário base do funcionário
- Mês e ano da folha
- Número de dependentes

### 2. O que é calculado

**Valores que o funcionário recebe:**
- Salário bruto
- Adicional de periculosidade (30% do salário)
- Adicional de insalubridade (10%, 20% ou 40%)
- Vale alimentação (R$ 25,00 por dia)

**Valores que são descontados:**
- INSS (previdência) - varia de 7,5% a 14%
- IRRF (imposto de renda) - depende do salário
- Vale transporte (máximo 6% do salário)

### 3. Resultado final
- Total de proventos (tudo que recebe)
- Total de descontos 
- Salário líquido (o que vai para a conta)

## Como usar

```java
// Criar uma folha
FolhaPagamento folha = new FolhaPagamento();
folha.setFuncionario(funcionario);
folha.setSalarioBase(new BigDecimal("3000.00"));
folha.setMesReferencia(10);
folha.setAnoReferencia(2025);

// Fazer os cálculos
CalculadoraFolha calculadora = new CalculadoraFolha(3000.0, 220);
folha.processarFolha(calculadora);

// Ver o resultado
System.out.println("Salário líquido: " + folha.getSalarioLiquido());
```

## APIs disponíveis

### Processar folha de um funcionário
```
POST /api/folha-pagamento/processar-funcionario
```

### Ver folha de um funcionário
```
GET /api/folha-pagamento/funcionario/{id}/periodo?mes=10&ano=2025
```

### Ver histórico de folhas
```
GET /api/folha-pagamento/funcionario/{id}/historico
```

## Integração com outras classes

- **Funcionario**: Quem vai receber o salário
- **CalculadoraFolha**: Faz os cálculos dos valores
- **FolhaPagamentoService**: Gerencia as operações
- **FolhaPagamentoRepository**: Salva no banco de dados

## Status da folha

- `PROCESSANDO`: Está calculando
- `PROCESSADA`: Cálculos prontos  
- `APROVADA`: RH aprovou
- `PAGA`: Salário foi pago
- `CANCELADA`: Folha cancelada

## Exemplo de resumo gerado

```
=== FOLHA DE PAGAMENTO ===
Funcionário: João Silva
Período: 10/2025

--- PROVENTOS ---
Salário Base: R$ 3000.00
Vale Alimentação: R$ 550.00
TOTAL PROVENTOS: R$ 3550.00

--- DESCONTOS ---
INSS: R$ 330.00
IRRF: R$ 150.00
TOTAL DESCONTOS: R$ 480.00

--- LÍQUIDO ---
SALÁRIO LÍQUIDO: R$ 3070.00
Status: PROCESSADA
```
