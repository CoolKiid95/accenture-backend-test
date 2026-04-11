# accenture-backend-test
Fullstack developer test for Accenture 

## Overview
This project is a reactive backend API bui9lt with SpringBoot for managing franchises, branches and products

A franchise contains a list of branches, and each branch containts a list of products. 
EAch product has a name and a stock quantity. 

The API supports: 

-Creating a new Franchise
-Adding branches to a franchise
-Adding products to a branch
-Deleting products from a branch
-Updating product stock
-Retrieving the product with the highest stock per branch for a specific franchise
-Updating franchise, branch, and product names

This solution was implemented following clean code principles, a simple Clean Architecture approach, reactive programming with Spring WebFlux, unit testing, Docker support , and clear API error handling. 


## Tech Stack

-Java 17
-SpringBoot 3
-Spring WebFlux
-Reactive MongoDB
-Maven
-JUnit 5
-Mockito
-Reactor Test
-Docker
-Docker Compose

## Architecture

Project foloows a simple Clean Architecture inspuired structure: 

```text
src/main/java/com/diego/accenture/franchise
  domain/
    model/
    ports/
    exception/

  application/
    usecase/
    dto/
    mapper/

  infrastructure/
    entrypoints/rest/
    persistence/mongo/
    config/
    exception/
```

## Layers
Domain: business models, repository ports, domain exceptions
Application: use cases, DTOs(Data transfer object), mappers
Infrastructure: REST controllers, Mongo persistance adapters, exception handling, configuiration

## Implemented Features
Required features
-Create franchise
-Add branch to franchise
- Add product to branch
-Delete product from branch
-Update product stock
-Get top stock product per branch for a specific franchise
-Data persistance with MongoDB

Extra features
-Update franchise name
-Update branch name
-Update product name
-Global APO exception handling
-Unit tests for main use cases
-Dockerized applications

## Prerequisites
Make sure you have installed: 
-Java 17
-Maven
-Docker
-Docker Compose

## Run Locally
1. Clone the repository
    git clone https://github.com/CoolKiid95/accenture-backend-test.git
    cd accenture-backend-test 

2. Start MongoDB with Docker
    docker compose up -d mongo

3. Run the application
    On windows: mvnw.cmd spring-boot:run
    With Maven: mvn spring-boot:run

4. API base URL
    http://localhost:8080

## Run with Docker
To build and run the full app with MongoDB: 
    docker compose up --build

To run in Background 
    docker compose up --build -d

To stop containers: 
    docker compose down

## API Endpoints
Create franchise
    POST /api/franchises

Add branch to franchise
    POST /api/franchises/{franchiseId}/branches

Add product to branch
    POST /api/franchises/{franchiseId}/branches/{branchId}/products

Delete product from branch
    DELETE /api/franchises/{franchiseId}/branches/{branchId}/products/{productId}

Update product stock
    PATCH /api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock

Get top stock product by branch for a franchise
    GET /api/franchises/{franchiseId}/top-stock-products

Update franchise name
    PATCH /api/franchises/{franchiseId}/name

Update branch name
    PATCH /api/franchises/{franchiseId}/branches/{branchId}/name

Update product name
    PATCH /api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name


## Running Tests
Run all tests 
    mvn test

On Windows
    mvnw.cmd test

## Error Handling
The API includes global exception handling and returns consisten error responses for: 
    -Resource not found(404)
    -Validation errors(400)
    -Invalid stock values(400)
    -Unexpected server errors(500)
Example error response: 
    JSON
```text
    {
        "status": 404,
        "error": "Not Found",
        "message": "Franchise not found with id: ...",
        "timestamp": "2026-04-10T00:00:00Z",
        "details": []
    }
```
## Design decisions

Why MongoDB?
MongoDB was selected because the problem natually firtts a hierarchical document structure: 
- Franchise
    -Branches
        -products
This reduces relational complexity and makes the top-stock query straightforward.

Why WebFlux?
Reactive programming was used to align with the evaluation criteria and to build a non blocking API using Spring WebFlux and Reactive MongoDB.

Why this architecture?
A simple Clean Architecture approach was used to separate:
    -business logic
    -use case orchestration
    -infrastructure details
This improves readability, maintainability, and testability.

## Author
Developed by Diego Alexander Delgado Castañeda as part of the Accenture Fullstack Developer technical assestment. 

## Git Workflow
Development was organized through small, incremental commits in a dedicated feature branch to keep the implementation traceable and maintainable.

## Docker Configuration
A dedicated `docker` Spring profile was configured so the application can connect to MongoDB using the Docker service name instead of localhost.