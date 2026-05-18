# ☕ IF E-Retail — Spring Boot

API REST de e-commerce desenvolvida em **Java 17** com **Spring Boot 4**, **Spring Data JPA**, **Hibernate** e **PostgreSQL**. Projeto acadêmico do curso de Sistemas de Informação — **IFPR Palmas**. Esta é a evolução da [versão PHP](https://github.com/LuizFKM/if-e-retail-php), reescrita com uma arquitetura de camadas (Controller → Service → Repository) e empacotada como WAR para deploy em servidor externo.

---

## 📋 Sobre o Projeto

O IF E-Retail Spring expõe uma **API REST** completa para gestão de uma loja virtual. Cobre CRUD de admins, clientes, produtos e pedidos, com funcionalidades como busca por CPF, busca parcial por nome, listagem de produtos sem estoque, carrinho de compras e lista de favoritos por cliente.

---

## 🚀 Tecnologias e Dependências

| Tecnologia | Versão | Finalidade |
|---|---|---|
| [Java](https://www.oracle.com/java/) | 17 | Linguagem principal |
| [Spring Boot](https://spring.io/projects/spring-boot) | 4.0.3 | Framework principal (web, JPA, testes) |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa) | via Boot | Repositórios com queries geradas por convenção |
| [Hibernate](https://hibernate.org/) | via Boot | ORM — implementação JPA |
| [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html) | via Boot (`spring-boot-starter-webmvc`) | Camada web REST |
| [PostgreSQL](https://www.postgresql.org/) | — | Banco de dados relacional |
| [Lombok](https://projectlombok.org/) | via Boot | Geração de getters, setters e construtores em tempo de compilação |
| [MapStruct](https://mapstruct.org/) | 1.6.3 | Mapeamento entre objetos (DTOs) |
| [Spring DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools) | via Boot | Hot reload em desenvolvimento |
| [JUnit 5](https://junit.org/junit5/) | via Boot | Testes automatizados |
| [Maven](https://maven.apache.org/) | — | Build e gerenciamento de dependências |

> O projeto é empacotado como **WAR** (`<packaging>war</packaging>`), o que permite deploy tanto pelo `spring-boot:run` quanto em servidores externos como Tomcat ou WildFly via `ServletInitializer`.

---

## 📁 Estrutura do Projeto

```
if-retail-spring/
├── .mvn/wrapper/               # Maven Wrapper — garante a versão correta do Maven
├── src/
│   ├── main/
│   │   ├── java/br/edu/ifpr/bsi/ifretailspring/
│   │   │   ├── IfRetailSpringApplication.java   # Ponto de entrada (@SpringBootApplication)
│   │   │   ├── ServletInitializer.java          # Suporte a deploy WAR em servidor externo
│   │   │   ├── controllers/                     # Camada REST — recebe e responde requisições HTTP
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── ClienteController.java
│   │   │   │   ├── PedidoController.java
│   │   │   │   └── ProdutoController.java
│   │   │   ├── domain/                          # Entidades de domínio e modelagem
│   │   │   │   ├── GenericDomain.java           # Superclasse abstrata com ID auto-gerado
│   │   │   │   ├── User.java                    # Entidade base (JOINED Inheritance)
│   │   │   │   ├── admin/Admin.java
│   │   │   │   ├── cliente/Cliente.java
│   │   │   │   ├── carrinho/Carrinho.java
│   │   │   │   ├── contato/Contato.java
│   │   │   │   ├── endereco/Endereco.java
│   │   │   │   ├── pedido/
│   │   │   │   │   ├── Pedido.java
│   │   │   │   │   └── ItemPedido.java
│   │   │   │   ├── produto/Produto.java
│   │   │   │   ├── enums/UserType.java          # Enum: CLIENTE | ADMIN
│   │   │   │   └── factory/UserFactory.java     # Factory para criação de usuários
│   │   │   ├── repository/                      # Spring Data JPA — acesso ao banco
│   │   │   │   ├── AdminRepository.java
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── PedidoRepository.java
│   │   │   │   └── ProdutoRepository.java
│   │   │   └── services/                        # Lógica de negócio e validações
│   │   │       ├── AdminService.java
│   │   │       ├── ClienteService.java
│   │   │       ├── PedidoService.java
│   │   │       └── ProdutoService.java
│   │   └── resources/
│   │       └── application.properties           # Configurações do Spring Boot
│   └── test/
│       └── java/br/edu/ifpr/bsi/ifretailspring/
│           ├── IfRetailSpringApplicationTests.java
│           └── repository/                      # Testes de integração com banco real
│               ├── AdminRepositoryTest.java
│               ├── ClienteRepositoryTest.java
│               ├── PedidoRepositoryTest.java
│               └── ProdutoRepositoryTest.java
├── mvnw / mvnw.cmd             # Scripts Maven Wrapper (Linux/Mac e Windows)
└── pom.xml                     # Dependências e configuração de build
```

**Por que essa estrutura:**

- **`controllers/`** — só lida com HTTP (receber request, chamar service, devolver response). Nenhuma regra de negócio aqui.
- **`services/`** — toda a lógica de negócio, validações e tratamento de erro (`ResponseStatusException`). Controllers não sabem como o dado é obtido.
- **`repository/`** — interfaces Spring Data JPA; o Spring gera a implementação automaticamente em tempo de execução.
- **`domain/`** — entidades puras, sem dependência de framework além das anotações JPA. MapStruct mapeia entre domain e DTOs sem poluir o modelo.

---

## 🗂️ Modelo de Dados

As tabelas são geradas pelo Hibernate com base nas entidades anotadas. A herança entre `User`, `Admin` e `Cliente` usa **JOINED Table Inheritance** — cada subtipo tem sua própria tabela ligada a `tb_user` pela chave primária.

```
tb_user (base)
├── tb_admins       (user_id FK → tb_user)
└── tb_clientes     (user_id FK → tb_user)
    └── tb_carrinho (cliente_id FK)

tb_pedidos
└── tb_itens_pedido (pedido_id FK, produto_id FK → tb_produtos)

tb_produtos
tb_enderecos        (vinculado a tb_user via OneToOne)
tb_contatos         (vinculado a tb_user via ManyToOne)
tb_clientes_favoritos (tabela de junção ManyToMany: cliente × produto)
```

**Por que JOINED ao invés de Single Table (STI):** JOINED cria tabelas normalizadas por subtipo, sem colunas nulas obrigatórias. Facilita adicionar novos subtipos e mantém integridade referencial mais clara no banco.

---

## 🔗 Endpoints da API

### `AdminController` — `/admins`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/admins` | Lista todos os admins |
| `GET` | `/admins/{id}` | Busca por ID |
| `GET` | `/admins/cpf/{cpf}` | Busca por CPF exato |
| `GET` | `/admins/nome/{name}` | Busca por nome (parcial, LIKE) |
| `POST` | `/admins` | Cria novo admin |
| `PUT` | `/admins/{id}` | Atualiza admin existente |
| `DELETE` | `/admins/{id}` | Remove admin (`204 No Content`) |

### `ClienteController` — `/clientes`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/clientes` | Lista todos os clientes |
| `GET` | `/clientes/{id}` | Busca por ID |
| `GET` | `/clientes/cpf/{cpf}` | Busca por CPF exato |
| `GET` | `/clientes/nome/{name}` | Busca por nome (parcial, LIKE) |
| `POST` | `/clientes` | Cria novo cliente |
| `PUT` | `/clientes/{id}` | Atualiza cliente existente |
| `DELETE` | `/clientes/{id}` | Remove cliente (`204 No Content`) |

### `ProdutoController` — `/produtos`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/produtos` | Lista todos os produtos |
| `GET` | `/produtos/{id}` | Busca por ID |
| `GET` | `/produtos/sem-estoque` | Lista produtos com estoque zerado |
| `POST` | `/produtos` | Cria novo produto |
| `PUT` | `/produtos/{id}` | Atualiza produto existente |
| `DELETE` | `/produtos/{id}` | Remove produto (`204 No Content`) |

### `PedidoController` — `/pedidos`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/pedidos` | Lista todos os pedidos |
| `POST` | `/pedidos` | Cria novo pedido (`201 Created`) |

> Datas (`dataDoPedido`, `dataDeEntregaDoPedido`), status e vínculo bidirecional dos itens são preenchidos automaticamente pelo `PedidoService` — não precisam ser enviados no body da requisição.

---

## 📦 Exemplos de Requisições

**Criar Produto:**
```json
POST /produtos
{
  "descricao": "Notebook Dell Inspiron",
  "quantidadeEmEstoque": 10,
  "precoUnitario": 3500.00,
  "status": true
}
```

**Criar Cliente:**
```json
POST /clientes
{
  "name": "Elis Regina",
  "cpf": "123.234.345-60",
  "password": "hash_aqui",
  "tipo": "CLIENTE",
  "contatoList": [
    { "telefone": "41 99999-0000", "email": "elis@email.com", "whatsapp": "41 99999-0000" }
  ]
}
```

**Criar Pedido:**
```json
POST /pedidos
{
  "cliente": { "ID": 1 },
  "items": [
    {
      "produto": { "ID": 1 },
      "quantidade": 2,
      "precoUnitario": 3500.00
    }
  ]
}
```

**Resposta de erro (`404 Not Found`):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Admin não encontrado"
}
```

---

## ⚙️ Pré-requisitos

- [Java 17](https://adoptium.net/) ou superior
- [Maven 3.8+](https://maven.apache.org/) — ou usar o Maven Wrapper incluso (`./mvnw`)
- [PostgreSQL 13+](https://www.postgresql.org/) em execução

---

## 🔧 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/NycollasCaprini/if-retail-spring.git
cd if-retail-spring
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE if_retail_spring;
```

### 3. Configure `application.properties`

Edite `src/main/resources/application.properties`:

```properties
spring.application.name=if-retail-spring

# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/if_retail_spring
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA / Hibernate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

spring.profiles.active=local
```

> `ddl-auto=create-drop` recria o schema a cada inicialização — útil em desenvolvimento. Use `update` para manter os dados entre reinicializações, ou `validate` em produção.

### 4. Execute a aplicação

```bash
# Com Maven Wrapper (recomendado — não exige Maven instalado)
./mvnw spring-boot:run

# No Windows
mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`.

### 5. Gerar o WAR para deploy externo

```bash
./mvnw package
# Saída: target/if-retail-spring-0.0.1-SNAPSHOT.war
```

---

## 🧪 Testes

Os testes de integração usam `@SpringBootTest` (sobe o contexto completo) e `@Transactional` (reverte automaticamente cada teste, mantendo o banco limpo). Requerem banco PostgreSQL configurado.

```bash
# Todos os testes
./mvnw test

# Classe específica
./mvnw test -Dtest=ProdutoRepositoryTest

# Método específico
./mvnw test -Dtest=AdminRepositoryTest#testInserir
```

| Classe de Teste | Cobertura |
|---|---|
| `IfRetailSpringApplicationTests` | Smoke test — contexto Spring carrega sem erros |
| `AdminRepositoryTest` | CRUD, busca por CPF, nome exato e nome parcial (LIKE) |
| `ClienteRepositoryTest` | CRUD, criação com carrinho via Factory, busca por CPF e nome |
| `ProdutoRepositoryTest` | CRUD, busca de produtos sem estoque |
| `PedidoRepositoryTest` | Criação de pedido com itens e cliente associado |

---

## 🔗 Projetos Relacionados

Este projeto faz parte de um ecossistema de três repositórios:

| Repositório | Tecnologia | Papel |
|---|---|---|
| [if-retail-spring](https://github.com/NycollasCaprini/if-retail-spring) | Java / Spring Boot | **Este projeto** — API REST |
| [if-retail-frontend](https://github.com/NycollasCaprini/if-retail-frontend) | React / Tailwind CSS | Frontend que consome esta API |
| [if-e-retail-php](https://github.com/NycollasCaprini/if-e-retail-php) | PHP / Doctrine ORM | Versão anterior do backend |

---

## 👥 Contribuidores

- [NycollasCaprini](https://github.com/NycollasCaprini)
- [LuizFKM](https://github.com/LuizFKM) — repositório original

---

*Projeto acadêmico — Sistemas de Informação, IFPR Palmas.*
