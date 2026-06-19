# ☕ IF E-Retail — Spring Boot

API REST de e-commerce desenvolvida em **Java 17** com **Spring Boot 4**, **Spring Data JPA**, **Hibernate** e **PostgreSQL**. Projeto acadêmico do curso de Sistemas de Informação — **IFPR Palmas**. Esta é a evolução da [versão PHP](https://github.com/LuizFKM/if-e-retail-php), reescrita com uma arquitetura de camadas (Controller → Service → Repository) e empacotada como WAR para deploy em servidor externo.

---

## 📋 Sobre o Projeto

O IF E-Retail Spring expõe uma **API REST** completa para gestão de uma loja virtual. Cobre:

- CRUD de **admins**, **clientes**, **produtos** e **pedidos**
- **Autenticação** por e-mail/senha com resposta variável por perfil (`/auth/login`)
- Buscas por **CPF** e por **nome parcial** (LIKE) para admins e clientes
- Listagem de **produtos sem estoque**
- **Lista de favoritos** por cliente (adicionar/remover produtos)
- **Upload de imagens** (foto de perfil do cliente e foto do produto) via **Cloudinary**
- Ciclo de vida do pedido: criar, **entregar**, **cancelar** e excluir
- **Paginação** em todas as listagens (Spring Data `Pageable`)

---

## 🚀 Tecnologias e Dependências

| Tecnologia | Versão | Finalidade |
|---|---|---|
| [Java](https://www.oracle.com/java/) | 17 | Linguagem principal |
| [Spring Boot](https://spring.io/projects/spring-boot) | 4.0.3 | Framework principal (web, JPA, testes) |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa) | via Boot | Repositórios com queries por convenção + paginação |
| [Hibernate](https://hibernate.org/) | via Boot | ORM — implementação JPA |
| [Spring MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html) | via Boot (`spring-boot-starter-webmvc`) | Camada web REST |
| [PostgreSQL](https://www.postgresql.org/) | — | Banco de dados relacional |
| [Lombok](https://projectlombok.org/) | via Boot | Geração de getters, setters e construtores em tempo de compilação |
| [MapStruct](https://mapstruct.org/) | 1.6.3 | Mapeamento entre entidades e DTOs |
| [Cloudinary](https://cloudinary.com/) (`cloudinary-http5`) | 2.3.2 | Armazenamento e hospedagem de imagens |
| [Spring DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools) | via Boot | Hot reload em desenvolvimento |
| [JUnit 5](https://junit.org/junit5/) + [Mockito](https://site.mockito.org/) | via Boot | Testes automatizados |
| [Maven](https://maven.apache.org/) | — | Build e gerenciamento de dependências |

> O projeto é empacotado como **WAR** (`<packaging>war</packaging>`), o que permite deploy tanto pelo `spring-boot:run` quanto em servidores externos como Tomcat ou WildFly via `ServletInitializer`. O Tomcat embarcado está em escopo `provided` justamente por isso.

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
│   │   │   ├── config/                          # Configurações de infraestrutura
│   │   │   │   ├── CorsConfig.java              # Libera CORS para o frontend (localhost:5173)
│   │   │   │   ├── CloudinaryConfig.java        # Bean do cliente Cloudinary (lê cloudinary.url)
│   │   │   │   └── DataInitializer.java         # Cria um admin de teste no primeiro start
│   │   │   ├── controllers/                     # Camada REST — recebe e responde requisições HTTP
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── ClienteController.java
│   │   │   │   ├── PedidoController.java
│   │   │   │   └── ProdutoController.java
│   │   │   ├── services/                        # Lógica de negócio e validações
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── AdminService.java
│   │   │   │   ├── ClienteService.java
│   │   │   │   ├── PedidoService.java
│   │   │   │   ├── ProdutoService.java
│   │   │   │   └── StorageService.java          # Upload de imagens no Cloudinary
│   │   │   ├── repository/                      # Spring Data JPA — acesso ao banco
│   │   │   │   ├── UserRepository.java          # findByEmail (usado no login e no seeding)
│   │   │   │   ├── AdminRepository.java
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── PedidoRepository.java
│   │   │   │   └── ProdutoRepository.java
│   │   │   ├── mappers/                         # MapStruct — entidade ⇄ DTO
│   │   │   │   ├── UserMapper.java   AdminMapper.java   ClienteMapper.java
│   │   │   │   ├── ProdutoMapper.java   PedidoMapper.java   ItemPedidoMapper.java
│   │   │   │   ├── EnderecoMapper.java   ContatoMapper.java
│   │   │   └── domain/                          # Entidades, DTOs, enums e factories
│   │   │       ├── GenericDomain.java           # Superclasse abstrata com ID auto-gerado
│   │   │       ├── user/        (User + DTOs)   # Entidade base (JOINED Inheritance)
│   │   │       ├── admin/       (Admin + DTOs)
│   │   │       ├── cliente/     (Cliente + DTOs)
│   │   │       ├── produto/     (Produto + DTOs)
│   │   │       ├── pedido/      (Pedido, ItemPedido + DTOs)
│   │   │       ├── endereco/    (Endereco + DTOs)
│   │   │       ├── contato/     (Contato + DTOs)
│   │   │       ├── auth/        (LoginRequestDTO, LoginResponseDTO)
│   │   │       ├── enums/       (UserRole, StatusPedido)
│   │   │       └── factory/     (UserFactory, LoginResponseFactory)
│   │   └── resources/
│   │       └── application.properties           # Configurações do Spring Boot
│   └── test/
│       └── java/br/edu/ifpr/bsi/ifretailspring/
│           ├── IfRetailSpringApplicationTests.java
│           ├── repository/                      # Testes de integração com banco real
│           │   ├── AdminRepositoryTest.java
│           │   ├── ClienteRepositoryTest.java
│           │   ├── PedidoRepositoryTest.java
│           │   └── ProdutoRepositoryTest.java
│           └── service/
│               └── ProdutoServiceTest.java      # Testa o service com StorageService mockado
├── mvnw / mvnw.cmd             # Scripts Maven Wrapper (Linux/Mac e Windows)
└── pom.xml                     # Dependências e configuração de build
```

**Por que essa estrutura:**

- **`controllers/`** — só lida com HTTP (receber request, chamar service, devolver response). Nenhuma regra de negócio aqui.
- **`services/`** — toda a lógica de negócio, validações e tratamento de erro (`ResponseStatusException`). Controllers não sabem como o dado é obtido.
- **`repository/`** — interfaces Spring Data JPA; o Spring gera a implementação automaticamente em tempo de execução.
- **`mappers/`** — converte entre entidades de domínio e DTOs com MapStruct, evitando expor a entidade direto na API e mantendo o modelo livre de lógica de serialização.
- **`config/`** — beans de infraestrutura (CORS, Cloudinary) e o seeding inicial do banco.
- **`domain/`** — entidades puras (apenas anotações JPA), DTOs, enums e factories, organizados por agregado.

---

## 🗂️ Modelo de Dados

As tabelas são geradas pelo Hibernate com base nas entidades anotadas. A herança entre `User`, `Admin` e `Cliente` usa **JOINED Table Inheritance** — cada subtipo tem sua própria tabela ligada a `tb_user` pela chave primária (`user_id`).

```
tb_user (base: name, CPF, e-mail, password, url_foto_perfil, role)
├── tb_admins   (user_id FK → tb_user; matricula, setor, cargo, dataAdmissao)
└── tb_clientes (user_id FK → tb_user)

tb_enderecos             (OneToOne com tb_user via endereco_id)
tb_contatos              (ManyToOne → tb_user via user_id)

tb_produtos              (descricao, quantidade, preco-unidade, url-foto-produto)

tb_pedidos               (dataDoPedido, status [ENVIADO|ENTREGUE|CANCELADO], cliente_id FK)
└── tb_itens_pedido      (pedido_id FK, produto_id FK, quantidade)

tb_clientes_favoritos    (tabela de junção ManyToMany: cliente_id × produto_id)
```

**Enums:**

- `UserRole` → `CLIENTE`, `ADMIN`
- `StatusPedido` → `ENVIADO` (padrão ao criar), `ENTREGUE`, `CANCELADO`

**Por que JOINED ao invés de Single Table (STI):** JOINED cria tabelas normalizadas por subtipo, sem colunas nulas obrigatórias. Facilita adicionar novos subtipos e mantém integridade referencial mais clara no banco.

> Observação: `spring.jpa.properties.hibernate.globally_quoted_identifiers=true` está ativo porque alguns nomes de coluna usam caracteres especiais (ex.: `e-mail`, `preco-unidade`), que precisam ser escapados pelo dialeto.

---

## 🔗 Endpoints da API

Todas as listagens são **paginadas** (parâmetros padrão do Spring: `?page=0&size=...&sort=id`). O `size` padrão varia por recurso, conforme indicado.

### `AuthController` — `/auth`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Autentica por e-mail/senha e retorna id, nome, role e rota de redirecionamento |

### `AdminController` — `/admins`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/admins` | Lista admins (paginado, `size=10`) |
| `GET` | `/admins/{id}` | Busca por ID |
| `GET` | `/admins/cpf/{cpf}` | Busca por CPF exato |
| `GET` | `/admins/nome/{name}` | Busca por nome (parcial, LIKE) |
| `POST` | `/admins` | Cria novo admin (`201 Created`) |
| `PUT` | `/admins/{id}` | Atualiza admin existente |
| `DELETE` | `/admins/{id}` | Remove admin (`204 No Content`) |

### `ClienteController` — `/clientes`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/clientes` | Lista clientes (paginado, `size=10`) |
| `GET` | `/clientes/{id}` | Busca por ID |
| `GET` | `/clientes/cpf/{cpf}` | Busca por CPF exato |
| `GET` | `/clientes/nome/{name}` | Busca por nome (parcial, LIKE) |
| `POST` | `/clientes` | Cria novo cliente (`201 Created`) |
| `PUT` | `/clientes/{id}` | Atualiza cliente — **`multipart/form-data`**: parte `dados` (JSON) + parte `imagem` (arquivo, opcional) |
| `POST` | `/clientes/{id}/imagem` | Upload da foto de perfil (campo `imagem`) |
| `DELETE` | `/clientes/{id}` | Remove cliente (`204 No Content`) |
| `POST` | `/clientes/{id}/favoritos/{produtoId}` | Adiciona produto aos favoritos |
| `DELETE` | `/clientes/{id}/favoritos/{produtoId}` | Remove produto dos favoritos |

### `ProdutoController` — `/produtos`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/produtos` | Lista produtos (paginado, `size=12`) |
| `GET` | `/produtos/{id}` | Busca por ID |
| `GET` | `/produtos/sem-estoque` | Lista produtos com estoque zerado |
| `POST` | `/produtos` | Cria novo produto (`201 Created`) |
| `PUT` | `/produtos/{id}` | Atualiza produto existente |
| `POST` | `/produtos/{id}/imagem` | Upload da foto do produto (campo `img`) |
| `DELETE` | `/produtos/{id}` | Remove produto (`204 No Content`) |

### `PedidoController` — `/pedidos`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/pedidos` | Lista pedidos (paginado, `size=10`) |
| `GET` | `/pedidos/{id}` | Busca por ID |
| `GET` | `/pedidos/cliente/{clienteId}` | Lista pedidos de um cliente (paginado, `size=5`) |
| `POST` | `/pedidos` | Cria novo pedido (`201 Created`) |
| `PATCH` | `/pedidos/{id}/entregar` | Marca o pedido como `ENTREGUE` (`204 No Content`) |
| `PATCH` | `/pedidos/{id}/cancelar` | Marca o pedido como `CANCELADO` (`204 No Content`) |
| `DELETE` | `/pedidos/{id}` | Remove pedido (`204 No Content`) |

> A `dataDoPedido` é preenchida automaticamente (`@CreationTimestamp`) e o `status` inicia como `ENVIADO` no `PedidoService` — não precisam ser enviados no body. Cancelar e entregar **não deletam** o pedido, apenas mudam o status.

---

## 📦 Exemplos de Requisições

**Login:**
```json
POST /auth/login
{
  "email": "admin@ifretail.com",
  "password": "admin123"
}
```
Resposta:
```json
{ "id": 1, "name": "Administrador", "role": "ADMIN", "redirect": "/painel" }
```

**Criar Produto:**
```json
POST /produtos
{
  "descricao": "Notebook Dell Inspiron",
  "quantidadeEmEstoque": 10,
  "precoUnitario": 3500.00,
  "urlFotoProduto": null
}
```

**Criar Cliente:**
```json
POST /clientes
{
  "name": "Elis Regina",
  "cpf": "123.234.345-60",
  "email": "elis@email.com",
  "password": "senha_aqui",
  "role": "CLIENTE",
  "endereco": {
    "rua": "Rua das Flores", "numero": "100", "complemento": "Apto 2",
    "bairro": "Centro", "cidade": "Palmas", "estado": "PR",
    "cep": "85555-000", "pais": "Brasil"
  },
  "contatos": [
    { "telefone": "46 99999-0000", "email": "elis@email.com", "whatsapp": "46 99999-0000" }
  ]
}
```

**Criar Pedido:**
```json
POST /pedidos
{
  "clienteId": 1,
  "itens": [
    { "produtoId": 1, "quantidade": 2 },
    { "produtoId": 5, "quantidade": 1 }
  ]
}
```

**Resposta de erro (`404 Not Found`):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Pedido não encontrado"
}
```

---

## ⚙️ Pré-requisitos

- [Java 17](https://adoptium.net/) ou superior
- [Maven 3.8+](https://maven.apache.org/) — ou usar o Maven Wrapper incluso (`./mvnw`)
- [PostgreSQL 13+](https://www.postgresql.org/) em execução
- Conta no [Cloudinary](https://cloudinary.com/) (para os endpoints de upload de imagem)

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

Edite `src/main/resources/application.properties`. O arquivo versionado **não inclui** as credenciais do banco nem a URL do Cloudinary — você precisa adicioná-las:

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
spring.jpa.properties.hibernate.globally_quoted_identifiers=true

# Cloudinary (upload de imagens)
cloudinary.url=cloudinary://<api_key>:<api_secret>@<cloud_name>

spring.profiles.active=local
```

> `ddl-auto=create-drop` recria o schema a cada inicialização — útil em desenvolvimento. Use `update` para manter os dados entre reinicializações, ou `validate` em produção.
>
> ⚠️ Recomendado manter `cloudinary.url` e credenciais fora do versionamento (ex.: variáveis de ambiente), para não expor segredos no repositório.

### 4. Execute a aplicação

```bash
# Com Maven Wrapper (recomendado — não exige Maven instalado)
./mvnw spring-boot:run

# No Windows
mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080`. No primeiro start, o `DataInitializer` cria automaticamente um admin de teste:

```
e-mail: admin@ifretail.com
senha:  admin123
```

> O frontend liberado por padrão no CORS é `http://localhost:5173` (Vite). Ajuste em `CorsConfig` se necessário.

### 5. Gerar o WAR para deploy externo

```bash
./mvnw package
# Saída: target/if-retail-spring-0.0.1-SNAPSHOT.war
```

---

## 🧪 Testes

Os testes usam `@SpringBootTest` (sobe o contexto completo) e `@Transactional` (reverte automaticamente cada teste, mantendo o banco limpo). Requerem banco PostgreSQL configurado. O teste de service usa **Mockito** (`@MockitoBean`) para simular o `StorageService` e não depender do Cloudinary real.

```bash
# Todos os testes
./mvnw test

# Classe específica
./mvnw test -Dtest=ProdutoServiceTest

# Método específico
./mvnw test -Dtest=AdminRepositoryTest#testInserir
```

| Classe de Teste | Cobertura |
|---|---|
| `IfRetailSpringApplicationTests` | Smoke test — contexto Spring carrega sem erros |
| `AdminRepositoryTest` | CRUD, busca por CPF, nome exato e nome parcial (LIKE) |
| `ClienteRepositoryTest` | CRUD, busca por CPF e nome |
| `ProdutoRepositoryTest` | CRUD, busca de produtos sem estoque |
| `PedidoRepositoryTest` | Criação de pedido com itens e cliente associado |
| `ProdutoServiceTest` | Regras do service com `StorageService` mockado (upload de imagem) |

---

## 🔗 Projetos Relacionados

Este projeto faz parte de um ecossistema de repositórios:

| Repositório | Tecnologia | Papel |
|---|---|---|
| [if-retail-spring](https://github.com/NycollasCaprini/if-retail-spring) | Java / Spring Boot | **Este projeto** — API REST |
| [if-retail-frontend](https://github.com/NycollasCaprini/if-retail-frontend) | React / Tailwind CSS | Frontend que consome esta API |
| [if-e-retail-php](https://github.com/LuizFKM/if-e-retail-php) | PHP / Doctrine ORM | Versão anterior do backend |

---

## 👥 Contribuidores

- [NycollasCaprini](https://github.com/NycollasCaprini)
- [LuizFKM](https://github.com/LuizFKM) — repositório original

---

*Projeto acadêmico — Sistemas de Informação, IFPR Palmas.*
