Documentação da Classe Employee
Visão Geral

A classe Employee representa os funcionários administrativos do sistema e estende a classe abstrata User. Por meio da herança, herda todos os atributos e comportamentos comuns aos usuários, enquanto fornece implementações específicas para métodos abstratos e pode adicionar funcionalidades próprias, se necessário.

1. Detalhes de Implementação
Recurso	Descrição
Extensão da Superclasse	public class Employee extends User
A classe concreta Employee especializa o comportamento da superclasse abstrata User, herdando seus atributos e funcionalidades.
Anotações JPA	A classe herda as anotações JPA da superclasse. Não necessita repetir anotações como @Entity, pois são aplicadas na hierarquia da superclasse User.
Discriminador	Por conta da anotação @DiscriminatorColumn presente em User, cada instância da classe Employee será registrada com o valor "EMPLOYEE" na coluna USER_TYPE da tabela.
Implementação do Método Abstrato	Implementa o método getUserType() de forma específica:
@Override public String getUserType() { return "EMPLOYEE"; }
Garante o polimorfismo no uso da superclasse.
Atributos Específicos (opcional)	A classe pode conter atributos próprios dos funcionários administrativos, caso necessário (ex: cargo, setor, etc.). Atualmente, utiliza apenas os atributos herdados.
2. Aplicação Direta dos Conceitos de POO (Sprint 2)
2.1. Herança e Reuso

A classe Employee herda da superclasse User, reaproveitando todos os atributos mapeados e comportamentos comuns, como:

id, username, email, password (exemplos de atributos da superclasse)

Mapeamento JPA para persistência

Estratégia de herança SINGLE_TABLE definida na superclasse

2.2. Polimorfismo

A implementação do método getUserType() em Employee permite ao sistema invocar esse comportamento de forma polimórfica. Mesmo quando referenciado como User, um objeto Employee responderá com seu tipo específico.

User user = new Employee();
System.out.println(user.getUserType()); // Saída: EMPLOYEE


Esse comportamento é essencial para diferenciar os tipos de usuários na camada de lógica de negócios, segurança ou apresentação.

2.3. Responsabilidade Específica

A classe Employee é responsável por representar os usuários com função administrativa. Mesmo que atualmente não tenha atributos adicionais, ela serve como uma especialização clara e futura extensão da lógica de negócios para esse tipo de usuário.

3. Considerações Futuras

Se necessário, novos atributos ou métodos específicos de Employee poderão ser adicionados (ex: cargo, área administrativa, etc.).

Pode-se aplicar validações ou regras específicas nessa subclasse para diferenciar ainda mais sua lógica de negócio em relação a outros tipos de usuário.
