# Documentação Técnica — Sistema Helpdesk com Spring Boot

## 1. Visão Geral da Arquitetura

O sistema adota o padrão arquitetural em camadas (Layered Architecture), amplamente utilizado em aplicações Spring Boot. As principais camadas são:

- **Controller (Apresentação/Interface):** Responsável por receber e responder requisições HTTP.
- **Service (Negócio):** Centraliza as regras de negócio e orquestra o fluxo entre Controller e Repository.
- **Repository (Persistência):** Abstrai o acesso ao banco de dados, utilizando Spring Data JPA.
- **Model (Domínio):** Representa as entidades de negócio e seu mapeamento para o banco.

Esse padrão favorece a separação de responsabilidades, facilita testes e manutenção, e permite evolução modular do sistema.

## 2. Estrutura do Projeto

```
src/main/java/com/ojuara/helpdesk/
│
├── config/         # Configurações do sistema (segurança, perfis, etc.)
├── enums/          # Enumerações de domínio (Status, Prioridade, Perfil)
├── model/          # Entidades de domínio e DTOs
│   └── dto/        # Data Transfer Objects
├── repositories/   # Interfaces de persistência (Spring Data JPA)
├── resources/      # Controllers REST (exposição de endpoints)
├── services/       # Serviços de negócio
│   └── exceptions/ # Tratamento de exceções customizadas
```

**Responsabilidades:**
- `config`: Configurações globais e de ambiente.
- `enums`: Tipos fixos do domínio.
- `model`: Entidades persistidas e objetos de transferência.
- `repositories`: Interfaces para acesso a dados.
- `resources`: Camada de apresentação (REST Controllers).
- `services`: Lógica de negócio e orquestração.
- `exceptions`: Estrutura de tratamento de erros.

## 3. Camada de Controller

### Papel do `@RestController`

- Expõe endpoints RESTful.
- Recebe requisições HTTP, valida dados de entrada e retorna respostas (JSON).

### Mapeamento de Endpoints

- `@RequestMapping`: Define o path base do controller.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Mapeiam métodos HTTP específicos.

**Exemplo:**
```java
@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoResource {
    @GetMapping("/{id}")
    public ResponseEntity<TecnicoDto> findById(@PathVariable Integer id) {
        // ...lógica...
    }
}
```

### Boas Práticas

- Retornar DTOs ao invés de entidades.
- Utilizar `ResponseEntity` para maior controle do status HTTP.
- Validar entradas e tratar exceções de forma centralizada.

## 4. Camada de Serviço (Service)

### Responsabilidade

- Centraliza regras de negócio.
- Evita lógica de negócio nos controllers.
- Orquestra operações entre controllers e repositórios.

### Uso de `@Service`

- Marca a classe como componente de serviço, permitindo injeção automática.

**Exemplo:**
```java
@Service
public class TecnicoService {
    // ...lógica de negócio...
}
```

### Separação de Responsabilidades

- Cada serviço deve tratar apenas um agregado de negócio.
- Não misturar lógica de apresentação ou persistência.

## 5. Camada de Persistência (Repository)

### Uso do Spring Data JPA

- Abstrai o acesso ao banco de dados.
- Reduz boilerplate com métodos prontos (findById, save, delete, etc.).

### Interface `JpaRepository`

- Herda métodos CRUD e permite definição de queries customizadas.

**Exemplo:**
```java
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {}
```

### Abstração do Acesso a Dados

- O desenvolvedor foca no domínio, não em detalhes de SQL.

## 6. Entidades (Model)

### Uso de `@Entity`, `@Table`, `@Id`

- `@Entity`: Marca a classe como entidade JPA.
- `@Table`: (Opcional) Define o nome da tabela.
- `@Id`: Identifica o campo chave primária.
- `@GeneratedValue`: Estratégia de geração do ID.

**Exemplo:**
```java
@Entity
@Table(name = "tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // ...outros campos...
}
```

