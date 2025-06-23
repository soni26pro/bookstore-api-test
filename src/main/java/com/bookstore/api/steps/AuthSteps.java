package com.bookstore.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.utils.RequestSpecFactory;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

/**
 * This class provides reusable steps related to user authentication,
 * such as login and access token extraction.
 */
public class AuthSteps {

    private static final Logger logger = LoggerFactory.getLogger(AuthSteps.class);

    /**
     * Logs in the user by sending a POST request to the /login endpoint
     * with the given user credentials.
     *
     * @param credentials the user credentials (email and password)
     * @return the HTTP response returned from the login request
     */
    @Step("Login with user credentials")
    public Response login(UserCredentials credentials) {
        logger.info("Attempting to login with user: {}", credentials.getEmail());

        return given()
                .spec(RequestSpecFactory.getRequestSpec()) // No auth required for login
                .body(credentials)
                .when()
                .post("/login")
                .then().extract().response();
    }

    /**
     * Extracts the access token from a successful login response.
     * This method assumes the token is located under the key "access_token".
     *
     * @param response the HTTP response returned from the login request
     * @return the JWT access token as a String
     */
    @Step("Extract access token from login response")
    public String extractAccessToken(Response response) {
        logger.info("Extracting access token from login response");
        return response.jsonPath().getString("access_token");
    }
}
