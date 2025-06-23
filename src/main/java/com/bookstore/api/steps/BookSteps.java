package com.bookstore.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.api.models.Book;
import com.bookstore.api.utils.RequestSpecFactory;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class BookSteps {

    private static final Logger logger = LoggerFactory.getLogger(BookSteps.class);

    @Step("Create a new book")
    public Response createBook(String token, Book book) {
        logger.info("Creating a new book: {}", book);

        return given()
                .spec(RequestSpecFactory.getRequestSpecWithAuth(token))
                .body(book)
                .when()
                .post("/books/")
                .then()
                .extract().response();
    }

    @Step("Get book by ID: {bookId}")
    public Response getBook(String token, int bookId) {
        logger.info("Getting book with ID: {}", bookId);

        // Use auth spec

        return given()
                .spec(RequestSpecFactory.getRequestSpecWithAuth(token)) // Use auth spec
                .when()
                .get("/books/" + bookId)
                .then().extract().response();
    }

    @Step("Update book with ID: {bookId}")
    public Response updateBook(String token, int bookId, Book book) {
        logger.info("Updating book with ID: {}", bookId);

        // Use auth spec

        return given()
                .spec(RequestSpecFactory.getRequestSpecWithAuth(token)) // Use auth spec
                .body(book)
                .when()
                .put("/books/" + bookId)
                .then().extract().response();
    }

    @Step("Delete book with ID: {bookId}")
    public Response deleteBook(String token, int bookId) {
        logger.info("Deleting book with ID: {}", bookId);

        // Use auth spec

        return given()
                .spec(RequestSpecFactory.getRequestSpecWithAuth(token)) // Use auth spec
                .when()
                .delete("/books/" + bookId)
                .then().extract().response();
    }
}
