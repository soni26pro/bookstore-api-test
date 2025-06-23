package com.bookstore.api.models;

/**
 * Represents user credentials for authentication (login/signup).
 * Used in API requests that require email and password.
 */
public class UserCredentials {

    /** The email address used as the username. */
    private String email;

    /** The password associated with the email account. */
    private String password;

    /**
     * Default constructor required for serialization/deserialization.
     */
    public UserCredentials() {}

    /**
     * Constructs a new {@code UserCredentials} instance with the specified email and password.
     *
     * @param email    the user's email address
     * @param password the user's password
     */
    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the user's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
