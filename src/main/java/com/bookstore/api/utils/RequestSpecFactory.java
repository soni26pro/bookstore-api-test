package com.bookstore.api.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getProperty("base.uri"))
                .addFilter(new AllureRestAssured())
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public static RequestSpecification getRequestSpecWithAuth(String token) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getProperty("base.uri"))
                .addFilter(new AllureRestAssured())
                .addHeader("Content-Type", "application/json");

        if (token != null && !token.isEmpty()) {
            builder.addHeader("Authorization", "Bearer " + token);
        }

        return builder.build();
    }
}
