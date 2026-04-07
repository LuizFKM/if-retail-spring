# IF E-Retail — Spring Boot Edition

> Sistema de gestão de varejo desenvolvido como projeto acadêmico para o curso de **Sistemas de Informação** do **IFPR Palmas**. Esta é a versão Java/Spring Boot do sistema, evoluindo a arquitetura do projeto PHP para uma **API REST** completa com Spring MVC, Spring Data JPA e PostgreSQL.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Modelo de Dados](#modelo-de-dados)
- [Camada Domain](#camada-domain)
- [Camada Repository](#camada-repository)
- [Camada Service](#camada-service)
- [Camada Controller — Endpoints REST](#camada-controller--endpoints-rest)
- [Testes](#testes)
- [Como Executar](#como-executar)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Referência da API](#referência-da-api)

---

## Sobre o Projeto

O **IF E-Retail Spring** é a versão Java do sistema de varejo, reescrita do zero com Spring Boot. O projeto expõe uma **API REST** com endpoints CRUD para as quatro entidades principais: Admins, Clientes, Produtos e Pedidos.

A principal evolução em relação à versão PHP é a substituição do padrão DAO manual pelo **Spring Data JPA** (que gera as queries automaticamente por convenção de nome de método), a separação explícita em três camadas (Controller → Service → Repository), e o uso de **JOINED Table Inheritance** no lugar de Single Table Inheritance.

**Funcionalidades disponíveis:**

- CRUD completo para **Admin**, **Cliente** e **Produto** via endpoints REST
- Criação de **Pedidos** com itens, datas automáticas e validação de conteúdo
- Busca por CPF e por nome parcial (LIKE) em admins e clientes
- Listagem de **produtos sem estoque**
- Carrinho de compras vinculado automaticamente a cada cliente
- Lista de **produtos favoritos** por cliente
- Factory de criação de usuários com inicialização de estado padrão

---

## Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| Java | 17 | Linguagem principal |
| Spring Boot | 4.0.3 | Framework principal (web, JPA, testes) |
| Spring Data JPA | (via Boot) | Abstração do acesso ao banco via repositórios |
| Hibernate | (via Boot) | ORM — implementação do JPA |
| PostgreSQL | — | Banco de dados relacional |
| Lombok | (via Boot) | Redução de boilerplate (getters, setters, construtores) |
| JUnit 5 | (via Boot) | Framework de testes automatizados |
| Maven | — | Gerenciador de build e dependências |
| Jakarta Persistence (JPA) | (via Boot) | Anotações de mapeamento objeto-relacional |

---

## Arquitetura

O projeto segue a arquitetura em camadas do Spring Boot, com separação clara de responsabilidades:

```
┌──────────────────────────────────────────────────────────┐
│                    Cliente HTTP                          │
│            (Postman, cURL, Frontend, etc.)               │
└────────────────────────┬─────────────────────────────────┘
                         │ HTTP Request
┌────────────────────────▼─────────────────────────────────┐
│               Camada Controller (REST)                   │
│   @RestController — recebe requisições, delega ao        │
│   Service, e retorna ResponseEntity com JSON             │
└────────────────────────┬─────────────────────────────────┘
                         │ Chamadas de método
┌────────────────────────▼─────────────────────────────────┐
│                 Camada Service                           │
│   @Service — contém a lógica de negócio, validações      │
│   e tratamento de erros (ResponseStatusException)        │
└────────────────────────┬─────────────────────────────────┘
                         │ Chamadas de método
┌────────────────────────▼─────────────────────────────────┐
│               Camada Repository                          │
│   @Repository / JpaRepository — queries geradas por      │
│   convenção de nome ou JPQL (@Query)                     │
└────────────────────────┬─────────────────────────────────┘
                         │ SQL gerado pelo Hibernate
┌────────────────────────▼─────────────────────────────────┐
│                    PostgreSQL                            │
│           (tabelas geradas via ddl-auto)                 │
└──────────────────────────────────────────────────────────┘
```

**Padrões e decisões de design:**

- **JOINED Table Inheritance:** `Admin` e `Cliente` possuem tabelas próprias (`tb_admins`, `tb_clientes`) ligadas à tabela base `tb_user` por chave estrangeira — permite queries mais limpas e elimina colunas nulas que existiriam com STI.
- **Lombok:** `@Getter` e `@Setter` geram os acessores em tempo de compilação, mantendo as classes de domínio enxutas. `@Data` em `GenericDomain` e `User` adiciona `equals`, `hashCode` e `toString`.
- **ResponseStatusException:** os Services lançam exceções HTTP diretamente (`404 NOT_FOUND`), que o Spring converte automaticamente para respostas de erro padronizadas, sem a necessidade de um `@ExceptionHandler` global.
- **`@Transactional` nos deletes:** garante que a verificação de existência e a remoção ocorram na mesma transação, evitando race conditions.
- **`@JsonIgnore`:** usado estrategicamente em relacionamentos bidirecionais (`Pedido → Cliente`, `ItemPedido → Pedido`, `Contato → User`) para evitar recursão infinita na serialização JSON.

---

## Estrutura de Pastas

```
src/
├── main/
│   ├── java/br/edu/ifpr/bsi/ifretailspring/
│   │   │
│   │   ├── IfRetailSpringApplication.java      # Ponto de entrada — @SpringBootApplication
│   │   ├── ServletInitializer.java             # Suporte a deploy em servidor externo (WAR)
│   │   │
│   │   ├── controllers/                        # Camada de apresentação (REST)
│   │   │   ├── AdminController.java
│   │   │   ├── ClienteController.java
│   │   │   ├── PedidoController.java
│   │   │   └── ProdutoController.java
│   │   │
│   │   ├── domain/                             # Entidades de domínio e regras de modelagem
│   │   │   ├── GenericDomain.java              # Superclasse com ID gerado automaticamente
│   │   │   ├── User.java                       # Entidade base abstrata (JOINED inheritance)
│   │   │   ├── admin/
│   │   │   │   └── Admin.java
│   │   │   ├── cliente/
│   │   │   │   └── Cliente.java
│   │   │   ├── carrinho/
│   │   │   │   └── Carrinho.java
│   │   │   ├── contato/
│   │   │   │   └── Contato.java
│   │   │   ├── endereco/
│   │   │   │   └── Endereco.java
│   │   │   ├── pedido/
│   │   │   │   ├── Pedido.java
│   │   │   │   └── ItemPedido.java
│   │   │   ├── produto/
│   │   │   │   └── Produto.java
│   │   │   ├── enums/
│   │   │   │   └── UserType.java               # Enum: CLIENTE | ADMIN
│   │   │   └── factory/
│   │   │       └── UserFactory.java            # Factory para criação de usuários
│   │   │
│   │   ├── repository/                         # Camada de acesso a dados (Spring Data JPA)
│   │   │   ├── AdminRepository.java
│   │   │   ├── ClienteRepository.java
│   │   │   ├── PedidoRepository.java
│   │   │   └── ProdutoRepository.java
│   │   │
│   │   └── services/                           # Camada de lógica de negócio
│   │       ├── AdminService.java
│   │       ├── ClienteService.java
│   │       ├── PedidoService.java
│   │       └── ProdutoService.java
│   │
│   └── resources/
│       └── application.properties              # Configurações do Spring Boot
│
└── test/
    └── java/br/edu/ifpr/bsi/ifretailspring/
        ├── IfRetailSpringApplicationTests.java  # Teste de contexto Spring
        └── repository/                          # Testes de integração com banco real
            ├── AdminRepositoryTest.java
            ├── ClienteRepositoryTest.java
            ├── PedidoRepositoryTest.java
            └── ProdutoRepositoryTest.java
```

---

## Modelo de Dados

As tabelas abaixo são geradas automaticamente pelo Hibernate com `ddl-auto=create-drop`:

```
┌─────────────────────────────────────────────────────────┐
│                      tb_user                            │
│  (tabela base — JOINED Inheritance)                     │
│─────────────────────────────────────────────────────────│
│ ID          BIGINT PK (auto)                            │
│ name        VARCHAR                                     │
│ cpf         VARCHAR                                     │
│ password    VARCHAR                                     │
│ tipo        VARCHAR  (enum: CLIENTE | ADMIN)            │
│ endereco_id BIGINT FK → tb_enderecos                    │
└────────┬────────────────────────┬───────────────────────┘
         │ JOINED                 │ JOINED
┌────────▼────────┐      ┌────────▼───────────────────────┐
│   tb_admins     │      │         tb_clientes             │
│─────────────────│      │────────────────────────────────│
│ user_id  FK/PK  │      │ user_id     FK/PK → tb_user    │
│ matricula       │      │ carrinho_id FK → tb_carrinho   │
│ setor           │      └────────────────────────────────┘
│ cargo           │
│ dataAdmissao    │
│ status          │
└─────────────────┘

┌──────────────┐    ┌──────────────────┐    ┌──────────────────────┐
│ tb_carrinho  │    │  tb_itens_pedido │    │     tb_pedidos       │
│──────────────│    │──────────────────│    │──────────────────────│
│ ID      PK   │    │ ID          PK   │    │ ID              PK   │
│ cliente_id FK│    │ produto_id  FK   │    │ dataDoPedido         │
└──────────────┘    │ pedido_id   FK   │    │ dataDeEntregaDoPedido│
                    │ quantidade       │    │ cliente_id  FK       │
                    │ precoUnitario    │    │ status               │
                    └──────────────────┘    └──────────────────────┘

┌──────────────┐    ┌──────────────┐    ┌────────────────────────┐
│  tb_produtos │    │ tb_enderecos │    │    tb_contatos         │
│──────────────│    │──────────────│    │────────────────────────│
│ ID      PK   │    │ ID      PK   │    │ ID      PK             │
│ descricao    │    │ rua          │    │ telefone               │
│ qtdEstoque   │    │ numero       │    │ email                  │
│ precoUnitario│    │ complemento  │    │ whatsapp               │
│ status       │    │ bairro       │    │ user_id  FK → tb_user  │
└──────────────┘    │ cidade       │    └────────────────────────┘
                    │ estado       │
                    │ cep          │    ┌────────────────────────┐
                    │ pais         │    │ tb_clientes_favoritos  │
                    └──────────────┘    │────────────────────────│
                                        │ cliente_id FK          │
                                        │ produto_id FK          │
                                        └────────────────────────┘
```

---

## Camada Domain

### `GenericDomain` — Superclasse Abstrata

Marcada com `@MappedSuperclass` e `@Data` (Lombok), declara o campo `ID` com geração automática por `GenerationType.AUTO`. Toda entidade herda esta classe, garantindo um identificador único sem repetição de código.

```java
@Data
@MappedSuperclass
public abstract class GenericDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
}
```

**Por que `AUTO`:** o Hibernate escolhe a estratégia mais adequada para o banco conectado (no PostgreSQL, usa sequences).

---

### `User` — Entidade Base com JOINED Inheritance

Entidade abstrata mapeada para `tb_user` que usa `InheritanceType.JOINED`: cada subclasse (`Admin`, `Cliente`) possui sua própria tabela, ligada a `tb_user` pela chave primária.

**Atributos:**

| Campo | Tipo | Descrição |
|---|---|---|
| `name` | String | Nome completo |
| `cpf` | String | CPF do usuário |
| `password` | String | Senha (deve ser armazenada como hash) |
| `tipo` | UserType (Enum) | `CLIENTE` ou `ADMIN` (persistido como String) |
| `endereco` | Endereco | Endereço (OneToOne, cascade ALL, eager) |
| `contatoList` | List\<Contato\> | Contatos (OneToMany, cascade ALL, orphanRemoval) |

**Por que JOINED vs STI (versão PHP):** JOINED cria uma tabela separada por subtipo, eliminando colunas nulas obrigatórias em STI. É mais normalizado e facilita adicionar novas subclasses sem alterar `tb_user`.

---

### `Admin` — Administrador

Tabela própria `tb_admins`, ligada a `tb_user` pelo `user_id`. Lombok `@Getter @Setter` geram os acessores.

**Atributos específicos:**

| Campo | Tipo | Descrição |
|---|---|---|
| `matricula` | String | Matrícula funcional |
| `setor` | String | Setor de atuação |
| `cargo` | String | Cargo na empresa |
| `dataAdmissao` | LocalDate | Data de início |
| `status` | boolean | `true` = ativo (padrão definido pela `UserFactory`) |

---

### `Cliente` — Cliente da Loja

Tabela `tb_clientes`. Possui carrinho, histórico de pedidos e favoritos.

**Atributos específicos:**

| Campo | Tipo | Descrição |
|---|---|---|
| `carrinho` | Carrinho | Carrinho ativo (OneToOne, cascade ALL) |
| `pedidoList` | List\<Pedido\> | Histórico de pedidos (OneToMany, `mappedBy`) |
| `favoritos` | List\<Produto\> | Favoritos (ManyToMany via `tb_clientes_favoritos`) |

**Observação sobre o `ClienteService`:** ao salvar, o service percorre a lista de contatos e seta a referência `user` em cada um (`contato.setUser(cliente)`). Isso é necessário porque o lado dono do relacionamento `Contato → User` precisa da referência explícita para o Hibernate gerar o INSERT correto.

---

### `Produto` — Produto do Catálogo

Tabela `tb_produtos`. Entidade simples sem subclasses.

**Atributos:**

| Campo | Tipo | Descrição |
|---|---|---|
| `descricao` | String | Nome/descrição do produto |
| `quantidadeEmEstoque` | int | Estoque atual |
| `precoUnitario` | double | Preço por unidade |
| `status` | boolean | `true` = disponível para venda |

---

### `Pedido` — Pedido de Compra

Tabela `tb_pedidos`. As datas e o status são definidos automaticamente pelo `PedidoService` no momento da criação — o cliente não precisa enviá-los no corpo da requisição.

**Atributos:**

| Campo | Tipo | Descrição |
|---|---|---|
| `dataDoPedido` | LocalDateTime | Setada automaticamente como `now()` |
| `dataDeEntregaDoPedido` | LocalDateTime | Setada como `now() + 7 dias` |
| `cliente` | Cliente | Dono do pedido (`@JsonIgnore` — evita recursão) |
| `status` | boolean | `true` = pedido ativo (setado automaticamente) |
| `items` | List\<ItemPedido\> | Itens (OneToMany, cascade ALL, orphanRemoval) |

**Validação no Service:** o `PedidoService` rejeita pedidos sem itens com `RuntimeException("Não é possível criar um pedido sem itens.")`.

---

### `ItemPedido` — Item de Pedido

Tabela `tb_itens_pedido`. Registra produto, quantidade e preço no momento da compra.

**Atributos:**

| Campo | Tipo | Descrição |
|---|---|---|
| `produto` | Produto | Produto associado (ManyToOne) |
| `pedido` | Pedido | Pedido pai (`@JsonIgnore` — evita recursão) |
| `quantidade` | int | Quantidade adquirida |
| `precoUnitario` | double | Preço snapshot (independente do preço atual do produto) |

---

### `Carrinho` — Carrinho de Compras

Tabela `tb_carrinho`. Associado a um único cliente via `OneToOne`.

**Atributos:**

| Campo | Tipo | Descrição |
|---|---|---|
| `cliente` | Cliente | Dono do carrinho (OneToOne, `@JoinColumn`) |

**Observação na `UserFactory`:** ao criar um `Cliente`, a factory inicializa um `Carrinho` e chama `carrinho.setCliente(cliente)` e `cliente.setCarrinho(new Carrinho())`. Existe uma inconsistência: dois objetos `Carrinho` distintos são criados. Recomenda-se unificar para um único objeto compartilhado.

---

### `Contato` — Contato do Usuário

Tabela `tb_contatos`. Permite múltiplos contatos por usuário. Agora inclui o campo `whatsapp` em relação à versão PHP.

**Atributos:** `telefone`, `email`, `whatsapp`, `user` (ManyToOne, `@JsonIgnore`).

---

### `Endereco` — Endereço

Tabela `tb_enderecos`. Vinculado a `User` via OneToOne. Não possui referência de volta ao usuário (unidirecional), diferente da versão PHP.

**Atributos:** `rua`, `numero`, `complemento`, `bairro`, `cidade`, `estado`, `cep`, `pais`.

---

### `UserType` — Enum de Tipo de Usuário

```java
public enum UserType {
    CLIENTE, ADMIN;
}
```

Persistido como String via `@Enumerated(EnumType.STRING)` em `User`, tornando os valores no banco legíveis (`"CLIENTE"`, `"ADMIN"`) ao invés de índices numéricos.

---

### `UserFactory` — Fábrica de Usuários

Implementa o padrão **Factory Method** usando `switch` sobre o enum `UserType`. Garante inicialização de estado padrão:

- `ADMIN`: cria um `Admin` com `status = true`
- `CLIENTE`: cria um `Cliente` com um `Carrinho` associado

```java
// Uso
Admin admin = (Admin) UserFactory.createUser(UserType.ADMIN);
Cliente cliente = (Cliente) UserFactory.createUser(UserType.CLIENTE);
// cliente.getCarrinho() já está inicializado
```

---

## Camada Repository

Todos os repositórios estendem `JpaRepository<Entidade, Long>`, que fornece automaticamente os métodos: `save`, `findById`, `findAll`, `deleteById`, `count`, entre outros.

### `AdminRepository`

```java
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByCpf(String CPF);                // Gerado por convenção
    List<Admin> findByName(String name);               // Gerado por convenção
    
    @Query("SELECT a FROM Admin a WHERE a.name LIKE %:name%")
    List<Admin> getAllByNameLike(@Param("name") String nome); // JPQL explícita
}
```

**Por que `@Query`:** o método `findByNameContaining` (convenção) funcionaria da mesma forma, mas o `@Query` com JPQL explicita a intenção e facilita a leitura para quem não conhece a convenção.

---

### `ClienteRepository`

Mesma estrutura do `AdminRepository`, aplicada à entidade `Cliente`.

```java
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByCpf(String CPF);
    List<Cliente> findByName(String name);
    
    @Query("SELECT c FROM Cliente c WHERE c.name LIKE %:name%")
    List<Cliente> getAllByNameLike(@Param("name") String nome);
}
```

---

### `ProdutoRepository`

```java
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEmEstoque = 0")
    List<Produto> findProdutosSemEstoque();
}
```

**Por que JPQL:** a query poderia ser escrita por convenção como `findByQuantidadeEmEstoque(0)`, mas o `@Query` nomeado comunica claramente a semântica de negócio ("sem estoque").

---

### `PedidoRepository`

Interface sem métodos customizados — herda apenas o CRUD padrão do `JpaRepository`. Isso é suficiente para as operações atuais de listar e salvar pedidos.

---

## Camada Service

Os Services são anotados com `@Service` e injetados nos Controllers via `@Autowired`. Cada service encapsula a lógica de negócio e o tratamento de erros, impedindo que os Controllers contenham regras.

### Padrão de `buscarPorId` (com erro 404)

Todos os services usam o mesmo padrão para buscar por ID:

```java
public Entidade buscarPorId(Long id) {
    return this.repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Entidade não encontrada"
        ));
}
```

**Por que `ResponseStatusException`:** ao lançar essa exceção, o Spring automaticamente converte para uma resposta HTTP com o status code correto e uma mensagem de erro no corpo JSON, sem necessidade de um handler global.

---

### `AdminService` e `ClienteService`

Operações disponíveis: `listar`, `buscarPorId`, `buscarPorCpf`, `buscarPorNome`, `salvar`, `atualizar`, `excluir`.

O método `atualizar` valida a existência do registro antes de salvar, e força o ID correto via `setID(id)` para garantir UPDATE ao invés de INSERT:

```java
public Admin atualizar(Long id, Admin admin) {
    this.adminRepository.findById(id)
        .orElseThrow(...); // valida que existe
    admin.setID(id);       // força o mesmo ID → Hibernate faz UPDATE
    return this.adminRepository.save(admin);
}
```

---

### `PedidoService`

Contém a lógica de negócio mais rica do sistema:

```java
public Pedido salvar(Pedido pedido) {
    if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
        throw new RuntimeException("Não é possível criar um pedido sem itens.");
    }
    LocalDateTime hoje = LocalDateTime.now();
    pedido.setDataDoPedido(hoje);
    pedido.setDataDeEntregaDoPedido(hoje.plusDays(7)); // prazo padrão de 7 dias
    pedido.setStatus(true);
    
    pedido.getItems().forEach(item -> item.setPedido(pedido)); // resolve bidirecionalidade
    return pedidoRepository.save(pedido);
}
```

**Por que setar `item.setPedido(pedido)`:** o lado dono do relacionamento é `ItemPedido` (quem possui `@JoinColumn`). Sem isso, o Hibernate não geraria o `pedido_id` na tabela `tb_itens_pedido`.

---

### `ProdutoService`

Operações: `listar`, `buscarPorId`, `listarSemEstoque`, `salvar`, `atualizar`, `excluir`. Sem lógica adicional além do padrão.

---

## Camada Controller — Endpoints REST

Todos os controllers são anotados com `@RestController` (combina `@Controller` + `@ResponseBody`) e recebem o service correspondente por injeção de dependência.

### `AdminController` — `/admins`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `GET` | `/admins` | Lista todos os admins | `200 OK` + `List<Admin>` |
| `GET` | `/admins/{id}` | Busca admin por ID | `200 OK` + `Admin` |
| `GET` | `/admins/cpf/{cpf}` | Busca por CPF exato | `200 OK` + `List<Admin>` |
| `GET` | `/admins/nome/{name}` | Busca por nome (parcial) | `200 OK` + `List<Admin>` |
| `POST` | `/admins` | Cria novo admin | `200 OK` + `Admin` |
| `PUT` | `/admins/{id}` | Atualiza admin existente | `200 OK` + `Admin` |
| `DELETE` | `/admins/{id}` | Remove admin | `204 No Content` |

---

### `ClienteController` — `/clientes`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `GET` | `/clientes` | Lista todos os clientes | `200 OK` + `List<Cliente>` |
| `GET` | `/clientes/{id}` | Busca cliente por ID | `200 OK` + `Cliente` |
| `GET` | `/clientes/cpf/{cpf}` | Busca por CPF exato | `200 OK` + `List<Cliente>` |
| `GET` | `/clientes/nome/{name}` | Busca por nome (parcial) | `200 OK` + `List<Cliente>` |
| `POST` | `/clientes` | Cria novo cliente | `200 OK` + `Cliente` |
| `PUT` | `/clientes/{id}` | Atualiza cliente existente | `200 OK` + `Cliente` |
| `DELETE` | `/clientes/{id}` | Remove cliente | `204 No Content` |

---

### `ProdutoController` — `/produtos`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `GET` | `/produtos` | Lista todos os produtos | `200 OK` + `List<Produto>` |
| `GET` | `/produtos/{id}` | Busca produto por ID | `200 OK` + `Produto` |
| `GET` | `/produtos/sem-estoque` | Lista produtos esgotados | `200 OK` + `List<Produto>` |
| `POST` | `/produtos` | Cria novo produto | `200 OK` + `Produto` |
| `PUT` | `/produtos/{id}` | Atualiza produto existente | `200 OK` + `Produto` |
| `DELETE` | `/produtos/{id}` | Remove produto | `204 No Content` |

---

### `PedidoController` — `/pedidos`

| Método | Rota | Descrição | Resposta |
|---|---|---|---|
| `GET` | `/pedidos` | Lista todos os pedidos | `200 OK` + `List<Pedido>` |
| `POST` | `/pedidos` | Cria novo pedido | `201 Created` + `Pedido` |

> O `PedidoController` é o mais enxuto, pois ainda não implementa busca por ID, atualização ou exclusão. O `POST` já retorna `201 Created` (correto para criação de recursos), diferente dos demais controllers que retornam `200 OK`.

---

## Testes

Os testes usam `@SpringBootTest` (carrega o contexto completo) e `@Transactional` (reverte cada teste ao final, mantendo o banco limpo entre execuções).

### `IfRetailSpringApplicationTests`

Smoke test que apenas verifica se o contexto Spring carrega sem erros.

---

### `AdminRepositoryTest`

Testes de integração completos para o repositório de admins:

| Teste | O que verifica |
|---|---|
| `testInserir` | Salva um Admin manualmente e confirma ID gerado |
| `testInserirComFactory` | Usa `UserFactory` para criar e persistir o Admin |
| `testUpdate` | Salva, altera o cargo e confirma a atualização no banco |
| `testDelete` | Salva e deleta; confirma que `findById` retorna null |
| `testFindByCpf` | Salva e busca por CPF exato |
| `testFindByName` | Salva dois admins e busca por nome exato |
| `testGetAllByNameLike` | Salva dois "Gilberto" e busca por `"Gil"` parcial |
| `testListar` | Verifica listagem não-vazia e tempo de resposta < 2 segundos |

---

### `ClienteRepositoryTest`

| Teste | O que verifica |
|---|---|
| `testInserir` | Persistência básica de cliente |
| `testInserirClienteComCarrinhoViaFactory` | Confirma que o Carrinho foi salvo em cascade |
| `testUpdate` | Atualiza o nome e verifica persistência |
| `testDelete` | Deleta e confirma ausência |
| `testFindByCpf` | Busca por CPF e verifica nome do retorno |
| `testGetAllByNameLike` | Busca parcial por `"Gil"` |
| `testFindByName` | Busca exata por nome |
| `testListar` | Listagem não-vazia com verificação de performance |

---

### `ProdutoRepositoryTest`

| Teste | O que verifica |
|---|---|
| `testInsert` | Persistência de produto com todos os campos |
| `testUpdate` | Atualiza preço e confirma novo valor |
| `testDelete` | Remove produto e confirma ausência |
| `testFindAll` | Confirma lista com pelo menos 2 produtos |
| `testFindProdutosSemEstoque` | Salva produto com estoque 0 e confirma busca |

---

### `PedidoRepositoryTest`

| Teste | O que verifica |
|---|---|
| `testCriarPedido` | Cria cliente, produto e pedido com ItemPedido associado; confirma persistência |

> **Nota:** este teste não possui `@Transactional`, o que significa que os dados persistem no banco após a execução. Recomenda-se adicionar `@Transactional` para manter os testes independentes.

---

## Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- PostgreSQL 13+

### 1. Clonar o repositório

```bash
git clone https://github.com/NycollasCaprini/if-retail-spring.git
cd if-retail-spring
```

### 2. Configurar o banco de dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE if_retail_spring;
```

### 3. Configurar as credenciais

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.application.name=if-retail-spring

# Conexão com o banco
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

> **Atenção:** `ddl-auto=create-drop` recria todas as tabelas a cada inicialização. Use `update` para persistir dados entre reinicializações, ou `validate` em produção.

### 4. Executar a aplicação

```bash
# Usando o Maven Wrapper (sem Maven instalado)
./mvnw spring-boot:run

# Usando Maven instalado globalmente
mvn spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

### 5. Executar os testes

```bash
# Todos os testes (requer banco configurado)
./mvnw test

# Classe específica
./mvnw test -Dtest=ProdutoRepositoryTest

# Método específico
./mvnw test -Dtest=AdminRepositoryTest#testInserir
```

### 6. Gerar o arquivo WAR (deploy em servidor externo)

```bash
./mvnw package
# Arquivo gerado em: target/if-retail-spring-0.0.1-SNAPSHOT.war
```

---

## Configuração do Banco de Dados

O projeto usa o arquivo `application.properties` para configuração. As propriedades mais importantes são:

| Propriedade | Valor atual | Descrição |
|---|---|---|
| `spring.datasource.driver-class-name` | `org.postgresql.Driver` | Driver JDBC do PostgreSQL |
| `spring.jpa.properties.hibernate.dialect` | `PostgreSQLDialect` | Dialeto SQL gerado pelo Hibernate |
| `spring.jpa.hibernate.ddl-auto` | `create-drop` | Recria o schema a cada start |
| `spring.jpa.show-sql` | `true` | Exibe SQL gerado no console |
| `spring.profiles.active` | `local` | Perfil ativo (pode ser usado para separar configs de dev/prod) |

**Valores recomendados por ambiente:**

| Ambiente | `ddl-auto` | `show-sql` |
|---|---|---|
| Desenvolvimento | `create-drop` ou `update` | `true` |
| Homologação | `validate` | `false` |
| Produção | `validate` | `false` |

---

## Referência da API

### Exemplos de Requisições

**Criar Produto:**
```http
POST /produtos
Content-Type: application/json

{
  "descricao": "Notebook Dell Inspiron",
  "quantidadeEmEstoque": 10,
  "precoUnitario": 3500.00,
  "status": true
}
```

**Criar Cliente com Contatos:**
```http
POST /clientes
Content-Type: application/json

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
```http
POST /pedidos
Content-Type: application/json

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

**Resposta de erro (recurso não encontrado):**
```http
HTTP/1.1 404 Not Found
Content-Type: application/json

{
  "status": 404,
  "error": "Not Found",
  "message": "Admin não encontrado"
}
```

---

*Projeto acadêmico desenvolvido para o curso de Sistemas de Informação — IFPR Palmas.*
