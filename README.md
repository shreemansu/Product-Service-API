# 🛒 Product Service API

A production-ready RESTful backend service built with **Spring Boot**, featuring secure JWT-based authentication, OTP email verification, and clean layered architecture. Designed as a fully deployable microservice for product management systems.

---

## 🚀 Tech Stack

 Layer       | Technology |
 --------------------------
 Framework   -> Spring Boot |
 Security    -> Spring Security + JWT |
 Persistence -> Spring Data JPA + MySQL |
 Mail        -> JavaMailSender |
 Build Tool  -> Maven |
 Utilities   -> Lombok |
 Validation  -> Bean Validation (JSR-380) |
 Testing     -> Postman + Swagger |

---

## ✨ Features

- **10+ REST Endpoints** across full product and user management flows
- **JWT Stateless Authentication** with role-based route protection
- **OTP Email Verification** via JavaMailSender on user registration
- **Global Exception Handling** using `@ControllerAdvice` with standardized error responses
- **DTO Pattern** to decouple API responses from JPA entity models
- **Custom JPQL Queries** for filtered searches by name, course, and enrollment status
- **Bean Validation** enforced on all request DTOs (`@Valid`, `@NotNull`, `@Size`)
- **Environment-specific Profiles** via `application.properties` for flexible deployment

---

## 🏗️ Architecture

```
Client Request
     │
     ▼
┌─────────────────────┐
│   Controller Layer  │  ← REST endpoints, request validation (@Valid)
└────────┬────────────┘
         │
         ▼
┌─────────────────────┐
│   Service Layer     │  ← Business logic, event handling, OTP flow
└────────┬────────────┘
         │
         ▼
┌─────────────────────┐
│  Repository Layer   │  ← Spring Data JPA, custom JPQL queries
└────────┬────────────┘
         │
         ▼
┌─────────────────────┐
│       MySQL         │
└─────────────────────┘
```

Cross-cutting concerns:
- `@ControllerAdvice` — global exception handling, standardized error format
- `Spring Security + JWT Filter` — stateless authentication on every request
- `ApplicationEventListener` — decoupled OTP dispatch on user registration

---

## 🔐 Security

Authentication follows a stateless JWT flow:

1. User registers → OTP sent via `JavaMailSender` → email verified
2. User logs in → JWT token issued
3. Every subsequent request carries the JWT in the `Authorization: Bearer <token>` header
4. Spring Security filter validates token and enforces role-based route access

---

## 📡 API Overview

### Auth Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user, triggers OTP email |
| POST | `/api/auth/verify-otp` | Verify OTP and activate account |
| POST | `/api/auth/login` | Authenticate and receive JWT token |

### Product Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Fetch all products |
| GET | `/api/products/{id}` | Fetch product by ID |
| GET | `/api/products/search` | Filtered search (name, course, status) |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |

> All product endpoints require a valid JWT token.

---

## 🗂️ Project Structure

```
src/
└── main/
    ├── java/com/example/productservice/
    │   ├── controller/       # REST controllers
    │   ├── service/          # Business logic & event listeners
    │   ├── repository/       # JPA repositories with JPQL queries
    │   ├── model/            # JPA entity classes
    │   ├── dto/              # Request/Response DTOs
    │   ├── security/         # JWT filter, SecurityConfig
    │   ├── exception/        # GlobalExceptionHandler, custom exceptions
    │   └── event/            # Application events (OTP dispatch)
    └── resources/
        ├── application.properties          # Default config
        ├── application-dev.properties      # Dev profile
        └── application-prod.properties     # Prod profile
```

---

## ⚙️ Configuration

The service uses Spring profiles for environment-specific settings. Create your `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# Mail Server
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# JWT
jwt.secret=your_secret_key
jwt.expiration=86400000
```

Switch profiles at runtime:
```bash
java -jar product-service.jar --spring.profiles.active=prod
```

---

## 🏃 Getting Started

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### Run Locally

```bash
# 1. Clone the repo
git clone https://github.com/shreemansu/Product-Service-Api.git
cd product-service

# 2. Set up the database
mysql -u root -p -e "CREATE DATABASE productdb;"

# 3. Configure application.properties (see above)

# 4. Build and run
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## 🧪 Testing

All endpoints were tested using **Postman**. 

To test locally:
1. Register a user via `POST /api/auth/register`
2. Check your email for the OTP and verify via `POST /api/auth/verify-otp`
3. Login via `POST /api/auth/login` and copy the returned JWT token
4. Add `Authorization: Bearer <token>` header to all product API requests

---

## 📌 Key Design Decisions

- **DTO Pattern** — API contracts are independent of database schema changes; entity models are never exposed directly
- **Stateless JWT** — No server-side session storage; scales horizontally without sticky sessions
- **Application Events** — OTP email dispatch is decoupled from registration logic via Spring's event system, keeping the service layer clean
- **@ControllerAdvice** — All exceptions are caught centrally, ensuring consistent error format across all endpoints

---

## 👨‍💻 Author

Shreemansu Sekhar Pradhan    
[GitHub](https://github.com/shreemansu) · [LinkedIn](https://linkedin.com/in/shreemansu)
