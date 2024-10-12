/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.dao;

import com.homesharing.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * UserDao interface defines the data access methods for user-related operations.
 * It includes saving a user to the database and checking if an email already exists.
 * @author ManhNC
 */
public interface UserDAO {

    /**
     * update the user to the database.
     *
     * @param googleId The id of user to be saved.
     * @param email The email of user to be saved.
     * @return rowUpdated
     */
    int updateGoogleId(String googleId, String email)throws SQLException;

    /**
     * get the googleID of user to the database.
     *
     * @param email The email of user to be saved.
     * @return googleID
     */
    String getGoogleId(String email) throws SQLException;

    /**
     * Saves the user to the database.
     *
     * @param user The user object to be saved.
     * @return userId
     */
    int saveUser(User user) throws SQLException;

    /**
     * Checks if an email already exists in the database.
     *
     * @param email The email address to check.
     * @return True if the email exists, false otherwise.
     */
    boolean emailExists(String email) throws SQLException;

    boolean phoneExists(String phone) throws SQLException;

    int updateEmail(String email, int id) throws SQLException;

    int passWordExists(int userId) throws SQLException;

    /**
     * Retrieves a user from the database based on the user ID.
     *
     * @param userId The unique ID of the user to retrieve.
     * @return The {@link User} object corresponding to the user ID, or {@code null} if no user is found.
     */
    User getUser(int userId) throws SQLException;
    /**
     * Finds a user from the database by their email address.
     *
     * @param email The email address to search for.
     * @return The {@link User} object if found, or {@code null} if no user is found.
     */
    User findUserByEmail(String email) throws SQLException;

    int updatePhoneNumber(int userId, String phoneNumber) throws SQLException;

    List<User> getAllUsers();
    /**
     * Updates the profile information of an existing user in the database.
     *
     * @param user The {@link User} object containing updated user information.
     * @return The number of rows affected by the update operation.
     */
    int updateUserProfile(User user);
    /**
     * Retrieves the avatar (profile picture URL or path) of a user based on their ID.
     *
     * @param id The unique ID of the user.
     * @return The avatar URL or path as a {@link String}, or {@code null} if no avatar is found.
     */
    String getUserAvatar(int id);
    /**
     * Updates the status of a user in the database.
     *
     * @param id The unique ID of the user whose status is being updated.
     * @param status The new status to be set for the user.
     */
    void updateUserStatus(int id, String status);

    User getUserById(int id);
    /**
     * Resets the password for a user.
     * The password is hashed before being stored in the database.
     *
     * @param password The new password in plaintext to be hashed and stored.
     * @param id The unique ID of the user whose password is being reset.
     * @return The number of rows affected by the password update.
     */
    int resetPassword(String password, int id);
}
