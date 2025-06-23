# Bookstore API Test Automation

## 🚀 Technologies Used
- Java + TestNG
- REST Assured
- Allure Report
- Maven

## ✅ Test Cases
- Signup (success)
- Signup (duplicate email)

## ▶️ How to Run Tests
```bash
mvn clean test
```

## 📊 Generate Allure Report

After running the tests, you can generate and view the detailed Allure report. The tests are configured with Allure annotations (`@Step`, `@Feature`, `@Story`) and attachments (request/response bodies) to provide comprehensive details in the report.

1.  **Generate the report:**
    ```bash
    allure generate --clean allure-results
    ```
2.  **Serve the report:**
    ```bash
    allure open
    ```
    Alternatively, you can generate and serve in one command:
    ```bash
    allure serve allure-results
    ```
    *(Note: This requires the Allure command-line tool to be installed and in your system's PATH. If you are on Windows, you might need to use `rmdir /s /q allure-results` before `mvn clean test` if you encounter issues with cleaning the results directory.)*
