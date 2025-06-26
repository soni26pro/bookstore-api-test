package com.bookstore.api.tests.positive;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.AuthSteps;
import com.bookstore.api.utils.ConfigLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Positive API Tests")
@Feature("User Authentication")
@Owner("QA Team")
public class AuthPositiveTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test to verify users can successfully login with valid credentials")
    public void testValidLogin() {
        // Get credentials from config.properties
        String email = ConfigLoader.getProperty("auth.email");
        String password = ConfigLoader.getProperty("auth.password");
        UserCredentials validUser = new UserCredentials(email, password);
        AuthSteps authSteps = new AuthSteps();

        // Perform login
        Allure.step("Attempting login with credentials from config: " + email, () -> {
            Response response = authSteps.login(validUser);

            // Assertions
            Allure.step("Assert login response status code is 200", () ->
                    assertThat(response.statusCode()).isEqualTo(200)
            );

            Allure.step("Assert token is returned in response", () ->
                    assertThat(response.jsonPath().getString("access_token")).isNotEmpty()
            );
        });
    }
}
