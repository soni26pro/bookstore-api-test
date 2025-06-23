package com.bookstore.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.utils.RequestSpecFactory;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class AuthSteps {

    private static final Logger logger = LoggerFactory.getLogger(AuthSteps.class);

    @Step("Login with user credentials")
    public Response login(UserCredentials credentials) {
        logger.info("Attempting to login with user: {}", credentials.getEmail());
        
        Response response = given()
                .spec(RequestSpecFactory.getRequestSpec()) // Login might not require auth initially
                .body(credentials)
                .when()
                .post("/login")
                .then().extract().response();

        return response;
    }

    // Method to extract token (optional, can be done in test)
    @Step("Extract access token from login response")
    public String extractAccessToken(Response response) {
        logger.info("Extracting access token from login response");
        return response.jsonPath().getString("access_token");
    }
}
