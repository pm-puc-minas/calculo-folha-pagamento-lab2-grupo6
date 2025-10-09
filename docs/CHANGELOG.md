## Alterações recentes

Data: 2025-10-09

### Adições
- Entidade `Funcionario` criada estendendo `User`.
  - Arquivo: `src/main/java/io/github/progmodular/gestaorh/model/entities/Funcionario.java`
  - Mapeada com `@Entity` e `@Table(name = "funcionario")`.
  - Implementa `getUserType()` retornando `"FUNCIONARIO"`.

- Repositório `FuncionarioRepository` para CRUD via Spring Data JPA.
  - Arquivo: `src/main/java/io/github/progmodular/gestaorh/repository/FuncionarioRepository.java`
  - Métodos utilitários: `existsByEmail`, `findByEmail`.

- Demo no startup (CommandLineRunner) para criar e listar funcionários.
  - Arquivo: `src/main/java/io/github/progmodular/gestaorh/GestaoRhApplication.java`
  - Cria (se não existirem) `Ana Souza` e `João Lima`, imprime contagem e lista no console.

### Correções/Ajustes
- `GestaoRhApplication`:
  - Removida `@Transactional` desnecessária no `CommandLineRunner`.
  - Removido aviso de parâmetro de lambda não usado.
  - Adicionados logs claros de início/fim do demo.

- `PayrollAdmin`:
  - Substituída anotação inválida `@NotEmpty` por `@NotNull` no campo `Boolean isAdmin`.

- `Employee`:
  - Removidos imports Lombok não utilizados (limpeza de warnings).

### Como rodar
1) Certifique-se que o MySQL está ativo e acessível conforme `src/main/resources/application.properties`:
   - `spring.datasource.url=jdbc:mysql://localhost:3306/apprh?createDatabaseIfNotExist=true`
   - `spring.datasource.username=root`
   - `spring.datasource.password=...`
   - `spring.jpa.hibernate.ddl-auto=update`

2) Execute a aplicação:
   - IDE: rodar a classe `GestaoRhApplication`.
   - Terminal (Windows/PowerShell): `./mvnw spring-boot:run` ou `mvn spring-boot:run`.

3) Saída esperada no console:
```
==== DEMO FUNCIONARIO START ====
Criado: Ana Souza <ana@example.com>        # apenas na primeira execução
Criado: João Lima <joao@example.com>       # apenas na primeira execução
Total de Funcionarios: X
<id> - <nome> (FUNCIONARIO)
Busca por email (ana@example.com): id=<id>, nome=Ana Souza
==== DEMO FUNCIONARIO END ====
```

### Como testar `Funcionario`
- Automático no startup (CommandLineRunner) conforme acima.
- Opcional (JUnit): criar teste injetando `FuncionarioRepository`, salvar um registro e consultar por email.

### Arquivos impactados
- `src/main/java/io/github/progmodular/gestaorh/model/entities/Funcionario.java` (novo)
- `src/main/java/io/github/progmodular/gestaorh/repository/FuncionarioRepository.java` (novo)
- `src/main/java/io/github/progmodular/gestaorh/GestaoRhApplication.java` (editado)
- `src/main/java/io/github/progmodular/gestaorh/model/entities/PayrollAdmin.java` (editado)
- `src/main/java/io/github/progmodular/gestaorh/model/entities/Employee.java` (editado)

### Observações
- `User` é `@MappedSuperclass`; as colunas herdadas são materializadas na tabela `funcionario`.
- O demo evita duplicidade usando `existsByEmail` antes de inserir.


