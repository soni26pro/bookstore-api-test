package com.bookstore.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.utils.RequestSpecFactory;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

/**
 * This class provides reusable steps for user signup (registration).
 * It is used to send new user registration requests to the API.
 */
public class SignupSteps {

    private static final Logger logger = LoggerFactory.getLogger(SignupSteps.class);

    /**
     * Sends a signup (registration) request to the /signup endpoint
     * using the provided user credentials (email and password).
     *
     * @param user the user credentials for registration
     * @return the HTTP response returned by the API
     */
    @Step("Signup a new user")
    public Response signup(UserCredentials user) {
        logger.info("Attempting to sign up user: {}", user.getEmail());

        Response response = given()
                .spec(RequestSpecFactory.getRequestSpec())
                .body(user)
                .when()
                .post("/signup")
                .then()
                .extract().response();

        logger.info("Signup response status code: {}", response.statusCode());
        // Optionally log response body for debugging
        // logger.debug("Signup response body: {}", response.getBody().asString());

        return response;
    }
}
