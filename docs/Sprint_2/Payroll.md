## Documentação do Módulo de Payroll 

### Visão Geral

Entidade JPA responsável por representar a folha de pagamento mensal dos funcionários. Armazena todos os dados de cálculos salariais, descontos e benefícios

### Componentes Salariais

#### Benefícios:

**dangerAllowance** - Adicional de periculosidade

**unhealthyAllowance** - Adicional de insalubridade

#### Descontos:

**inssDiscount** - Desconto INSS

**irrfDiscount** - Imposto de Renda

**valueTransportDiscount** - Vale-transporte

**fgts** - Depósito FGTS

#### Relacionamentos
**Many-to-One** com Employee

**Múltiplas folhas por funcionário**

**Chave estrangeira:** employee_id

#### Aplicação
Geração de folha de pagamento

Cálculo de custos