Price Service

Spring Boot REST API that returns the applicable price for a product and brand at a given date and time.

Requirements
Java 21
Maven
Running the application

Start the application with:

./mvnw spring-boot:run

The application will start on:

http://localhost:8080

The application uses an in-memory H2 database. The database schema is created automatically by Hibernate and the example pricing data is loaded from data.sql on startup.

REST API
Get applicable price
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

Architecture

The application follows a Hexagonal Architecture approach.

                ┌──────────────────────┐
                │     REST Controller  │
                │      (Inbound)       │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │     Application      │
                │      Use Case        │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │   PriceRepository    │
                │    (Domain Port)     │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │ Persistence Adapter  │
                │   (Outbound)         │
                └──────────┬───────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │      H2 Database     │
                └──────────────────────┘

The domain model and application use case do not depend on persistence or web frameworks.

Testing

Run all tests with:

./mvnw test

The integration tests cover the five scenarios required by the technical test, including overlapping tariffs and priority selection.

Technologies
Java 21
Spring Boot
Spring Web MVC
Spring Data JPA
H2
Maven
JUnit
MockMvc