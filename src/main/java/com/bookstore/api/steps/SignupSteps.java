package com.bookstore.api.steps;

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

    /**
     * Sends a signup (registration) request to the /signup endpoint
     * using the provided user credentials (email and password).
     *
     * @param user the user credentials for registration
     * @return the HTTP response returned by the API
     */
    @Step("Signup a new user")
    public Response signup(UserCredentials user) {
        return given()
                .spec(RequestSpecFactory.getRequestSpec())
                .body(user)
                .when()
                .post("/signup")
                .then()
                .extract().response();
    }
}
