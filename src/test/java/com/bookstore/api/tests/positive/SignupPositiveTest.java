package com.bookstore.api.tests.positive;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.SignupSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test suite to validate the user signup flow via API.
 */
@Epic("Positive API Tests")
@Feature("User Signup")
@Owner("QA Team")
public class SignupPositiveTest {

    SignupSteps signupSteps = new SignupSteps();

    /**
     * Test to verify successful signup of a new user with a unique email address.
     * This test dynamically generates a new email to avoid conflicts.
     */
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test to verify successful signup of a new user with a dynamically generated email")
    public void testSignupSuccess() {
        String email = "user" + System.currentTimeMillis() + "@mail.com";
        String password = "pass123";
        UserCredentials user = new UserCredentials(email, password);

        Allure.step("Creating new user with email: " + email, () -> {
            Response res = signupSteps.signup(user);

            Allure.step("Assert response status code is 200", () ->
                    assertEquals(res.statusCode(), 200)
            );
            Allure.step("Assert success message is 'User created successfully'", () ->
                    assertEquals(res.jsonPath().getString("message"), "User created successfully")
            );
        });
    }
}
