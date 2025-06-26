# Bookstore API Test Framework

## Overview
This project is a comprehensive API testing framework for a Bookstore application, built using Java, RestAssured, TestNG, and Allure. It follows a structured approach to API testing with separate test cases for positive and negative scenarios, providing thorough test coverage for authentication, user management, and book CRUD operations.

## Project Structure

```
bookstore-api-test/
├── src/
│   ├── main/java/com/bookstore/api/
│   │   ├── models/             # Data models used for requests and responses
│   │   │   ├── Book.java       # Book entity model
│   │   │   └── UserCredentials.java # User authentication model
│   │   ├── steps/              # API operation steps
│   │   │   ├── AuthSteps.java  # Authentication API steps
│   │   │   ├── BookSteps.java  # Book API steps
│   │   │   └── SignupSteps.java # User signup steps
│   │   └── utils/              # Utility classes
│   │       ├── ConfigLoader.java # Config property management
│   │       └── RequestSpecFactory.java # RestAssured request specifications
│   └── test/java/com/bookstore/api/tests/
│       ├── positive/           # Positive test scenarios
│       │   ├── AuthPositiveTest.java      # Authentication positive tests
│       │   ├── BookCrudPositiveTest.java  # Book CRUD positive tests
│       │   ├── SignupPositiveTest.java    # User signup positive tests
│       │   └── TestSuiteSetup.java        # Test suite initialization
│       └── negative/           # Negative test scenarios
│           ├── AuthNegativeTest.java       # Authentication negative tests
│           ├── CreateBookNegativeTest.java # Book creation negative tests
│           ├── DeleteBookNegativeTest.java # Book deletion negative tests
│           ├── ReadBookNegativeTest.java   # Book retrieval negative tests
│           ├── SignupNegativeTest.java     # User signup negative tests
│           └── UpdateBookNegativeTest.java # Book update negative tests
├── allure-results/             # Allure test execution results
├── allure-report/              # Generated Allure HTML reports
├── pom.xml                     # Maven project configuration
└── testng.xml                  # TestNG test suite configuration
```

## Key Features

1. **Structured Test Organization**:
   - Clear separation between positive and negative test scenarios
   - Modular test components with reusable steps

2. **Comprehensive Test Coverage**:
   - Authentication (login, token validation)
   - User Management (signup, validation)
   - Book CRUD Operations (create, read, update, delete)
   - Validation testing for all API endpoints

3. **Robust Test Reporting**:
   - Detailed Allure reports with Epic, Feature, Story categorization
   - Test severity classification
   - Step-by-step test execution details including API requests and responses
   - Attachment of request/response headers for debugging

4. **Dynamic Test Data**:
   - JavaFaker integration for generating random test data
   - Avoids test data conflicts and dependencies

5. **Configuration Management**:
   - Externalized configuration using properties files
   - Support for different test environments

## Test Categories

### Positive Tests
Tests that verify the API behaves correctly with valid inputs:
- **Authentication**: Successful login with valid credentials
- **User Signup**: Successful user registration
- **Book CRUD**: Complete lifecycle testing of books (create, read, update, delete)

### Negative Tests
Tests that verify the API handles invalid inputs appropriately:
- **Invalid Authentication**: Login attempts with incorrect credentials
- **Duplicate User Registration**: Attempts to register with existing email
- **Invalid Book Operations**: 
  - Creating books with invalid data
  - Reading non-existent books
  - Updating books with invalid data
  - Deleting non-existent books

## Allure Reporting

This project uses Allure for enhanced test reporting with:

- **Hierarchical Test Organization**:
  - Epics: High-level test categories (Positive/Negative API Tests)
  - Features: Functional areas (Authentication, Book CRUD, etc.)
  - Stories: Specific test scenarios

- **Test Details**:
  - Severity levels (Blocker, Critical, Normal)
  - Test descriptions
  - Test ownership
  - Step-by-step execution logs

- **API Request Details**:
  - HTTP methods, URLs, and status codes
  - Request and response headers
  - Request and response bodies

## Running the Tests

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Allure command-line tools (optional, for report generation)

### Running Tests with Maven

1. **Run all tests**:
   ```bash
   mvn clean test
   ```

2. **Run specific test classes or packages**:
   ```bash
   # Run only positive tests
   mvn clean test -Dtest=*PositiveTest
   
   # Run only authentication tests
   mvn clean test -Dtest=Auth*Test
   
   # Run a specific test class
   mvn clean test -Dtest=BookCrudPositiveTest
   ```

3. **Run with a specific TestNG XML file**:
   ```bash
   mvn clean test -DsuiteXmlFile=testng.xml
   ```

### Generating Allure Reports

1. **Generate the report**:
   ```bash
   mvn allure:report
   ```

2. **Generate and open the report**:
   ```bash
   mvn allure:serve
   ```

3. **Using the Allure command-line**:
   ```bash
   allure generate allure-results -o allure-report --clean
   allure open allure-report
   ```

## Best Practices Implemented

1. **Clean Code Structure**:
   - Separation of concerns (models, steps, tests)
   - Consistent naming conventions
   - Comprehensive documentation

2. **Testing Best Practices**:
   - Isolated and independent tests
   - Appropriate assertions
   - Thorough error handling

3. **Reporting Best Practices**:
   - Detailed test steps
   - Clear categorization
   - Comprehensive API request/response logging

## Getting Started

1. Clone the repository
2. Ensure the Bookstore API is running at the URL specified in `config.properties`
3. Run the tests with `mvn clean test`
4. Generate reports with `mvn allure:serve`

## Note
This framework is designed to test a RESTful Bookstore API that allows users to register, login, and perform CRUD operations on books. Ensure the API is running before executing tests.
