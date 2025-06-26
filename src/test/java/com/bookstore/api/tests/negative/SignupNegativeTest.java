package com.bookstore.api.tests.negative;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.SignupSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test suite to validate the user signup flow via API.
 */
@Epic("Negative API Tests")
@Feature("User Signup")
@Owner("QA Team")
public class SignupNegativeTest {

    SignupSteps signupSteps = new SignupSteps();

    /**
     * Negative test to verify that attempting to sign up with an existing email
     * results in a 400 Bad Request response.
     */
    @Test(description = "Signup with existing email - negative test", groups = {"negative"})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test to verify system prevents duplicate user registration with the same email")
    public void testSignupDuplicate() {
        String email = "existing@mail.com";
        UserCredentials user = new UserCredentials(email, "pass123");

        Allure.step("First signup attempt with email: " + email, () -> {
            signupSteps.signup(user); // First signup attempt
        });

        Allure.step("Attempt duplicate signup with same email", () -> {
            Response res = signupSteps.signup(user); // Duplicate signup

            Allure.step("Assert duplicate signup response status code is 400", () ->
                    assertEquals(res.statusCode(), 400)
            );
            Allure.step("Assert error message is 'Email already registered'", () ->
                    assertEquals(res.jsonPath().getString("detail"), "Email already registered")
            );
        });
    }
}
