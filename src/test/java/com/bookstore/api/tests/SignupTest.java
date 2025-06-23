package com.bookstore.api.tests;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.SignupSteps;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Feature("User Signup")
public class SignupTest {

    SignupSteps signupSteps = new SignupSteps();

    @Story("Successful Signup")
    @Test(description = "Signup a new user - success scenario")
    public void testSignupSuccess() {
        UserCredentials user = new UserCredentials("user" + System.currentTimeMillis() + "@mail.com", "pass123");
        Response res = signupSteps.signup(user);

        assertEquals(res.statusCode(), 200);
        assertEquals(res.jsonPath().getString("message"), "User created successfully");
    }

    @Story("Duplicate Signup Attempt")
    @Test(description = "Signup with existing email - negative test")
    public void testSignupDuplicate() {
        String email = "existing@mail.com";
        UserCredentials user = new UserCredentials(email, "pass123");

        signupSteps.signup(user);
        Response res = signupSteps.signup(user);

        assertEquals(res.statusCode(), 400);
        assertEquals(res.jsonPath().getString("detail"), "Email already registered");
    }
}
