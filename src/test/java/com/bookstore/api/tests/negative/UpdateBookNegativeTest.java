package com.bookstore.api.tests.negative;

import com.bookstore.api.models.Book;
import com.bookstore.api.steps.BookSteps;
import com.bookstore.api.utils.ConfigLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Negative API Tests")
@Feature("Update Book")
@Owner("QA Team")
public class UpdateBookNegativeTest {
    private final BookSteps bookSteps = new BookSteps();
    private final String authToken = ConfigLoader.getProperty("auth.token");

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify system rejects book update with invalid data or non-existent ID")
    public void testUpdateBookWithInvalidData() {
        int nonExistentBookId = 999999;
        Book invalidBook = new Book("", "", -1, "");
        Allure.step("Attempting to update non-existent book with invalid data", () -> {
            Response response = bookSteps.updateBook(authToken, nonExistentBookId, invalidBook);
            Allure.step("Assert update non-existent or invalid book returns 400, 404, or 422", () ->
                    assertThat(response.statusCode()).isIn(400, 404, 422)
            );
        });
    }
}
