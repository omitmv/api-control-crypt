# api-control-crypt

REST API built with **Spring Boot 3**, following **Hexagonal Architecture** (Ports & Adapters), secured with **JWT**, persisted in **MySQL**, and deployed to **Azure Container Apps** via **GitHub Actions CI/CD**.

---

## Architecture

```
src/main/java/com/example/apicontrolcrypt/
├── domain/                         # Core business logic (framework-agnostic)
│   ├── model/                      # Domain entities (User)
│   └── port/
│       ├── in/                     # Input ports (use-case interfaces)
│       └── out/                    # Output ports (repository interfaces)
├── application/
│   └── service/                    # Use-case implementations (UserService, AuthService)
└── infrastructure/
    ├── adapter/
    │   ├── in/web/                 # REST controllers, DTOs, mappers, exception handler
    │   └── out/persistence/        # JPA entities, Spring Data repos, persistence adapter
    ├── config/                     # SecurityConfig
    └── security/                   # JwtUtil, JwtAuthFilter, UserDetailsServiceImpl, UserSecurity
```

## Features

- ✅ **Hexagonal Architecture** — domain is fully decoupled from frameworks
- ✅ **JWT Authentication** — stateless, token-based via `Authorization: Bearer <token>`
- ✅ **UserDetailsService** — loads users from MySQL for authentication
- ✅ **JwtAuthFilter** — validates every request before it reaches controllers
- ✅ **Role-based access control** — `ROLE_USER` / `ROLE_ADMIN`
- ✅ **Bean Validation** — request DTOs validated at controller boundary
- ✅ **Global Exception Handler** — consistent error responses
- ✅ **Actuator health endpoint** — `/actuator/health`
- ✅ **Dockerized** — multi-stage build, non-root user
- ✅ **GitHub Actions CI/CD** — test on every push, deploy on merged PR

---

## Endpoints

| Method | Path              | Auth Required | Description          |
|--------|-------------------|---------------|----------------------|
| POST   | /api/auth/login   | No            | Authenticate, get JWT|
| POST   | /api/users        | No            | Register a new user  |
| GET    | /api/users        | ADMIN         | List all users       |
| GET    | /api/users/{id}   | ADMIN or owner| Get user by ID       |
| PUT    | /api/users/{id}   | ADMIN or owner| Update user          |
| DELETE | /api/users/{id}   | ADMIN         | Delete user          |
| GET    | /actuator/health  | No            | Health check         |

---

## Running Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Create the database

```sql
CREATE DATABASE apicontrolcrypt CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configure environment variables

```bash
export DB_URL=jdbc:mysql://localhost:3306/apicontrolcrypt?useSSL=false&serverTimezone=UTC
export DB_USERNAME=your_db_user
export DB_PASSWORD=your_db_password
export JWT_SECRET=your-super-secret-key-at-least-256-bits-long
export JWT_EXPIRATION_MS=86400000
```

### 3. Build and run

```bash
mvn clean package -DskipTests
java -jar target/api-control-crypt-0.0.1-SNAPSHOT.jar
```

### 4. Run tests

```bash
mvn test
```

---

## Docker

```bash
# Build
docker build -t api-control-crypt .

# Run
docker run -p 8080:8080 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/apicontrolcrypt \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=secret \
  -e JWT_SECRET=your-super-secret-key-at-least-256-bits-long \
  api-control-crypt
```

---

## CI/CD — GitHub Actions

| Workflow | Trigger                  | What it does                                   |
|----------|--------------------------|------------------------------------------------|
| `ci.yml` | Push to any branch / PR  | Build + run all tests                          |
| `cd.yml` | PR merged into `main`    | Build, push to ACR, deploy to Container Apps   |

### Required GitHub Secrets

| Secret                      | Description                                        |
|-----------------------------|----------------------------------------------------|
| `AZURE_CREDENTIALS`         | JSON output of `az ad sp create-for-rbac`          |
| `AZURE_REGISTRY_NAME`       | Azure Container Registry name (without `.azurecr.io`) |
| `AZURE_RESOURCE_GROUP`      | Resource group containing the Container App        |
| `AZURE_CONTAINER_APP_NAME`  | Azure Container App name                           |

### Azure Setup (one-time)

```bash
# Create resource group
az group create --name rg-api-control-crypt --location eastus

# Create Azure Container Registry
az acr create --name <registry-name> --resource-group rg-api-control-crypt --sku Basic --admin-enabled true

# Create Container App environment
az containerapp env create \
  --name api-control-crypt-env \
  --resource-group rg-api-control-crypt \
  --location eastus

# Create Container App
az containerapp create \
  --name api-control-crypt \
  --resource-group rg-api-control-crypt \
  --environment api-control-crypt-env \
  --image <registry-name>.azurecr.io/api-control-crypt:latest \
  --target-port 8080 \
  --ingress external \
  --registry-server <registry-name>.azurecr.io \
  --env-vars \
    DB_URL=secretref:db-url \
    DB_USERNAME=secretref:db-username \
    DB_PASSWORD=secretref:db-password \
    JWT_SECRET=secretref:jwt-secret

# Create service principal for GitHub Actions
az ad sp create-for-rbac \
  --name "github-actions-api-control-crypt" \
  --role contributor \
  --scopes /subscriptions/<subscription-id>/resourceGroups/rg-api-control-crypt \
  --sdk-auth
```

Copy the JSON output as the `AZURE_CREDENTIALS` secret in GitHub.

---

## Environment Variables Reference

| Variable           | Default                               | Description              |
|--------------------|---------------------------------------|--------------------------|
| `DB_URL`           | `jdbc:mysql://localhost:3306/...`     | MySQL JDBC URL           |
| `DB_USERNAME`      | `root`                                | DB username              |
| `DB_PASSWORD`      | `root`                                | DB password              |
| `JPA_DDL_AUTO`     | `update`                              | Hibernate DDL strategy   |
| `JWT_SECRET`       | (weak default — **change in prod!**)  | HMAC-SHA256 signing key  |
| `JWT_EXPIRATION_MS`| `86400000` (24 h)                     | Token lifetime in ms     |