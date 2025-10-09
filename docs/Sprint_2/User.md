## Documentação da Classe User - Módulo de Entidades

### Visão Geral

A classe User é uma Entidade fundamental no sistema de Gestão de RH (gestaorh). Ela representa os usuários básicos da aplicação, como colaboradores, administradores ou gerentes, e é mapeada diretamente para uma tabela no banco de dados, utilizando o framework Spring Data JPA (evidenciado pelas anotações @Entity, @Id, etc.).

É uma classe simples de modelo (POJO - Plain Old Java Object) que armazena informações essenciais para autenticação e identificação: id, name, email e password.

#### 1. Detalhes de Implementação

|Recurso |Descrição|
|Pacote | io.github.progmodular.gestaorh.model.entities|
|Anotações JPA | @Entity: Marca a classe como uma entidade persistente. @Id e @GeneratedValue: Define o campo id como chave primária com geração automática (estratégia IDENTITY) |
| Anotações Lombok | @NoArgsConstructor, @AllArgsConstructor: Geram construtores sem argumentos e com todos os argumentos, simplificando o código. @Getter, @Setter: Geram os métodos getters e setters para os atributos, conforme a necessidade.|
|Validação|@NotEmpty: Garante que os campos name, email e password não sejam nulos e não estejam vazios, aplicando validação básica do Bean Validation.|

