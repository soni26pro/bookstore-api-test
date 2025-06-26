package com.bookstore.api.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import io.qameta.allure.Allure;

/**
 * Utility class that provides reusable REST-assured request specifications
 * for API requests, with and without authorization headers.
 */
public class RequestSpecFactory {

    private static final Logger logger = LoggerFactory.getLogger(RequestSpecFactory.class);

    /**
     * Builds a basic {@link RequestSpecification} with:
     * <ul>
     *   <li>Base URI from {@code config.properties}</li>
     *   <li>JSON Content-Type header</li>
     *   <li>AllureRestAssured filter for reporting</li>
     *   <li>Request and Response logging filters for headers</li>
     * </ul>
     *
     * @return a {@link RequestSpecification} for unauthenticated requests
     */
    public static RequestSpecification getRequestSpec() {
        // Create standard AllureRestAssured filter without using unavailable methods
        AllureRestAssured allureFilter = new AllureRestAssured();

        // Create custom logging filters that will log to both console and Allure
        RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(LogDetail.HEADERS);
        ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(LogDetail.HEADERS);

        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getProperty("base.uri"))
                .addFilter(allureFilter)
                .addFilter(requestLoggingFilter)
                .addFilter(responseLoggingFilter)
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
        // Create standard AllureRestAssured filter
        AllureRestAssured allureFilter = new AllureRestAssured();

        // Create custom logging filters
        RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(LogDetail.HEADERS);
        ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(LogDetail.HEADERS);

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getProperty("base.uri"))
                .addFilter(allureFilter)
                .addFilter(requestLoggingFilter)
                .addFilter(responseLoggingFilter)
                .addHeader("Content-Type", "application/json");

        if (token != null && !token.isEmpty()) {
            builder.addHeader("Authorization", "Bearer " + token);
            logger.info("Added Authorization header with Bearer token");

            // Add a step to Allure report about the authorization header
            Allure.step("Added Authorization header with Bearer token");
        }

        return builder.build();
    }
}
