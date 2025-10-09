## Documentação da Classe 

### Visão Geral

A classe PayrollAdmin é uma subclasse concreta que estende a classe abstrata User. Ela representa o usuário do sistema com permissões de administração e gerenciamento de folha de pagamento.

A criação desta classe é a implementação direta dos conceitos de Herança e Polimorfismo, garantindo que apenas tipos específicos e funcionais de usuários sejam instanciados.


#### 1. Detalhes de Implementação

| **Recurso**               | **Aplicação em PayrollAdmin**                                                                                                                                                                                                                                                                                                                                                                                 |
|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Herança**               | PayrollAdmin extends User: Herda automaticamente os atributos básicos (id, name, email, password) e os comportamentos definidos na classe base.                                                                                                                                                                                                                                                                                    |
| **Polimorfismo**          | Sobrescrita do Método **getUserType()**: Implementa o método abstrato herdado de User, retornando o tipo específico "PAYROLL". Isso permite que o sistema trate um objeto como User, mas invoque o comportamento específico de PayrollAdmin em tempo de execução.                                                                                                                              |
| **Classes Abstratas**     | Cumpre a regra da User abstrata, fornecendo uma implementação concreta que pode ser instanciada. |

#### 2. Detalhes de Implementação 

| **Recurso**       | **Descrição**                                                                                                                                            |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Superclasse**   | PayrollAdmin extends User: Herda automaticamente os atributos básicos (id, name, email, password) e os comportamentos definidos na classe base.          |
| **Anotação JPA**  | @Entity: Marca a classe para persistência.                                                                                                               |
| **Discriminador** | @DiscriminatorValue("payroll_user"): Define o valor que será registrado na coluna USER_TYPE da tabela única, distinguindo-o de outros subtipos de User.  |
| **isAdmin** | Atributo específico do administrador, inicializado como true. | 
| **Método getUserType()** | Implementa o contrato da superclasse, sendo crucial para a lógica polimórfica de identificação do usuário.|
