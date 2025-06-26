package com.bookstore.api.tests.negative;

import com.bookstore.api.models.Book;
import com.bookstore.api.steps.BookSteps;
import com.bookstore.api.utils.ConfigLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Negative API Tests")
@Feature("Create Book")
@Owner("QA Team")
public class CreateBookNegativeTest {
    private final BookSteps bookSteps = new BookSteps();
    private final String authToken = ConfigLoader.getProperty("auth.token");

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify system rejects book creation with invalid data")
    public void testCreateBookWithInvalidData() {
        Book invalidBook = new Book("", "", -1, ""); // Invalid fields
        Allure.step("Prepare invalid book data for creation", () -> {
            Allure.step("Book payload: " + invalidBook);
        });
        Allure.step("Attempt to create a book with invalid data", () -> {
            Response response = bookSteps.createBook(authToken, invalidBook);
            Allure.step("Assert create book with invalid data returns 400 or 422", () ->
                    assertThat(response.statusCode()).isIn(400, 422)
            );
            Allure.step("Assert error message is present in response body", () ->
                    assertThat(response.getBody().asString()).isNotEmpty()
            );
            Allure.step("Response body: " + response.getBody().asString());
        });
    }
}
