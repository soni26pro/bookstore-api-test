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
- **Detailed Reporting**: Integration with Allure Report provides comprehensive and easy-to-understand test results, including request/response details and **granular assertion results** via `Allure.step()`.
- **Maven Build**: Standardized build process using Maven simplifies dependency management and test execution.

## 🧪 Testing Strategy

Our testing strategy focuses on providing comprehensive and maintainable API test coverage.

-   **Test Flow Approach**: Tests are designed to follow typical user workflows and API interactions, including request chaining where the output of one API call (like authentication tokens or created resource IDs) is used as input for subsequent calls. This is evident in tests like `BookCrudTest` which covers the full lifecycle of a book.
-   **Reliability and Maintainability**:
    -   **Structured Code**: The framework uses a layered approach with dedicated 'steps' classes (`AuthSteps`, `BookSteps`, `SignupSteps`) to encapsulate API interactions, promoting reusability and keeping test methods clean and focused on test logic.
    -   **Strong Assertions**: AssertJ is used for fluent and readable assertions, making it clear what is being validated in each test.
    -   **Data Modeling**: API request and response payloads are represented by Java model classes, ensuring type safety and reducing errors.
    -   **Configuration Management**: Externalizing configuration in `config.properties` and using `ConfigLoader` allows easy switching between environments without code changes.
    -   **Logging**: Integrated logging provides visibility into test execution and API responses, aiding in debugging.
-   **Challenges Faced**:
    -   Handling dynamic data like authentication tokens and generated resource IDs requires careful implementation of request chaining.
    -   Ensuring comprehensive negative test coverage for various error conditions (e.g., invalid input, unauthorized access) requires thorough API analysis. We have started expanding coverage to include scenarios like **creating resources with invalid data**.
    -   Validating API responses comprehensively, including **status codes, response payloads, and headers (like Content-Type)**.
    -   Setting up and managing the test environment and dependencies can sometimes pose challenges.

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

## 🔄 Continuous Integration (CI)

This project includes a basic CI pipeline configured using **GitHub Actions**. The workflow is defined in `.github/workflows/ci.yml`.

The CI pipeline is triggered on pushes to the `main` branch and includes steps to:
- Checkout the code.
- Set up the Java Development Kit (JDK).
- Build the project and run the tests using Maven.

You can extend this workflow to include additional steps like publishing Allure reports or integrating with other tools.
