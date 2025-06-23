package com.bookstore.api.tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bookstore.api.models.Book;
import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.AuthSteps;
import com.bookstore.api.steps.BookSteps;
import com.bookstore.api.steps.SignupSteps;
import com.github.javafaker.Faker;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

/**
 * End-to-end test class to validate the full CRUD lifecycle of a book
 * using REST APIs with JWT-based authentication.
 */
@Feature("Book Management")
public class BookCrudTest {

    AuthSteps authSteps = new AuthSteps();
    BookSteps bookSteps = new BookSteps();
    SignupSteps signupSteps = new SignupSteps();

    private int bookId;
    private String authToken;

    /**
     * Prepares the environment before running the tests:
     * <ul>
     *     <li>Registers a new user</li>
     *     <li>Performs login and extracts auth token</li>
     *     <li>Initializes a test book instance</li>
     * </ul>
     */
    @BeforeClass
    public void setup() {
        Faker faker = new Faker();

        // Generate random user credentials
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        UserCredentials user = new UserCredentials(email, password);

        // Register user
        Response signupRes = signupSteps.signup(user);
        assertThat(signupRes.statusCode()).isEqualTo(200);

        // Login and get token
        Response loginRes = authSteps.login(user);
        assertThat(loginRes.statusCode()).isEqualTo(200);
        authToken = authSteps.extractAccessToken(loginRes);
        assertThat(authToken).isNotEmpty();
    }

    /**
     * Test for creating a new book via API.
     */
    @Story("Create Book")
    @Tag("positive")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Create a new book", groups = {"positive"})
    public void testCreateBook() {
        Book testBook = new Book("The Sun Also Rises", "Fredric Hodkiewicz", 2020, "A classic novel by Hemingway.");

        Response createRes = bookSteps.createBook(authToken, testBook);
        assertThat(createRes.statusCode()).isEqualTo(200);
        assertThat(createRes.getHeader("Content-Type")).contains("application/json");
        bookId = createRes.jsonPath().getInt("id");
        assertThat(bookId).isPositive();
    }

    /**
     * Test for retrieving the previously created book.
     */
    @Story("Read Book")
    @Tag("positive")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Read the created book", dependsOnMethods = "testCreateBook", groups = {"positive"})
    public void testReadBook() {
        Book testBook = new Book("The Sun Also Rises", "Fredric Hodkiewicz", 2020, "A classic novel by Hemingway.");

        Response getRes = bookSteps.getBook(authToken, bookId);
        assertThat(getRes.statusCode()).isEqualTo(200);
        assertThat(getRes.getHeader("Content-Type")).contains("application/json");
        assertThat(getRes.jsonPath().getString("name")).isEqualTo(testBook.getName());
        assertThat(getRes.jsonPath().getString("author")).isEqualTo(testBook.getAuthor());
        assertThat(getRes.jsonPath().getInt("published_year")).isEqualTo(testBook.getPublished_year());
    }

    /**
     * Test for updating the existing book with new details.
     */
    @Story("Update Book")
    @Tag("positive")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Update the created book", dependsOnMethods = "testReadBook", groups = {"positive"})
    public void testUpdateBook() {
        Book testBook = new Book("The Sun Also Rises", "Fredric Hodkiewicz", 2020, "A classic novel by Hemingway.");
        testBook.setName("Updated Book Name");
        testBook.setPublished_year(2022);

        Response updateRes = bookSteps.updateBook(authToken, bookId, testBook);
        assertThat(updateRes.statusCode()).isEqualTo(200);
        assertThat(updateRes.getHeader("Content-Type")).contains("application/json");
        assertThat(updateRes.jsonPath().getString("name")).isEqualTo(testBook.getName());
        assertThat(updateRes.jsonPath().getInt("published_year")).isEqualTo(testBook.getPublished_year());
    }

    /**
     * Test for deleting the book and verifying its removal.
     */
    @Story("Delete Book")
    @Tag("positive")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Delete the created book", dependsOnMethods = "testUpdateBook", groups = {"positive"})
    public void testDeleteBook() {
        Response deleteRes = bookSteps.deleteBook(authToken, bookId);
        assertThat(deleteRes.statusCode()).isEqualTo(200);
        assertThat(deleteRes.getHeader("Content-Type")).contains("application/json");

        Response getAfterDelete = bookSteps.getBook(authToken, bookId);
        assertThat(getAfterDelete.statusCode()).isEqualTo(404);
    }

    /**
     * Test for reading a book ID that does not exist in the database.
     */
    @Story("Read Non-Existent Book")
    @Tag("negative")
    @Severity(SeverityLevel.MINOR)
    @Test(description = "Attempt to read a non-existent book", groups = {"negative"})
    public void testReadNonExistentBook() {
        int invalidBookId = 9999999;
        Response res = bookSteps.getBook(authToken, invalidBookId);
        assertThat(res.statusCode()).isEqualTo(404); // Not found
        assertThat(res.getHeader("Content-Type")).contains("application/json");
    }

    /**
     * Cleanup method to ensure no leftover data exists after the test run.
     * This is useful in case of partial test failures.
     */
    @AfterClass
    public void cleanup() {
        if (bookId > 0) {
            bookSteps.deleteBook(authToken, bookId);
        }
    }

    /**
     * Test for creating a new book with invalid data.
     */
    @Story("Create Book")
    @Tag("negative")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Attempt to create a book with invalid data", groups = {"negative"})
    public void testCreateBookWithInvalidData() {
        // Attempt to create a book with missing required fields (e.g., name)
        Book invalidBook = new Book(null, "Invalid Author", 2023, "Book with missing name.");

        Response createRes = bookSteps.createBook(authToken, invalidBook);
        assertThat(createRes.statusCode()).isEqualTo(500);
        assertThat(createRes.getHeader("Content-Type")).contains("text/plain; charset=utf-8");
    }
}
