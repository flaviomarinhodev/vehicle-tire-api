# API de Gerenciamento de Veículos e Pneus

## Visão Geral

Esta aplicação é uma API RESTful para gerenciamento de veículos e pneus, permitindo associar pneus a veículos, consultar informações detalhadas e gerenciar o ciclo de vida desses componentes. A API é construída com Spring Boot e utiliza PostgreSQL como banco de dados.

## Tecnologias

- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring MVC**
- **PostgreSQL 15**
- **Flyway** (Migração de banco de dados)
- **Lombok** (Redução de código boilerplate)
- **OpenAPI/Swagger** (Documentação da API)
- **Docker & Docker Compose** (Conteinerização)
- **Maven** (Gerenciamento de dependências)
- **H2 Database** (Para testes)

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas padrão para aplicações Spring:

## Funcionalidades Principais

A API oferece os seguintes endpoints principais:

### Veículos

- **GET /api/v1/veiculos**: Lista todos os veículos
- **GET /api/v1/veiculos/{id}**: Busca um veículo pelo ID
- **POST /api/v1/veiculos**: Cria um novo veículo
- **POST /api/v1/veiculos/{vehicleId}/pneus**: Associa um pneu a um veículo
- **DELETE /api/v1/veiculos/{vehicleId}/pneus/{tireId}**: Remove um pneu de um veículo

### Pneus

- Endpoints para gerenciamento de pneus (listagem, criação, atualização, exclusão)

## Configuração do Ambiente

### Pré-requisitos

- Java 17 ou superior
- Docker e Docker Compose
- Maven

### Executando Localmente

1. Clone o repositório:
   ```bash
   git clone [url-do-repositorio]
   cd vehicle-tire-api
   ```

2. Compile o projeto:
   ```bash
   ./mvnw clean package -DskipTests
   ```

3. Inicie os contêineres Docker:
   ```bash
   docker-compose up -d
   ```

4. A aplicação estará disponível em:
    - API: http://localhost:8080


## Banco de Dados

O projeto utiliza PostgreSQL como banco de dados principal e Flyway para gerenciar migrações.

### Modelo de Dados

- **Vehicle**: Armazena informações sobre veículos
- **Tire**: Contém dados dos pneus
- **VehicleTire**: Relacionamento entre veículos e pneus
- **Enums**: VehicleStatus e TireStatus para controle de estados

## Documentação da API

A documentação completa da API está disponível através do Swagger UI:
- http://localhost:8080/swagger-ui.html

## Docker

O projeto inclui arquivos para conteinerização:

- **Dockerfile**: Configuração para construir a imagem da aplicação
- **docker-compose.yml**: Orquestra a aplicação e o banco de dados


## Monitoramento e Atuação

O projeto inclui Spring Actuator para monitoramento:
- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/info
- http://localhost:8080/actuator/metrics


---

Developed by Flávio Marinho 