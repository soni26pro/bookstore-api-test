package com.bookstore.api.steps;

import com.bookstore.api.models.UserCredentials;
import com.bookstore.api.utils.RequestSpecFactory;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class SignupSteps {

    @Step("Signup a new user")
    public Response signup(UserCredentials user) {

        Response response = given()
                .spec(RequestSpecFactory.getRequestSpec())
                .body(user)
                .when()
                .post("/signup")
                .then()
                .extract().response();
        return response;
    }
}
