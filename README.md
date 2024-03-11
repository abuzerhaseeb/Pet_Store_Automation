# Pet Store REST Assured Automation Project

## Introduction

This project is an automation suite for testing the functionality of a Pet Store REST API using the REST Assured library in Java. It includes tests for various endpoints related to pets, users, and store orders.

## Swagger Document

The API documentation for the Pet Store can be found on [Swagger Petstore](https://petstore.swagger.io/). This Swagger document provides detailed information about the available endpoints, request parameters, response formats, and authentication methods.

## Prerequisites

- Java Development Kit (JDK) installed on your machine
- Maven build automation tool installed
- IDE (Integrated Development Environment) such as IntelliJ IDEA or Eclipse

## Setup Instructions

1. Clone this repository to your local machine:

    ```bash
    git clone <repository-url>
    ```

2. Open the project in your preferred IDE.

3. Install the project dependencies using Maven:

    ```bash
    mvn clean install
    ```

4. Ensure that the required libraries and dependencies are downloaded and configured successfully.

## Running Tests

You can run the automated tests using the TestNG test runner. TestNG is configured to execute tests defined in the `testng.xml` file.

To run the tests:

1. Locate the `testng.xml` file in the project's root directory.

2. Right-click on the `testng.xml` file and select "Run As" > "TestNG Suite" in your IDE.

3. Alternatively, you can execute the tests using Maven:

    ```bash
    mvn test
    ```

## Project Structure

- **src/main/java**: Contains the main Java source code for the project.
- **src/test/java**: Contains the test classes for automated testing.
- **src/test/resources**: Contains resources such as test data and configuration files.
- **testng.xml**: TestNG configuration file defining test suites and test classes.
- **pom.xml**: Maven project configuration file specifying project dependencies and build plugins.

## Test Cases

The test cases cover various scenarios related to pets, users, and store orders. Each test case is designed to verify specific functionalities and endpoints of the Pet Store API.

## Reporting

Test execution reports are generated using ExtentReports. After running the tests, you can find the HTML reports in the `reports` directory. These reports provide detailed information about test execution, including test results, logs, and screenshots (if configured).

## Contributors

- [Abuzer Haseeb](https://github.com/abuzerhaseeb)
