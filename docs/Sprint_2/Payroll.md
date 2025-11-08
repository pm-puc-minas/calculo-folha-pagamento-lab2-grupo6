## Documentação do Módulo de Relatório 

### Visão Geral

O Módulo de Relatório implementa a camada de **Apresentação** do demonstrativo de pagamento. O design é fundamentado no **Padrão Strategy** e no uso de **Interfaces**, o que garante a máxima flexibilidade e o **desacoplamento** entre a lógica de coordenação e a camada visual do sistema.

Esta arquitetura simplifica a manutenção, pois a alteração na aparência do relatório não exige modificação nas classes que orquestram a geração dos dados.

---

### 1. Estrutura de Classes e Responsabilidades

Abaixo estão as classes e interfaces principais, com suas responsabilidades e a aplicação dos conceitos de POO:

| Arquivo/Classe | Responsabilidade Principal | Conceitos Chave |
| :--- | :--- | :--- |
| **`IFolhaPagamentoDados`** | Define o **Contrato** para o acesso aos dados essenciais para o relatório (salário, descontos, etc.). | **Interface**: Garante que o relatório aceita dados de qualquer fonte que implemente esse contrato. |
| **`IFormatadorRelatorio`** | Define o **Contrato de Comportamento** para a apresentação do relatório através do método `formatar()`. | **Abstração**: Permite a substituição fácil e segura da estratégia de visualização. |
| **`RelatorioFolha`** | **Coordenador.** Orquestra o processo de geração do relatório. Recebe a implementação de `IFormatadorRelatorio` via construtor. | **Baixo Acoplamento**: Depende apenas de interfaces (Princípio da Inversão de Dependência). |
| **`FormatadorConsoleRetro`** | **Estratégia Concreta.** É a implementação de `IFormatadorRelatorio`. Sua função é construir a *string* de saída, formatar valores em R$, e aplicar o visual MS-DOS. | **Encapsulamento**: Isola a lógica complexa de apresentação. |
| **`DemonstracaoRelatorio`** | **Ponto de Execução (`main`).** Instancia o conjunto de dados, o `FormatadorConsoleRetro` e a classe `RelatorioFolha` para demonstrar o funcionamento integrado do módulo. | **Prova Funcional**: Ponto de entrada para a execução. |
| **`BigDecimal`** | Utilizado em todas as definições de valores monetários no módulo. | **Tipagem Segura**: Garante a precisão financeira, essencial para evitar erros de arredondamento em cálculos. |

---

### 2. Análise dos Conceitos de POO Aplicados

#### 2.1. Polimorfismo e Padrão Strategy

O design utiliza o **Padrão Strategy** para desacoplar o algoritmo (a formatação) do objeto principal (`RelatorioFolha`).

* A classe `RelatorioFolha` referencia o tipo **abstrato** `IFormatadorRelatorio`.
* Quando o método `gerarRelatorio()` é chamado, ele executa o método `formatar()` do objeto concreto que foi injetado (o `FormatadorConsoleRetro`).
* Este é um exemplo de **Polimorfismo Dinâmico**, que torna o módulo **aberto para extensão** (novos formatadores, como HTML) e **fechado para modificação** (a lógica central do `RelatorioFolha` permanece intacta).

#### 2.2. Inversão de Dependência

* O princípio de **Inversão de Dependência** é aplicado: as classes de alto nível (`RelatorioFolha`) não dependem de classes de baixo nível (`FormatadorConsoleRetro`). Ambas dependem de abstrações (as Interfaces).
* Isso resulta em um sistema com **Baixo Acoplamento**, facilitando a manutenção e a substituição de componentes.
