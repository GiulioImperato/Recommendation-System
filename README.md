# 🎬 Recommendation System
> A genre-based movie recommender built with Java & Spring Boot

A Java-based RESTful service simulating a **recommendation engine for video content**, built with Spring Boot.  
It manages a catalogue of movies and user interactions - such as **ratings** and **views** - and provides **genre-aware** movie recommendations.

## 📚 Table of Contents

- [📌 Objective](#-objective)
- [🛠️ Built With](#-built-with)
- [🛠️ Functional Requirements](#functional-requirements)
- [🚀 Features](#-features)
- [📖 API Specification](#-api-specification)
- [🗂️ Project Structure](#-project-structure)
- [📥 Provided Data Files](#-provided-data-files)
- [🐳 Docker, Containerization & Deployment](#-docker-containerization--deployment)
- [▶️ Getting Started](#-getting-started)
- [📊 API Testing](#-api-testing)
- [🧪 Testing Strategy & Coverage](#-testing-strategy--coverage)
- [🧩 DB Schema: Design Choices](#-db-schema-design-choices)
- [⚙️ RESTful API Considerations](#-restful-api-considerations)
- [⚡ Native Query Performance](#-native-query-performance)
- [📄 License](#-license)
- [🙋‍♂️ Author](#-author)

## 🎯 Objective

The service is designed to:  
* Manage a catalogue of movies;  
* Track and manage user interactions;  
* Recommend movies.

### Interaction/Event Types

**Rating**: Integer rating from 1 (dislike) to 5 (like)

**View**: A view percentage from 0% to 100%  
⤷ Converted into **implicit ratings** as:
* 60–80% → Rating 4  
* 81%-100% → Rating 5  

**Note**: Explicit ratings override implicit ones.

## 🛠️ Built With

* Java 21  
* Spring Boot 3.4.5  
* JPA  
* H2 (testing) / PostgreSQL (production-ready)  
* JUnit  
* Maven  
* Docker  

## 🛠️ Functional Requirements

### 1. Retrieve a list of all movies

Retrieve a list of all movies with the possibility to specify optional query parameters:  
* **genre**: filter movies by genre;
* **minRating**: filter movies with an average rating above a certain value;
* **maxRating**: filter movies with an average rating below a certain value.

### 2. Retrieve a user's interaction history

Retrieve a user's interaction history. The API has to provide an optional query parameter to retrieve ratings only, views only or both.

### 3. Add an event for a movie by the user

Ingest a new event (rating or view) for a movie by updating the user's interaction history in the database and adjusting recommendation results accordingly.

### 4. Recommend movies for a user

Retrieve a list of recommended movies similar to those that the user has rated highly (e.g. 4 and 5) and hasn't rated yet. For sake of simplicity, similarity between movies has to be computed on “Genre” metadata only. Optionally, prioritize movies with the higher number of events.

## 🚀 Features

✅ Add or update interactions via `PUT /interactions`  
✅ Get personalized movie recommendations via `GET /recommendations/{userId}`  
✅ Uses genre affinity & interaction count sorting strategies  
✅ Built-in Swagger documentation  
✅ Prepopulated database using CSV files  
✅ Dockerized setup with PostgreSQL and PgAdmin  

## 🗂️ Project Structure

```bash
├── dto/
│   ├── InteractionDto.java
│   ├── InteractionRequestDto.java
│   ├── MovieDto.java
│   └── UserDto.java
├── mapper/
│   ├── InteractionMapper.java
│   ├── MovieMapper.java
│   └── UserMapper.java
├── entity/
│   ├── Interaction.java
│   ├── Movie.java
│   └── User.java
├── repository/
│   ├── MovieRepository.java
│   ├── UserRepository.java
│   └── InteractionRepository.java
├── services/
│   ├── InteractionService.java
│   ├── MovieService.java
│   ├── RecommendationsService.java
│   └── UserService.java
├── controllers/
│   ├── InteractionController.java
│   ├── MovieController.java
│   ├── RecommendationsController.java
│   └── UserController.java
├── strategy/
│   ├── GenreAffinityFilter.java
│   ├── InteractionCountSorter.java
│   ├── MovieSimilarityFilterStrategy.java
│   └── MovieSorterStrategy.java
├── bootstrap/
│   └── DataLoader.java
├── utils/
│   └── RatingUtils.java
├── test/
│	 ├── repository/
│	 │   └── MovieRepositoryTest.java
│	 ├── utils/
│	 │   └── RatingUtilsTest.java
│	 └── strategy/
│		  ├── GenreAffinityFilterTest.java
│		  └── InteractionCountSorterTest.java
│
├── init.sql
├── Dockerfile
└── docker-compose.yml
```

## 📖 API Specification

This project provides a fully documented, self-describing RESTful API, allowing developers and stakeholders to explore and understand the system's capabilities **without digging into the source code**.

### 🔍 Swagger UI

A live, interactive **Swagger UI** included out of the box. It offers:

- 🧭 **Clear endpoint documentation**;
- 🧪 **Request/response examples**;
- ✅ **Try-it-out functionality** for quick manual testing.

Once the app is running, access the UI at:  
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 🔧 APIs

```
GET     /api/v1/movies                          List all movies with optional filters
GET     /api/v1/interactions/{userId}/history   Retrieve a user's interaction history
PUT     /api/v1/interactions/addevent           Add an event (rating or view) for a movie by the user
GET     /api/v1/recommendations/{userId}        Get movie recommendations for a specific user
GET     /api/v1/users                           Retrieve all users in the system
```

Each endpoint is fully documented in Swagger, including:
- Query parameters;
- Request bodies;
- Example payloads;
- Response formats and HTTP status codes.

> 🔄 Swagger is auto-generated from the code using SpringDoc.


## 📥 Provided Data Files

The application uses CSV files to **prepopulate the database** on startup:

- `users.csv` contains user information. 
- `movies.csv` contains movie information.
- `ratings.csv` contains user ratings for movies. 

> ✅ No manual data entry required.  
> 📁 Files located in `src/main/resources/data/csv`

These files are loaded automatically by the `DataLoader` class at application startup to ensure immediate usability.

## 🐳 Docker, Containerization & Deployment

This project comes with full Docker support, allowing you to spin up the entire environment - including the application, a PostgreSQL database, and a visual database inspector—without manual setup.

The system includes a `Dockerfile` and `docker-compose.yml` for easy deployment.

### 🧩 docker-compose.yml

```yaml
services:
  db:
    image: postgres:15
    container_name: recommendation_db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: recommendation
    ports:
      - "5432:5432"
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    healthcheck:
      test: ["CMD", "pg_isready", "-U", "postgres"]
      interval: 10s
      timeout: 5s
      retries: 5

  app:
    build: .
    container_name: recommendation_app
    depends_on:
      db:
        condition: service_healthy
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/recommendation
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres

  pgadmin:
    image: dpage/pgadmin4
    container_name: pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@demo.com
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "5050:80"
    depends_on:
      - db
```

The docker-compose.yml orchestrates three key services:

DB – PostgreSQL Database
* Uses postgres:15
* Loads schema from init.sql;
* Exposes port 5432;
* Includes a health check to ensure it's ready before the app starts

Spring Boot App - Recommendation Service
* Built from the Dockerfile;
* Connects to the internal PostgreSQL instance;
* Runs under the prod Spring profile;
* Exposes APIs on port 8080.

PgAdmin – Visual Database Interface
* Exposes a web interface on port 5050 to inspect the database visually.

### 🐳 Dockerfile

```dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/RecommendationSystem-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```
### 🔄 Database Initialization with `init.sql`

The `init.sql` file is automatically mounted into the PostgreSQL container via the volume mapping defined in `docker-compose.yml`:

```yaml
volumes:
  - ./init.sql:/docker-entrypoint-initdb.d/init.sql
```

## ▶️ Getting Started

### 🛠 Prerequisites

Before running the application, ensure you have Docker (v20+ recommended) installed on your machine.

### Run

Build the app (if not already built):

```
./mvnw clean package
```

Start the stack:

```
docker-compose up --build
```

Once services are up:
* Access Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
* Access PgAdmin.

#### 🔌 Connecting to PgAdmin

Go to [http://localhost:5050](http://localhost:5050)

Login: admin@demo.com / admin

Create a new server with:
* Host: db
* Port: 5432
* Username: postgres
* Password: postgres

Done! You can now explore the PostgreSQL schema. This enables full visual access to all tables and relationships in the system.

## 📊 API Testing

You can interact with the REST API via:  

* Swagger UI  
* Postman  
* cURL  

### 🔧 Features of Swagger UI

✅ Browse all available REST endpoints  
✅ View detailed request/response schema  
✅ Trigger API requests directly from the browser  
✅ Supports automatic schema generation  

## 🧪 Testing Strategy & Coverage

A bottom-up testing strategy was adopted:  

✅ Starting from the most critical and atomic components  
✅ Covering business logic and recommendation strategies  
✅ Verified API endpoints via E2E testing  

### ✅ Code Coverage

<img src="Coverage.png" alt="Coverage" width="979" height="607">

## 🧩 DB Schema: Design Choices

<contenuto mantenuto intatto>

<img src="ER_Diagram.png" alt="ER Diagram" width="661" height="674">

## ⚙️ RESTful API Considerations

### Use of HTTP PUT for Insert or Update

<contenuto mantenuto intatto>

## ⚡ Native Query Performance

<contenuto mantenuto intatto>

## 📄 License

This project is provided for evaluation and educational purposes.  
Feel free to copy ;).

## 🙋‍♂️ Author

Developed by Giulio Imperato as part of a technical challenge.  
For inquiries or feedback, feel free to reach out via GitHub or email at imperatogiulio@tutanota.com.



