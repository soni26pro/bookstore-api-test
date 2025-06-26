package com.bookstore.api.tests.positive;

import com.bookstore.api.models.Book;
import com.bookstore.api.steps.BookSteps;
import com.bookstore.api.utils.ConfigLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Positive API Tests")
@Feature("Book CRUD Operations")
public class BookCrudPositiveTest {
    private final BookSteps bookSteps = new BookSteps();
    private final String authToken = ConfigLoader.getProperty("auth.token");
    private static int bookId;

    @Test(priority = 1)
    @Story("Create Book")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test to verify successful creation of a new book")
    public void testCreateBook() {
        Book book = new Book("Positive Book", "Author Pos", 2024, "A positive test book.");
        Response response = bookSteps.createBook(authToken, book);
        Allure.step("Assert create book status code is 200", () ->
                assertThat(response.statusCode()).isEqualTo(200)
        );
        bookId = response.jsonPath().getInt("id");
        Allure.step("Assert created book ID is positive", () ->
                assertThat(bookId).isPositive()
        );
    }

    @Test(priority = 2, dependsOnMethods = "testCreateBook")
    @Story("Read Book")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify successful retrieval of book details by ID")
    public void testReadBook() {
        Response response = bookSteps.getBook(authToken, bookId);
        Allure.step("Assert read book status code is 200", () ->
                assertThat(response.statusCode()).isEqualTo(200)
        );
        Allure.step("Assert book name matches", () ->
                assertThat(response.jsonPath().getString("name")).isEqualTo("Positive Book")
        );
    }

    @Test(priority = 3, dependsOnMethods = "testReadBook")
    @Story("Update Book")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify successful update of a book's details")
    public void testUpdateBook() {
        Book updatedBook = new Book("Updated Positive Book", "Author Pos", 2025, "Updated description.");
        Response response = bookSteps.updateBook(authToken, bookId, updatedBook);
        Allure.step("Assert update book status code is 200", () ->
                assertThat(response.statusCode()).isEqualTo(200)
        );
        Allure.step("Assert book name is updated", () ->
                assertThat(response.jsonPath().getString("name")).isEqualTo("Updated Positive Book")
        );
    }

    @Test(priority = 4, dependsOnMethods = "testUpdateBook")
    @Story("Delete Book")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test to verify successful deletion of a book")
    public void testDeleteBook() {
        Response response = bookSteps.deleteBook(authToken, bookId);
        Allure.step("Assert delete book status code is 200", () ->
                assertThat(response.statusCode()).isEqualTo(200)
        );
        // Verify book is deleted
        Response getResponse = bookSteps.getBook(authToken, bookId);
        Allure.step("Assert get after delete status code is 404", () ->
                assertThat(getResponse.statusCode()).isEqualTo(404)
        );
    }
}
