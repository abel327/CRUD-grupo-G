# Projeto Feira Livre - Gestão de Pedidos (Web)

Este projeto é uma evolução (migração) de um sistema de gestão de pedidos de feira livre original em Console (Java) para uma arquitetura Web completa, utilizando **Spring Boot** para a API REST (Backend) e **Angular** para a interface interativa (Frontend).

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17+**
- **Spring Boot 3.3.0** (Spring Web, Spring Data JPA, Spring Validation)
- **H2 Database** (Banco de dados relacional em memória)
- **Maven** para gestão de dependências

### Frontend
- **Node.js**
- **Angular 19** (Standalone Components, Routing)
- **HTML5 & CSS3** (Vanilla CSS com Design System Responsivo)
- **RxJS** e **HttpClient** para integração

---

## 🛠️ Como Executar o Projeto

Para testar o projeto de ponta a ponta na sua máquina, você precisará executar o Backend e o Frontend separadamente.

### 1. Executando o Backend (Spring Boot)

O backend expõe a API RESTful na porta **8080** e utiliza um banco de dados em memória (H2), portanto, não requer instalações de banco de dados locais.

1. Abra o terminal na raiz do projeto e navegue até a pasta `backend`:
   ```bash
   cd backend
   ```
2. Execute a aplicação utilizando o Maven:
   ```bash
   mvn spring-boot:run
   ```
3. O servidor estará rodando em: `http://localhost:8080/api/pedidos`
4. *Opcional:* Acesse o console do banco H2 em `http://localhost:8080/h2-console` (URL: `jdbc:h2:mem:feiradb`, Usuário: `sa`, Senha: em branco).

---

### 2. Executando o Frontend (Angular)

O frontend interage com a API Java para construir e atualizar a interface em tempo real na porta **4200**.

1. Em um *novo terminal*, navegue até a pasta `frontend`:
   ```bash
   cd frontend
   ```
2. Instale as dependências (necessário apenas na primeira execução):
   ```bash
   npm install
   ```
3. Inicie o servidor de desenvolvimento:
   ```bash
   npm start
   ```
4. Abra o seu navegador e acesse: **[http://localhost:4200/](http://localhost:4200/)**

---

## 👨‍💻 Integrantes
- **Abel Camurça da Paz**
- **Felipe Rodrigues de Melo**

*Sprint 4 - Telas Angular, Integração Final e Apresentação.*
