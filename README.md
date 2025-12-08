## Sistema de Gestão de RH

Trabalho final da disciplina Programação Modular – PUC Minas
Professor: Paulo Henrique D. S. Coelho

## Documentação extra

[Documentação Completa](docs/README.md) <br>

[Demonstração da aplicação Completa](docs/Sprint_4/demo-app)

[Padrão de Projeto Factory](docs/Sprint_4/padraoProjeto.md)

## Sobre o projeto

O Sistema de Gestão de Recursos Humanos (RH) é uma solução abrangente para automatizar e otimizar processos-chave do departamento de pessoal de uma empresa. Inspirado em sistemas líderes de mercado como o da TOTVS, este projeto foca em funcionalidades essenciais como folha de pagamento, controle de ponto eletrônico, e gestão de dados de funcionários.

O objetivo é criar uma ferramenta robusta que:

Automatize o cálculo salarial, considerando descontos, benefícios e horas extras.

Ofereça um portal para que funcionários consultem contracheques.

O desenvolvimento segue as melhores práticas de qualidade de software, incluindo Programação Orientada a Objetos (POO), aplicação dos princípios SOLID e a criação de uma suíte completa de testes unitários para garantir a confiabilidade e a integridade dos dados.

## Tecnologias Utilizadas

**Java**: Linguagem de programação base do projeto.

**Spring Boot**: Framework para o desenvolvimento da aplicação.

**JUnit**: Ferramenta para testes unitários.

**Maven**: Gerenciador de dependências e automação de build.

**Banco de Dados**: Postgres

**Frontend**: React.

**Teste de usuário**: Selenium

 ## Como Rodar a Aplicação

Este projeto é composto por Frontend (React + TypeScript), Backend (Java + Spring Boot) e Banco de Dados PostgreSQL via Docker.
Siga o passo a passo abaixo para configurar e executar todo o ambiente.

**Pré-requisitos**

Certifique-se de ter instalado:

VS Code – para abrir e rodar o frontend

IntelliJ IDEA – para rodar o backend Java (JDK 21)

JDK 21 – backend em Java 21

Node.js – recomendado: v22 (v21 LTS também funciona)

Docker Desktop – para subir o PostgreSQL via docker-compose

DBeaver – para visualizar e manipular o banco PostgreSQL

Git – para clonar o repositório

**1. Clone o repositório**

Escolha uma pasta no seu computador e execute:
````
git clone https://github.com/.../calculo-folha-pagamento-lab2-grupo6.git
````

**2. Rodando o Frontend (React + TypeScript)**

Navegue até o diretório do frontend:

````
cd calculo-folha-pagamento-lab2-grupo6/front/gestao-front/gestao-front
````

Abra no VS Code:

````
code .
````

Instale as dependências:

````
npm install
````

Scripts disponíveis:

<img width="325" height="99" alt="image" src="https://github.com/user-attachments/assets/40883f34-9f36-4fdb-b733-032fed73fac5" />

````
npm start
npm run build
npm test
````

**Para iniciar a aplicação:**

````
npm start
````

**O frontend rodará em:**
````
http://localhost:3000
````

**3. Rodando o Backend (Java + Spring Boot)**

Abra o projeto no IntelliJ IDEA
Diretório:

````
calculo-folha-pagamento-lab2-grupo6/
````

Certifique-se de que está usando:

JDK 21

Maven (sincronize o pom.xml)

Abra a classe Application e clique em Run ▶️

**O backend rodará em:**
````
http://localhost:8080
````


**4. Subindo o Banco de Dados (Docker)**

No diretório raiz do backend, execute:

````
cd calculo-folha-pagamento-lab2-grupo6
docker-compose up -d
````

**Isso iniciará:**

PostgreSQL

**Agora o banco e o backend estão funcionando.**

** 5. Configurando o banco no DBeaver**

Conecte-se ao banco com:

Host: localhost

Porta: 5432

Banco: gestao_database

Usuário: admin

Senha: admin

Driver: PostgreSQL

URL JDBC:

jdbc:postgresql://localhost:5432/gestao_database

** 6. Criando o usuário administrador inicial**

Execute os seguintes scripts SQL abaixo no DBeaver para criar o admin master:

````
CREATE EXTENSION IF NOT EXISTS pgcrypto;
````

````
INSERT INTO users (
    user_type, 
    is_admin, 
    name, 
    email, 
    password,
    creation_date_time, 
    last_date_time      
) VALUES (
    'PAYROLL_ADMIN', 
    true, 
    'admin', 
    'admin@gmail.com', 
    crypt('123', gen_salt('bf')), 
    NOW(),              
    NOW()             
);
````


➡️ Após isso, você poderá logar no sistema com:

Email: admin@gmail.com
Senha: 123

E então criar novos:
````
PAYROLL_ADMIN
EMPLOYEE
````


**Agora o projeto completo estará rodando:**

Serviço	Porta

Frontend	3000

Backend	8080

PostgreSQL	5432
