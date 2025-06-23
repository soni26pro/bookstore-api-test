# Bookstore API — FastAPI + Java API Test Framework

This repository hosts both the **Bookstore FastAPI backend** (`main` branch) and the **Java TestNG + Allure test automation framework** (`tests` branch).

It follows a structured, CI-ready approach to enable backend development and test automation under the same project umbrella.

---

## 🚀 Technologies Used

### Backend (main branch)
- **FastAPI**: High-performance Python API framework
- **SQLAlchemy**: ORM for database operations
- **Uvicorn**: ASGI server for running FastAPI apps

### Test Automation (tests branch)
- **Java**: Primary language
- **TestNG**: Testing framework
- **REST Assured**: API test automation library
- **AssertJ**: Fluent assertion library
- **Maven**: Build and dependency manager
- **Allure Report**: Beautiful test reports

---

## 📁 Repository Branches

| Branch  | Purpose                          |
|---------|----------------------------------|
| `main`  | FastAPI backend                  |
| `tests` | Java API test automation suite   |

---

## 🧪 Test Framework Overview (tests branch)

### Project Structure

bookstore-api-test/ (tests branch)
├── pom.xml
├── testng.xml
├── config.properties
└── src/
└── test/java/com/bookstore/api/
├── models/ # POJOs for API payloads
├── steps/ # Reusable steps (BookSteps, AuthSteps, etc.)
├── utils/ # ConfigLoader, RequestSpecFactory
└── tests/ # TestNG classes (BookCrudTest, SignupTest)


---

## 🔁 Continuous Integration (CI/CD)

This project is configured for Jenkins-based CI with the following pipeline:

- Clone `main` branch → start FastAPI server locally (using `uvicorn`)
- Clone `tests` branch → build and run TestNG tests
- Publish Allure reports

> ✅ Jenkinsfile is located in the `main` branch  
> ✅ You must install Python, Maven, and Allure CLI on the Jenkins agent

---

## ▶️ Prerequisites for Local Test Execution (tests branch)

- **Java 8+**
- **Maven 3.6+**
- **Allure Command-Line Tool**
- **Running FastAPI server at `http://localhost:8000`**

---

## 🧪 How to Run Tests Locally (tests branch)

```bash
# Start FastAPI backend (main branch)
uvicorn main:app --reload

# In a separate terminal, run tests (tests branch)
mvn clean test -Dapi.base.url=http://localhost:8000


📊 How to Generate and View Allure Report

# Generate HTML report from test results
allure generate --clean allure-results

# Open the report in your browser
allure open

# OR one-liner:
allure serve allure-results
Ensure allure is installed and added to your system's PATH

## Key Features

*   ✅ Clean separation of API and tests via branching
*   ✅ Reusable test steps (e.g., BookSteps, SignupSteps)
*   ✅ Fluent assertions using AssertJ
*   ✅ Data modeling with Java POJOs
*   ✅ Dynamic request chaining with extracted values (like tokens, IDs)
*   ✅ Allure integration for detailed visual reports
*   ✅ Flexible config management via config.properties
