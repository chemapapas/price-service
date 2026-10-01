# Price Service

Spring Boot REST API that returns the applicable price for a product and brand at a given date and time.

## Requirements
Java 21
Maven


## Running the application
Start the application with:

./mvnw spring-boot:run

The application will start on:

http://localhost:8080

The application uses an in-memory H2 database. The database schema is created automatically by Hibernate and the example pricing data is loaded from data.sql on startup.

## REST API
### Get applicable price

GET /api/prices

Query parameters:

Parameter	Description
brandId	Brand / chain identifier
productId	Product identifier
applicationDate	Date and time for which the applicable price is requested

Example:

GET /api/prices?brandId=1&productId=35455&applicationDate=2020-06-14T16:00:00

Example response:

{
"productId": 35455,
"brandId": 1,
"priceList": 2,
"startDate": "2020-06-14T15:00:00",
"endDate": "2020-06-14T18:30:00",
"price": 25.45,
"currency": "EUR"
}

When multiple prices are applicable, the price with the highest priority is returned.

If no applicable price is found, the API returns 404 Not Found.

Invalid brandId or productId values result in 400 Bad Request.


## Architecture

The application follows a Hexagonal Architecture approach.

The main components are:

REST Controller: inbound adapter.
GetApplicablePriceUseCase: input port.
GetApplicablePriceService: application service.
PriceRepository: output port.
PriceRepositoryAdapter: persistence adapter.
H2 database: persistence layer.

The domain model is independent of persistence and web frameworks.

The application layer defines the input and output ports, while infrastructure adapters provide the implementations for REST and persistence concerns.

The price lookup is performed directly in the database using the brand, product and application date criteria. When multiple tariffs are applicable, the query orders them by priority and returns the highest-priority result.

A composite database index on brand_id and product_id is used to improve the efficiency of price lookups.


## Testing

Run all tests with:

./mvnw test

The test suite includes:

Integration tests covering the five scenarios required by the technical test.
Tests for overlapping tariffs and priority selection.
REST validation tests for invalid brand and product identifiers.
Test for 404 Not Found when no applicable price exists.
Unit tests for the application service.
Unit tests for the persistence adapter and entity-to-domain mapping.

For the complete build, tests and static analysis can be executed with:

./mvnw clean verify


## Code Quality

The project uses Checkstyle for static code analysis.

Checkstyle is executed automatically during the Maven verify phase.

The build fails if Checkstyle violations are detected.


## Configuration

The application uses an in-memory H2 database with:

Hibernate schema creation and cleanup using create-drop.
Initial example data loaded from data.sql.
spring.jpa.open-in-view=false.

## Technologies
Java 21
Spring Boot
Spring Web MVC
Spring Data JPA
H2
Maven
JUnit
Mockito
MockMvc
Checkstyle