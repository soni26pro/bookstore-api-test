# Bookstore API Test Automation

This project provides automated API tests for a Bookstore application. It is built using Java, TestNG, and REST Assured, following a structured approach to ensure maintainability and readability.

## 🚀 Technologies Used

- **Java**: The primary programming language.
- **TestNG**: A testing framework for organizing and running tests.
- **REST Assured**: A Java library for simplifying REST API testing.
- **AssertJ**: A fluent assertion library for Java, providing rich and readable assertions.
- **Maven**: A build automation tool used for dependency management and project build lifecycle.
- **Allure Report**: A flexible lightweight test reporting tool that provides clear and detailed reports.

## 📁 Project Structure

The project is organized into the following main directories:

- `src/main/java`: Contains the main source code, including:
    - `com/bookstore/api/models`: Data classes representing API request/response bodies (e.g., `Book`, `UserCredentials`).
    - `com/bookstore/api/steps`: Classes containing reusable test steps, often used in conjunction with TestNG tests (e.g., `AuthSteps`, `BookSteps`, `SignupSteps`).
    - `com/bookstore/api/utils`: Utility classes for common tasks like configuration loading and request specification creation (e.g., `ConfigLoader`, `RequestSpecFactory`).
- `src/test/java`: Contains the test classes:
    - `com/bookstore/api/tests`: Test suites and individual test cases (e.g., `BookCrudTest`, `SignupTest`).
- `src/test/resources`: Contains test resources:
    - `book_data.json`: Data file for book-related tests.
    - `config.properties`: Configuration file for test environment settings.
    - `logback.xml`: Configuration for logging.
- `allure-results`: Directory where Allure test results are generated after running tests.
- `allure-report`: Directory where the generated Allure HTML report is stored.
- `pom.xml`: Maven project object model file, defining dependencies and build configurations.
- `testng.xml`: TestNG suite configuration file, defining which tests to run and in what order.

## ✨ Benefits

- **Structured Tests**: Tests are organized using TestNG and step classes, improving readability and maintainability.
- **API Modeling**: Using model classes for API data ensures type safety and clarity.
- **Reusable Utilities**: Common functionalities like configuration loading and request setup are centralized in utility classes.
- **Detailed Reporting**: Integration with Allure Report provides comprehensive and easy-to-understand test results, including request/response details.
- **Maven Build**: Standardized build process using Maven simplifies dependency management and test execution.

## ▶️ Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher.
- **Maven**: Version 3.x or higher.
- **Allure Command-line Tool**: Required to generate and serve Allure reports. Follow the installation instructions for your operating system.

## ⚙️ How to Run Tests

1.  **Clone the repository** (if you haven't already).
2.  **Navigate to the project directory** in your terminal.
3.  **Run the tests using Maven**:
    ```bash
    mvn clean test
    ```
    This command will clean the target directory, compile the test code, and execute the tests defined in `testng.xml`.

## 📊 How to Generate and View Allure Report

After running the tests with `mvn clean test`, the test results will be generated in the `allure-results` directory.

1.  **Generate the HTML report**:
    ```bash
    allure generate --clean allure-results
    ```
    This command reads the results from `allure-results` and generates the HTML report in the `allure-report` directory. The `--clean` flag removes previous report data.

2.  **Serve the report**:
    ```bash
    allure open
    ```
    This command opens the generated report in your default web browser.

    Alternatively, you can generate and serve in one command:
    ```bash
    allure serve allure-results
    ```

*(Note: Ensure the Allure command-line tool is installed and accessible in your system's PATH.)*
