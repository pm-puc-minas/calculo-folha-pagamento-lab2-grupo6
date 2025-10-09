## Documentação da Classe User - Módulo de Entidades

### Visão Geral

A classe User é uma Entidade fundamental no sistema de Gestão de RH (gestaorh). Ela representa os usuários básicos da aplicação, como colaboradores, administradores ou gerentes, e é mapeada diretamente para uma tabela no banco de dados, utilizando o framework Spring Data JPA (evidenciado pelas anotações @Entity, @Id, etc.).

É uma classe simples de modelo (POJO - Plain Old Java Object) que armazena informações essenciais para autenticação e identificação: id, name, email e password.

#### 1. Detalhes de Implementação

| **Recurso**               | **Descrição**                                                                                                                                                                                                                                                                                                                                                                                 |
|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Modificador**           | **abstract**: Impede a instanciação direta da classe, reforçando que ela serve apenas como um modelo base.                                                                                                                                                                                                                                                                                    |
| **Anotações JPA**         | **@Entity**: Marca a classe como uma entidade persistente. <br/>**@Id** e **@GeneratedValue**: Define o campo id como chave primária com geração automática.                                                                                                                                                                                                                                  |
| **Estratégia de Herança** | **@Inheritance**(strategy = InheritanceType.SINGLE_TABLE): Define que todas as subclasses (**Employee**, **PayrollAdmin**) serão mapeadas para uma única tabela no banco de dados. Justificativa: Conforme sua premissa, esta é uma abordagem válida para o escopo do projeto, simplificando consultas e gerenciamento, especialmente se os subtipos não tiverem muitos atributos exclusivos. |
|**Coluna Discriminadora**| **@DiscriminatorColumn**(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING): Adiciona uma coluna (USER_TYPE) na tabela única para identificar qual o tipo concreto de usuário está salvo em cada linha (Ex: 'EMPLOYEE', 'ADMIN'). Essencial para a estratégia SINGLE_TABLE.                                                                                                        |
| **Método Abstrato** | **public abstract String getUserType();**: Obriga todas as subclasses a fornecerem uma implementação concreta (Ex: retornar "Employee" ou "PayrollAdmin"), garantindo o Polimorfismo. |

#### 2. Aplicação Direta dos Conceitos de POO (Sprint 2)

##### 2.1. Herança e Reuso

A classe **User**  é a **Superclasse** que define o contrato básico de identidade e autenticação para os usuários sistema.

**Subclasses** (Ex: Employee, PayrollAdmin) deverão estender User, herdando e reusando todos os atributos anotados.

Ex: public class Employee extends User { ... }

##### 2.2. Polimorfismo

Polimorfismo Comportamental: A declaração do método getUserType() como abstract exige que cada subclasse implemente sua própria lógica. Quando esse método for chamado em um objeto do tipo User, o sistema executará automaticamente a versão específica da subclasse (Polimorfismo Dinâmico ou Sobrescrita).

##### 2.3. Interfaces 

**Interfaces:** Embora a entidade User não implemente interfaces diretamente, o conceito de Interfaces será aplicado em outras camadas do projeto.

**Planejamento:** O uso principal de interfaces será na camada de Repository (e/ou Service), onde as interfaces do Spring Data JPA ou interfaces de contrato definidas pelo grupo garantirão o baixo acoplamento e o polimorfismo na manipulação de dados dos usuários.