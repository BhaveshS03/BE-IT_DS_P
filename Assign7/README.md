# Assignment 7

## Description
A SOAP Web Service application demonstrating a simple calculator. The project consists of a server (`CalculatorWebService`) exposing a `getNumber` method to add two numbers, and a client servlet (`CalculatorWebServiceClient`) that consumes the web service.

## Files
- CalculatorWebService/src/java/com/unique/Calculator.java : The Web Service implementation.
- CalculatorWebServiceClient/src/java/Calculator.java : The client servlet that accesses the Web Service.

## Requirements
- Java 8
- GlassFish / Tomcat Server
- IDE like NetBeans for deploying the web projects.

## How to Run

### Java
```bash
# This is a web project. Open in an IDE like NetBeans or Eclipse.
# 1. Deploy the CalculatorWebService project to a local application server (e.g. GlassFish).
# 2. Deploy the CalculatorWebServiceClient project.
# 3. Access the deployed client servlet via your web browser to perform addition.
```
