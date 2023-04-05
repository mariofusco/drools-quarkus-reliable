# Drools 7.x + Quarkus example

## Description

A simple rule service to validate `LoanApplication` fact.

The intent of this project is demonstrating a basic integration between an existing Drools project and Quarkus. It just wraps a KieSession in a rest endpoint, but of course the most interesting Quarkus features like the dev mode and the possibility of generating a native image are missing and they will be available only migrating to Kogito.

## Installing and Running

### Prerequisites

You will need:
  - Java 11+ installed
  - Environment variable JAVA_HOME set accordingly
  - Maven 3.6.2+ installed

### Package and Run in JVM mode

```sh
mvn clean package
java -jar quarkus-app/target/quarkus-app/quarkus-run.jar
```

or on windows

```sh
mvn clean package
java -jar quarkus-app\target\quarkus-app\quarkus-run.jar
```

## Example Usage

### POST /session

Create a new drools session returning its id

```sh
curl -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' http://localhost:8080/session
```

### POST /session/{sessionId}/find-approved-amount

Insert a list of loan application and returns the total amount of approved loan applications on a session:

```sh
curl -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' -d '{"maxAmount":5000,"loanApplications":[{"id":"ABC10001","amount":2000,"deposit":100,"applicant":{"age":45,"name":"John"}}, {"id":"ABC10002","amount":5000,"deposit":100,"applicant":{"age":25,"name":"Paul"}}, {"id":"ABC10015","amount":1000,"deposit":100,"applicant":{"age":12,"name":"George"}}]}' http://localhost:8080/session/0/find-approved-amount
```
or on windows

```sh
curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" -d "{\"maxAmount\":5000,\"loanApplications\":[{\"id\":\"ABC10001\",\"amount\":2000,\"deposit\":100,\"applicant\":{\"age\":45,\"name\":\"John\"}}, {\"id\":\"ABC10002\",\"amount\":5000,\"deposit\":100,\"applicant\":{\"age\":25,\"name\":\"Paul\"}}, {\"id\":\"ABC10015\",\"amount\":1000,\"deposit\":100,\"applicant\":{\"age\":12,\"name\":\"George\"}}]}" http://localhost:8080/session/0/find-approved-amount
```

As response an array of loan applications is returned.

Example response:

```json
[
  {
    "id":"ABC10001",
    "applicant":{
      "name":"John",
      "age":45
    },
    "amount":2000,
    "deposit":100,
    "approved":true
  }
]
```
