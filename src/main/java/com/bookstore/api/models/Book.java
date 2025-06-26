package com.bookstore.api.models;

/**
 * Represents a book entity in the bookstore system.
 * This model is used for API request and response payloads.
 */
public class Book {

    /** The name or title of the book. */
    private String name;

    /** The author of the book. */
    private String author;

    /** The year the book was published. */
    private Integer published_year;

    /** A short summary or description of the book. */
    private String book_summary;

    /**
     * Default constructor for deserialization.
     */
    public Book() {}

    /**
     * Constructs a new Book with the specified attributes.
     *
     * @param name           the title of the book
     * @param author         the author of the book
     * @param published_year the year the book was published
     * @param book_summary   a brief summary of the book
     */
    public Book(String name, String author, Integer published_year, String book_summary) {
        this.name = name;
        this.author = author;
        this.published_year = published_year;
        this.book_summary = book_summary;
    }

    /**
     * Gets the name (title) of the book.
     *
     * @return the book's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name (title) of the book.
     *
     * @param name the new title of the book
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the author of the book.
     *
     * @return the book's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the year the book was published.
     *
     * @return the published year
     */
    public Integer getPublished_year() {
        return published_year;
    }

    /**
     * Sets the published year of the book.
     *
     * @param published_year the year to set
     */
    public void setPublished_year(Integer published_year) {
        this.published_year = published_year;
    }

    /**
     * Gets the summary of the book.
     *
     * @return the book's summary
     */
    public String getBook_summary() {
        return book_summary;
    }

    /**
     * Sets the summary of the book.
     *
     * @param book_summary the summary to set
     */
    public void setBook_summary(String book_summary) {
        this.book_summary = book_summary;
    }

    /**
     * Returns a string representation of this Book.
     * This is useful for logging and debugging.
     *
     * @return a string representation of the book
     */
    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", published_year=" + published_year +
                ", summary='" + book_summary + '\'' +
                '}';
    }
}
