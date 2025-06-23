package com.bookstore.api.tests;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.SignupSteps;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

/**
 * Test suite to validate the user signup flow via API.
 */
@Feature("User Signup")
public class SignupTest {

    SignupSteps signupSteps = new SignupSteps();

    /**
     * Test to verify successful signup of a new user with a unique email address.
     * This test dynamically generates a new email to avoid conflicts.
     */
    @Story("Successful Signup")
    @Tag("positive")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Signup a new user - success scenario", groups = {"positive"})
    public void testSignupSuccess() {
        UserCredentials user = new UserCredentials("user" + System.currentTimeMillis() + "@mail.com", "pass123");
        Response res = signupSteps.signup(user);

        Allure.step("Assert response status code is 200", () ->
            assertEquals(res.statusCode(), 200)
        );
        Allure.step("Assert success message is 'User created successfully'", () ->
            assertEquals(res.jsonPath().getString("message"), "User created successfully")
        );
    }

    /**
     * Negative test to verify that attempting to sign up with an existing email
     * results in a 400 Bad Request response.
     */
    @Story("Duplicate Signup Attempt")
    @Tag("negative")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Signup with existing email - negative test", groups = {"negative"})
    public void testSignupDuplicate() {
        String email = "existing@mail.com";
        UserCredentials user = new UserCredentials(email, "pass123");

        signupSteps.signup(user); // First signup attempt
        Response res = signupSteps.signup(user); // Duplicate signup

        Allure.step("Assert duplicate signup response status code is 400", () ->
            assertEquals(res.statusCode(), 400)
        );
        Allure.step("Assert error message is 'Email already registered'", () ->
            assertEquals(res.jsonPath().getString("detail"), "Email already registered")
        );
    }
}
