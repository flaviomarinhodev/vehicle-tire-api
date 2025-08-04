# API de Gerenciamento de Veículos e Pneus

## Visão Geral

Esta aplicação é uma API REST para gerenciamento de veículos e pneus, permitindo associar pneus a veículos, consultar informações detalhadas e gerenciar o ciclo de vida desses componentes. A API é construída com Spring Boot e utiliza PostgreSQL como banco de dados.

## Tecnologias

- **Java 17**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring MVC**
- **PostgreSQL 15**
- **Flyway** 
- **Lombok** 
- **OpenAPI/Swagger** 
- **Docker**
- **Maven** 

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas padrão para aplicações Spring.

## Configuração do Ambiente

### Pré-requisitos

- Java 17 ou superior
- Docker e Docker Compose
- Maven

### Executando Localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/flaviomarinhodev/vehicle-tire-api.git
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

5. Testes automatizados
   ```bash
    ./mvnw test
   ```
   
## Ambiente de Demonstração
   ```
   https://vehicle-tire-api.onrender.com
   ```

## Exemplos de Requisição

Cadastrar um novo veículo:
   ```
curl -X POST https://vehicle-tire-api.onrender.com/api/v1/veiculos \
-H "Content-Type: application/json" \
-d '{
"placa": "ABC1D23",
"marca": "Honda",
"modelo": "Civic",
"ano": 2023,
"quilometragem": 15000,
"status": "ATIVO"
}'
```
Listar todos os veículos cadastrados:
```
curl -X GET https://vehicle-tire-api.onrender.com/api/v1/veiculos \
  -H "Accept: application/json"
```
Buscar veículo pelo id:
```
curl https://vehicle-tire-api.onrender.com/api/v1/veiculos/1
```
Listar todos os pneus disponíveis:
```
curl https://vehicle-tire-api.onrender.com/api/v1/tires/disponiveis
```
Buscar pneu por marca:
```
curl https://vehicle-tire-api.onrender.com/api/v1/tires/marca?marca=GOODYEAR
```

## Documentação da API

A documentação completa da API está disponível através do Swagger UI:
- http://localhost:8080/swagger-ui.html


## Banco de Dados

O projeto utiliza PostgreSQL como banco de dados principal e Flyway para gerenciar migrações.

## Variáveis de Ambiente
DB_URL=jdbc:postgresql://localhost:5432/vehicle_tire_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
SERVER_PORT=8080
DB_POOL_MAX_SIZE=20
DB_POOL_MIN_IDLE=5
DB_CONNECTION_TIMEOUT=20000
HIBERNATE_DDL_AUTO=validate
JPA_SHOW_SQL=false
HIBERNATE_SQL_LOG_LEVEL=DEBUG
HIBERNATE_BINDER_LOG_LEVEL=TRACE
FLYWAY_ENABLED=true
FLYWAY_LOCATIONS=classpath:db/migration
SWAGGER_UI_PATH=/swagger-ui.html
MANAGEMENT_ENDPOINTS=health,info,metrics
APP_NAME=vehicle-tire-api

## Docker

O projeto inclui arquivos para conteinerização:

- **Dockerfile**: Configuração para construir a imagem da aplicação
- **docker-compose.yml**: Orquestra a aplicação e o banco de dados


## Monitoramento e Atuação

O projeto inclui Spring Actuator para monitoramento:
- http://localhost:8080/actuator/health


---

Developed by Flávio Marinho 