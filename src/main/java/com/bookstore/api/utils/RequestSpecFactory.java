package com.bookstore.api.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * Utility class that provides reusable REST-assured request specifications
 * for API requests, with and without authorization headers.
 */
public class RequestSpecFactory {

    /**
     * Builds a basic {@link RequestSpecification} with:
     * <ul>
     *   <li>Base URI from {@code config.properties}</li>
     *   <li>JSON Content-Type header</li>
     *   <li>AllureRestAssured filter for reporting</li>
     * </ul>
     *
     * @return a {@link RequestSpecification} for unauthenticated requests
     */
    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getProperty("base.uri"))
                .addFilter(new AllureRestAssured())
                .addHeader("Content-Type", "application/json")
                .build();
    }

    /**
     * Builds a {@link RequestSpecification} similar to {@link #getRequestSpec()}, but
     * also includes an Authorization header if a token is provided.
     *
     * @param token the Bearer token used for authenticated requests
     * @return a {@link RequestSpecification} for authenticated requests
     */
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
