package com.bookstore.api.tests.positive;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.steps.AuthSteps;
import com.bookstore.api.steps.SignupSteps;
import com.bookstore.api.utils.ConfigLoader;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeSuite;

@Epic("Test Environment Setup")
@Feature("User Authentication Setup")
public class TestSuiteSetup {

    @BeforeSuite
    @Story("Create Test User and Store Authentication Token")
    @Description("Creates a new random user, signs up, logs in, and stores the auth token for subsequent tests")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("QA Team")
    public void setup() {
        System.out.println("Setting up test suite...");
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        // Generate a stronger, more consistent password that meets common requirements
        String password = faker.internet().password(8, 12, true, true, true); // min 8, max 12 chars, with special chars, numbers, and uppercase
        UserCredentials user = new UserCredentials(email, password);
        AuthSteps authSteps = new AuthSteps();
        SignupSteps signupSteps = new SignupSteps();

        Allure.step("Creating random test user with email: " + email + " and secure password");

        Response signupRes = signupSteps.signup(user);
        Allure.step("Assert signup response status code is 200", () ->
                Assertions.assertThat(signupRes.statusCode()).isEqualTo(200)
        );

        Response loginRes = authSteps.login(user);
        Allure.step("Assert login response status code is 200", () ->
                Assertions.assertThat(loginRes.statusCode()).isEqualTo(200)
        );

        String authToken = authSteps.extractAccessToken(loginRes);
        Allure.step("Assert auth token is not empty", () ->
                Assertions.assertThat(authToken).isNotEmpty()
        );

        Allure.step("Storing authentication data in config.properties", () -> {
            ConfigLoader.setProperty("auth.token", authToken);
            ConfigLoader.setProperty("auth.email", email);
            ConfigLoader.setProperty("auth.password", password);
            ConfigLoader.save();
        });
    }
}
