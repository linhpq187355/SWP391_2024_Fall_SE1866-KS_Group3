package com.homesharing.dao;

import com.homesharing.model.Token;
import com.homesharing.model.User;

import java.util.List;

/**
 * UserDao interface defines the data access methods for user-related operations.
 * It includes saving a user to the database and checking if an email already exists.
 */
public interface UserDao {

    /**
     * Saves the user to the database.
     *
     * @param user The user object to be saved.
     * @return userId
     */
    int saveUser(User user);

    /**
     * Checks if an email already exists in the database.
     *
     * @param email The email address to check.
     * @return True if the email exists, false otherwise.
     */
    boolean emailExists(String email);

    User getUser(int userId);


}
