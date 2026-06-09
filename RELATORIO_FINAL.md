# Relatório Final - Migração de Sistema Feira Livre (Java Web)

Este documento descreve os aspectos técnicos abordados na migração de um sistema Java Console de Gestão de Pedidos para uma arquitetura Web com **Spring Boot** (Backend) e **Angular** (Frontend). O relatório aborda os padrões de projeto aplicados, as dificuldades enfrentadas durante o desenvolvimento e as oportunidades de melhorias futuras.

---

## 1. Padrões de Projeto e Arquiteturais Aplicados

A transição de uma interface em linha de comando (com gravação em arquivos `.json` legados) para um ecossistema API REST + SPA demandou o uso extensivo de padrões da indústria:

### 1.1 Model-View-Controller (MVC) e API RESTful
O fluxo inteiro segue a separação de responsabilidades. O Backend age estritamente como provedor de dados (Controllers) expondo rotas HTTP padrão (`GET`, `POST`, `PUT`, `DELETE`), enquanto o Angular atua como a *View* dinâmica.

### 1.2 Data Transfer Object (DTO)
Utilizamos objetos simplificados para transferir os dados entre o frontend e a camada de controle do backend sem expor toda a hierarquia (e estado interno) dos objetos persistidos no banco.
* **Exemplo no Código:** A classe `PedidoDTO.java` transmite dados planos (apenas o `statusPedidoId` em vez do objeto relacional inteiro), e mascara o acesso direto à entidade `@Entity Pedido`.

### 1.3 Repository Pattern (Data Access Object - DAO)
Todo o acesso e manipulação do banco de dados relacional foi abstraído pelo Spring Data JPA, que isola a camada de dados da camada de negócios.
* **Exemplo no Código:** `PedidoRepository` (uma interface estendendo `JpaRepository`) permite que métodos como `existsByNomeIgnoreCase` sejam executados magicamente via Hibernate, sem escrever uma única linha de SQL manual.

### 1.4 Dependency Injection (Inversão de Controle)
Tanto o Spring Boot quanto o Angular utilizam injeção de dependência via construtores para prover dependências fracamente acopladas e altamente testáveis.
* **Exemplo no Angular:** Em `app.component.ts`, o `PedidoService` é injetado diretamente no construtor (`constructor(private pedidoService: PedidoService)`).
* **Exemplo no Spring:** Em `PedidoController.java`, injetamos o `PedidoService` garantindo a imutabilidade usando `private final`.

---

## 2. Principais Dificuldades Encontradas

1. **Integração Frontend/Backend (CORS):**
   * *Problema:* O Angular roda nativamente na porta `4200` enquanto o Java atua na `8080`. Os navegadores modernos bloqueiam conexões nativas entre domínios/portas diferentes pela política CORS.
   * *Solução:* Foi configurada a anotação `@CrossOrigin(origins = "*")` estritamente nas classes Controllers REST da API Java para liberar as trocas de mensagens na rede local.

2. **Gerenciamento de Assincronismo no Angular:**
   * Lidar com `Observables` (`RxJS`) para realizar o `fetch` dos pedidos e status do backend e renderizar imediatamente a interface foi inicialmente desafiador, especialmente no disparo e recarregamento da tela ao "Editar o Status" de um Pedido de forma instantânea na mesma View.

---

## 3. Melhorias Futuras Sugeridas

Apesar do sistema realizar seu CRUD completo ponta-a-ponta corretamente, há excelentes caminhos de evolução arquitetural:

1. **Testes Automatizados (TDD):** Implementar suítes de testes unitários com JUnit 5 + Mockito no Java para a camada de *Service*, e testes E2E/Componentes no Angular (usando Cypress).
2. **Autenticação e Segurança (JWT):** Adicionar o Spring Security na API e Route Guards no Angular para garantir que os pedidos de feira não sejam manipulados por usuários não autenticados.
3. **Dockerização do Ambiente:** Criar um arquivo `docker-compose.yml` e `Dockerfile` para conteinerizar a build em produção tanto do banco de dados (migrando H2 para PostgreSQL) quanto do Node e do Java.
4. **Validações Assíncronas Nativas (Reactive Forms):** Substituir o uso do `FormsModule` base por `ReactiveFormsModule` no Angular, permitindo que as validações de nome em tempo real não precisem "bater no backend" para confirmar a restrição.
