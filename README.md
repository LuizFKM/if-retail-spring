# IF E-RETAIL (Spring Boot Edition)
Sistema de gestão de varejo para o curso de Sistemas de Informação - IFPR Palmas.

## 🚀 Tecnologias
Java: 17

Framework: Spring Boot 4.0.3

Persistência: Spring Data JPA

Banco de Dados: PostgreSQL

Build Tool: Maven

Auxiliares: Lombok (para redução de boilerplate)

---
## Estrutura do projeto

```

src/
├── main/java/br/edu/ifpr/bsi/ifretailspring/
│   ├── controllers/   # Endpoints REST e mapeamento de rotas (A porta de entrada da API)
│   ├── domain/        # Entidades de negócio (Modelos), Enums e Classes Base (User, GenericDomain)
│   ├── repository/    # Interfaces de comunicação com o Banco de Dados (Spring Data JPA)
│   ├── services/      # Lógica de negócio e regras da aplicação
│   └── IfRetailSpringApplication.java  # Classe principal que inicia o Spring Boot
│
├── main/resources/    # Arquivos de configuração (application.properties, etc.)
│
└── test/java/.../     # Testes automatizados
    └── repository/    # Testes de integração da camada de persistência (Banco de Dados)

```
