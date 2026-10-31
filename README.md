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

This first stage is for local learning. Authentication, Docker, GitHub Actions, Helm, and Kubernetes come later. Browser requests go through the Angular development proxy.

## Verification in the creation session

Backend builds/tests could not run because this sandbox could not download the missing ModelMapper dependencies from Maven Central. Import the Maven project in IntelliJ and run the tests after dependencies resolve. No running-backend or end-to-end success is claimed.

References: [Spring Cloud compatibility](https://spring.io/projects/spring-cloud/), [ModelMapper](https://modelmapper.org/getting-started/).
