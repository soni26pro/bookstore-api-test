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
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Feature("Book Management")
public class BookCrudTest {

    AuthSteps authSteps = new AuthSteps();
    BookSteps bookSteps = new BookSteps();
    SignupSteps signupSteps = new SignupSteps();

    private int bookId;
    private Book testBook;
    private String authToken;

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

        // Initialize single test book
        testBook = new Book("The Sun Also Rises", "Fredric Hodkiewicz", 2020, "A classic novel by Hemingway.");

    }

    @Story("Create Book")
    @Test(description = "Create a new book")
    public void testCreateBook() {
        Response createRes = bookSteps.createBook(authToken, testBook);
        assertThat(createRes.statusCode()).isEqualTo(200);
        bookId = createRes.jsonPath().getInt("id");
        assertThat(bookId).isPositive();
    }

    @Story("Read Book")
    @Test(description = "Read the created book", dependsOnMethods = "testCreateBook")
    public void testReadBook() {
        Response getRes = bookSteps.getBook(authToken, bookId);
        assertThat(getRes.statusCode()).isEqualTo(200);
        assertThat(getRes.jsonPath().getString("name")).isEqualTo(testBook.getName());
        assertThat(getRes.jsonPath().getString("author")).isEqualTo(testBook.getAuthor());
        assertThat(getRes.jsonPath().getInt("published_year")).isEqualTo(testBook.getPublished_year());
    }

    @Story("Update Book")
    @Test(description = "Update the created book", dependsOnMethods = "testReadBook")
    public void testUpdateBook() {
        // Update book details
        testBook.setName("Updated Book Name");
        testBook.setPublished_year(2022);

        Response updateRes = bookSteps.updateBook(authToken, bookId, testBook);
        assertThat(updateRes.statusCode()).isEqualTo(200);

        // Verify updated details in the response
        assertThat(updateRes.jsonPath().getString("name")).isEqualTo(testBook.getName());
        assertThat(updateRes.jsonPath().getInt("published_year")).isEqualTo(testBook.getPublished_year());
    }

    @Story("Delete Book")
    @Test(description = "Delete the created book", dependsOnMethods = "testUpdateBook")
    public void testDeleteBook() {
        Response deleteRes = bookSteps.deleteBook(authToken, bookId);
        assertThat(deleteRes.statusCode()).isEqualTo(200);

        // Ensure it's deleted
        Response getAfterDelete = bookSteps.getBook(authToken, bookId);
        assertThat(getAfterDelete.statusCode()).isEqualTo(404);
    }

    @Story("Read Non-Existent Book")
    @Test(description = "Attempt to read a non-existent book")
    public void testReadNonExistentBook() {
        int invalidBookId = 9999999;
        Response res = bookSteps.getBook(authToken, invalidBookId);
        assertThat(res.statusCode()).isEqualTo(404);
    }

    @AfterClass
    public void cleanup() {
        if (bookId > 0) {
            bookSteps.deleteBook(authToken, bookId);
        }
    }
}
