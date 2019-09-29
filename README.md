[![Build Status](https://travis-ci.com/kuba-04/moneyapp.svg?branch=master)](https://travis-ci.com/kuba-04/moneyapp)

# moneyapp

This simple app allows you to create accounts and transfer money between them.

The application was build with:
- Maven
- Java 11
- Micronaut 1.2.2

## Getting Started

If you want to quickly get the server up and running, run these instructions:
```
./mvnw install
./mvnw exec:java
```
By default the application is running on localhost port 8080.

Once the application is started you can make HTTP calls to the server.

To see the available endpoints go to:
```
http://localhost:8080/swagger/money-app-1.0.yml
```
where 1.0 is the app version

## Running the tests

```
./mvnw test
```
