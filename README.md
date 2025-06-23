# 📚 Bookstore API — FastAPI + Java API Test Framework

This repository hosts both the **Bookstore FastAPI backend** (`main` branch) and the **Java TestNG + Allure test automation framework** (`tests` branch).

It follows a structured, CI-ready approach to enable backend development and test automation under the same project umbrella.

---

## 🚀 Technologies Used

### Backend (main branch)
- **FastAPI** – High-performance Python API framework
- **SQLAlchemy** – ORM for database operations
- **Uvicorn** – ASGI server to run FastAPI apps

### Test Automation (tests branch)
- **Java** – Primary language
- **TestNG** – Testing framework
- **REST Assured** – API test automation
- **AssertJ** – Fluent assertions
- **Maven** – Build and dependency manager
- **Allure Report** – Visual test reporting

---

## 📁 Repository Branches

| Branch  | Purpose                        |
|---------|--------------------------------|
| `main`  | FastAPI backend (Python)       |
| `tests` | Java TestNG API test framework |

---

## 🧪 Test Framework Overview (`tests` branch)

### 📁 Project Structure (`tests` branch)

```

bookstore-api-test/
├── pom.xml
├── testng.xml
├── config.properties
└── src/
└── test/java/com/bookstore/api/
├── models/     # POJOs for API payloads
├── steps/      # Reusable steps (BookSteps, AuthSteps, etc.)
├── utils/      # ConfigLoader, RequestSpecFactory
└── tests/      # TestNG classes (BookCrudTest, SignupTest)

```

---

## 🔁 Continuous Integration (CI/CD)

This project supports full CI with **Jenkins**, designed to:

1. ✅ Start the FastAPI backend from the `main` branch  
2. ✅ Run the Java TestNG automation suite from the `tests` branch  
3. ✅ Generate and publish **Allure reports** after tests

### 🛠 Jenkins Pipeline Flow

The `Jenkinsfile` (located in the `main` branch) does the following:

```

* Checkout the `main` branch (FastAPI backend)
* Set up Python environment and dependencies
* Start the FastAPI server on port 8000
* Checkout the `tests` branch
* Run `mvn test` for executing Java API tests
* Generate Allure reports from test output

````

> ✅ Jenkins agent must have Python, Java, Maven, and Allure CLI installed.

---

## ▶️ Prerequisites for Local Test Execution (tests branch)

- **Java 8+**
- **Maven 3.6+**
- **Allure Command-Line Tool**
- **FastAPI server running at** `http://localhost:8000`

---

## 🧪 How to Run Tests Locally

### 1. Start FastAPI backend (from `main` branch)

```bash
uvicorn main:app --reload
````

### 2. In a separate terminal (on `tests` branch), run tests

```bash
mvn clean test -Dapi.base.url=http://localhost:8000
```

---

## 📊 How to Generate and View Allure Report

```bash
# Generate HTML report
allure generate --clean allure-results

# Open the report in your browser
allure open

# OR one-liner
allure serve allure-results
```

> ⚠️ Make sure Allure CLI is installed and available in your system PATH.

---

## 🔐 Authentication

* User accounts are created via `/signup`
* Tokens are retrieved via `/login`
* All `/books` endpoints require JWT `Authorization: Bearer <token>`

---

## 📦 Dependencies

* Java 8+
* Maven 3.6+
* REST Assured
* AssertJ
* Allure TestNG adapter

---

## ✅ Key Features

* Clean separation of backend and tests via branches
* Reusable test steps (e.g., `BookSteps`, `SignupSteps`)
* Fluent assertions using AssertJ
* Strong data modeling with Java POJOs
* Dynamic request chaining with extracted values (tokens, IDs)
* Beautiful Allure reports with detailed logs
* Centralized environment config via `config.properties`

---

## 📝 License

MIT — feel free to use, fork, and adapt with attribution.

```