### Mapeamento Objeto-Relacional (ORM)

- Relacionamentos (`@OneToMany`, `@ManyToOne`, etc.) são definidos via anotações.
- O Hibernate gerencia o ciclo de vida das entidades.

## 7. Explicação das Principais Anotações do Spring

### `@RestController`
- **O que faz:** Marca a classe como controller REST, combinando `@Controller` e `@ResponseBody`.
- **Como funciona:** Todos os métodos retornam objetos serializados (JSON/XML).
- **Quando usar:** Para expor APIs RESTful.

### `@Service`
- **O que faz:** Indica que a classe é um serviço de negócio.
- **Como funciona:** É registrada no contexto do Spring para injeção automática.
- **Quando usar:** Para lógica de negócio.

### `@Repository`
- **O que faz:** Marca a classe como componente de persistência.
- **Como funciona:** Permite tratamento automático de exceções de persistência.
- **Quando usar:** Em classes que acessam o banco de dados.

### `@Autowired`
- **O que faz:** Injeta dependências automaticamente.
- **Como funciona:** O Spring resolve e instancia o bean necessário.
- **Quando usar:** Para injeção de serviços, repositórios, etc.

### `@Entity`
- **O que faz:** Marca a classe como entidade JPA.
- **Como funciona:** O Hibernate mapeia a classe para uma tabela.
- **Quando usar:** Para classes persistidas no banco.

### `@Id`
- **O que faz:** Define o campo como chave primária.
- **Como funciona:** O JPA utiliza esse campo para identificar a entidade.
- **Quando usar:** Em entidades JPA.

### `@GeneratedValue`
- **O que faz:** Define a estratégia de geração do ID.
- **Como funciona:** O JPA/Hibernate gera o valor automaticamente.
- **Quando usar:** Para chaves primárias auto-incrementadas.

### `@RequestMapping` e variações
- **O que faz:** Mapeia URLs para métodos do controller.
- **Como funciona:** O DispatcherServlet roteia as requisições conforme o path e método HTTP.
- **Quando usar:** Para definir endpoints REST.

## 8. Fluxo Completo de uma Requisição

1. **Request:** Cliente faz requisição HTTP (ex: `POST /api/chamados`).
2. **Controller:** O método do controller recebe, valida e encaminha os dados.
3. **Service:** O service executa as regras de negócio e chama o repositório.
4. **Repository:** O repositório executa operações no banco via JPA.
5. **Banco:** Dados são persistidos ou recuperados.
6. **Response:** O controller retorna a resposta (DTO/JSON) ao cliente.

**Diagrama Simplificado:**
```
[Cliente] → [Controller] → [Service] → [Repository] → [Banco]
     ←------------------- Response -------------------←
```

## 9. Boas Práticas Aplicadas

- **Separação de responsabilidades:** Cada camada tem um papel claro.
- **Baixo acoplamento:** Controllers não conhecem detalhes de persistência.
- **Uso correto de anotações:** Facilita manutenção e evolução.
- **DTOs:** Evitam exposição direta das entidades.
- **Tratamento centralizado de exceções:** Via `@ControllerAdvice`.

## 10. Pontos de Evolução

- **DTOs e Mapeamento Automático:** Uso de MapStruct ou ModelMapper para conversão entre entidades e DTOs.
- **Validações:** Aplicar `@Valid` e anotações do Bean Validation nos DTOs.
- **Tratamento de Exceções:** Centralizar com `@ControllerAdvice` e respostas padronizadas.
- **Testes:** Implementar testes unitários e de integração (JUnit, Mockito).
- **Paginação e Filtros:** Adicionar suporte a paginação e filtros nos endpoints.
- **Documentação:** Integrar Swagger/OpenAPI para documentação automática.

---

Este material consolida os principais conceitos, decisões e práticas do projeto, servindo como referência para estudo, revisão e evolução do sistema Helpdesk com Spring Boot.
