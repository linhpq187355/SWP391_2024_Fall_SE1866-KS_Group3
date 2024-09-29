package com.homesharing.dao;

import com.homesharing.model.User;

/**
 * UserDAO interface defines the data access methods for user-related operations.
 * It includes saving a user to the database and checking if an email already exists.
 */
public interface UserDAO {

    /**
     * Saves the user to the database.
     *
     * @param user The user object to be saved.
     * @return The ID of the saved user.
     */
    int saveUser(User user);

    /**
     * Checks if an email already exists in the database.
     *
     * @param email The email address to check.
     * @return True if the email exists, false otherwise.
     */
    boolean emailExists(String email);

    /**
     * Retrieves the user information by userId.
     *
     * @param userId The ID of the user to retrieve.
     * @return The User object containing the user's information, or null if no user is found.
     */
    User getUser(int userId);

    /**
     * Finds a user in the database by email address.
     *
     * @param email The email address to search for.
     * @return The User object if a user is found, or null if no user exists for the given email.
     */
    User findUserByEmail(String email);

}
