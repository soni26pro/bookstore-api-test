package com.bookstore.api.tests.negative;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.AuthSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Negative API Tests")
@Feature("User Authentication")
@Owner("QA Team")
public class AuthNegativeTest {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify system rejects login with invalid credentials")
    public void testInvalidLogin() {
        // Arrange: create invalid credentials
        UserCredentials invalidUser = new UserCredentials("invalid@example.com", "wrongpassword");
        AuthSteps authSteps = new AuthSteps();

        // Act: attempt to login
        Allure.step("Attempting login with known invalid credentials", () -> {
            Response response = authSteps.login(invalidUser);

            // Assert: verify login fails
            Allure.step("Assert login response status code is 401 or 400", () ->
                    assertThat(response.statusCode()).isIn(400, 401)
            );
            Allure.step("Assert error message is present", () ->
                    assertThat(response.getBody().asString()).isNotEmpty()
            );
        });
    }
}
