package com.homesharing.dao;

import com.homesharing.model.User;

import java.util.List;

/**
 * UserDao interface defines the data access methods for user-related operations.
 * It includes saving a user to the database and checking if an email already exists.
 */
public interface UserDAO {

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

    /**
     * Retrieves a {@link User} by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} object if found, or {@code null} if no user is found.
     */
    User getUser(int userId);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The {@link User} object if found, or {@code null} if no user is found.
     */
    User findUserByEmail(String email);

    List<User> getAllUsers();

    int updateUserProfile(User user);

    String getUserAvatar(int id);

    void updateUserStatus(int id, String status);

    User getUserById(int id);

    int resetPassword(String password, int id);
}
