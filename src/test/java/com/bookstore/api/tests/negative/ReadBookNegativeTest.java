package com.bookstore.api.tests.negative;

import com.bookstore.api.steps.BookSteps;
import com.bookstore.api.utils.ConfigLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Negative API Tests")
@Feature("Read Book")
@Owner("QA Team")
public class ReadBookNegativeTest {
    private final BookSteps bookSteps = new BookSteps();
    private final String authToken = ConfigLoader.getProperty("auth.token");

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test to verify system handles attempts to read non-existent books appropriately")
    public void testReadNonExistentBook() {
        int nonExistentBookId = 999999;
        Allure.step("Prepare non-existent book ID for reading", () -> {
            Allure.step("Book ID: " + nonExistentBookId);
        });
        Allure.step("Attempt to read a non-existent book", () -> {
            Response response = bookSteps.getBook(authToken, nonExistentBookId);
            Allure.step("Assert read non-existent book returns 404", () ->
                    assertThat(response.statusCode()).isEqualTo(404)
            );
            Allure.step("Assert error message is present in response body", () ->
                    assertThat(response.getBody().asString()).isNotEmpty()
            );
            Allure.step("Response body: " + response.getBody().asString());
        });
    }
}
