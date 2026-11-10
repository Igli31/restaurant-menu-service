# Restaurant menu service

Spring Boot 4.1.1, Java 17+, Maven, JPA, H2, Lombok, and ModelMapper.

## Run locally

Open this folder as a Maven project in IntelliJ. Let Maven download dependencies, then run MenuApplication.
Or, from this directory in PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Address: http://localhost:8081.

Each service owns a separate file-based H2 database under data/ (ignored by Git). No database installation is needed for this first stage. Run from this directory. H2 and automatic schema updates are local learning defaults; MySQL and migrations can follow later.

## Packages

- entity: database models.
- repository: JPA database access.
- dto: API request/response objects, including input validation.
- service: business interfaces.
- serviceimpl: business logic and ModelMapper conversions.
- controller: HTTP endpoints.
- config: shared Spring beans.
- exception: HTTP error handling using ProblemDetail.


## Endpoints

- GET, POST /api/categories
- GET, PUT, DELETE /api/categories/{id}
- GET, POST /api/dishes
- GET, PUT, DELETE /api/dishes/{id}

Category example:
```json
{"name":"Mains"}
```
Dish example:
```json
{"name":"Pizza","description":"Tomato and mozzarella","price":12.50,"available":true,"categoryId":1}
```
A category containing dishes cannot be deleted.

## Tests

```powershell
.\mvnw.cmd test
```

This application is for local learning. Browser requests go through the Angular development proxy. Authentication, Helm, and Kubernetes come later.

## Docker

Start Docker Desktop and build from this directory:

```powershell
docker build -t restaurant-menu:local .
```

The first stage uses the Maven Wrapper to test and build the JAR. The final stage runs it with Java 17 as a non-root user.

```powershell
docker run -d --name restaurant-menu -p 8081:8081 -v restaurant-menu-data:/app/data restaurant-menu:local
```

Stop the IntelliJ menu-service instance first to free port 8081, or use `-p 7001:8081` and access the container through `localhost:7001` instead.
The named volume keeps the container's H2 database after the container is removed. It starts separately from your IntelliJ database. Only one container should use this H2 volume at a time.

Inspect the API at http://localhost:8081/api/categories (or port 7001 if you changed the mapping).

```powershell
docker logs restaurant-menu
docker stop restaurant-menu
docker rm restaurant-menu
```

## Verification in the creation session

Backend builds/tests could not run because this sandbox could not download the missing ModelMapper dependencies from Maven Central. Import the Maven project in IntelliJ and run the tests after dependencies resolve. No running-backend or end-to-end success is claimed.

References: [Spring Cloud compatibility](https://spring.io/projects/spring-cloud/), [ModelMapper](https://modelmapper.org/getting-started/).
