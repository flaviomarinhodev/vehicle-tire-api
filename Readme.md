
![pneu_imagem](https://github.com/user-attachments/assets/bd5eaee1-f054-47a8-8935-ef719ef269bd)

# API de Gerenciamento de Veículos e Pneus

## Visão Geral

Esta aplicação é uma API REST para gerenciamento de veículos e pneus, permitindo associar pneus a veículos, consultar informações detalhadas e gerenciar o ciclo de vida desses componentes. A API é construída com Spring Boot e utiliza PostgreSQL como banco de dados.

## Tecnologias

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- Spring MVC
- PostgreSQL 15
- Flyway
- Lombok
- OpenAPI/Swagger
- Docker
- Maven
- Cloud

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
   ```
    http://localhost:8080
   ```
5. Testes automatizados
   ```
    ./mvnw test
   ```
   
## Ambiente de Demonstração
  **https://vehicle-tire-api.onrender.com/api/v1/veiculos**



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

**https://vehicle-tire-api.onrender.com/swagger-ui.html**


## Banco de Dados

O projeto utiliza PostgreSQL como banco de dados principal e Flyway para gerenciar migrações.

## Principais Variáveis de Ambiente
- DB_CONNECTION_TIMEOUT
- DB_PASSWORD
- DB_POOL_MAX_SIZE
- DB_POOL_MIN_IDLE
- DB_URL
- DB_USERNAME
- HIBERNATE_DDL_AUTO
- SERVER_PORT

## Monitoramento

**https://vehicle-tire-api.onrender.com/actuator/health**

---

Developed by Flávio Marinho 
