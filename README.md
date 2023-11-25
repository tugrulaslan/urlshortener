# Getting Started

## Technical Requirements
The following setup must exist in the development environment in order for execution and testing.
- Java 17
- Kotlin
- Gradle 3

The nix systems have gradle as gradlew, the binary executable may be different for other OSes.
This project has been formed in a Mac OS.

## Consuming Endpoints
The project has two endpoints to consume each carry out specific business functions. Examples are given in each respective call

### Creating short url
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/urls/www.dkb.de
```

### Receiving long url
```bash
curl -H "Content-Type: application/json" http://localhost:8080/api/v1/urls/9e50ade
```

## Running the Project
in the command line issue the following command.
```bash
./gradlew bootRun
```

## Running the Tests
The project has sufficient tests to cover all the functionality.
Tests are located underneath src/test/kotlin which can be fun from the preferred IDE or the command line

```bash
./gradlew clean test
```

## Project Information
The project is responsible for shortening long urls as a service. Customers are also able to receive the long url in exchange of the shorter version using REST endpoints