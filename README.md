# APIAUTAssignment

## Overview

This project is a Java-based API testing framework using RestAssured and JUnit. It includes utilities for JSON manipulation and test cases for interacting with a sample API.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

- `src/main/java`: Contains the main utility classes.
  - `utils/JsonPathUtil.java`: Utility class for reading, updating, and writing JSON files.
  - `base/BaseTest.java`: Base class for setting up RestAssured configuration.
- `src/test/java`: Contains the test classes.
  - `ApiTest.java`: Test class with API test cases.
- `src/test/resources`: Contains the JSON request payloads.
  - `request.json`: Sample JSON file for creating a user.

## Dependencies

The project uses the following dependencies:

- `rest-assured`: For API testing.
- `junit`: For writing and running test cases.
- `jackson-databind`: For JSON processing.

## Setup

1. Clone the repository:
   ```sh
   git clone <repository-url>
   cd APIAUTAssignment
