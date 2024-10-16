/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 */

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
     * Retrieves a user from the database based on the user ID.
     *
     * @param userId The unique ID of the user to retrieve.
     * @return The {@link User} object corresponding to the user ID, or {@code null} if no user is found.
     */
    User getUser(int userId);
    /**
     * Finds a user from the database by their email address.
     *
     * @param email The email address to search for.
     * @return The {@link User} object if found, or {@code null} if no user is found.
     */
    User findUserByEmail(String email);

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

    int getNumberUsers();
    /**
     * Updates the matching profile of a user in the database.
     *
     * @param user The {@link User} object containing updated matching profile information.
     * @return The number of rows affected by the update operation.
     */
    int updateMatchingProfile(User user);
    /**
     * Retrieves the matching user profile based on the unique ID.
     *
     * @param id The unique ID of the user whose matching profile is to be retrieved.
     * @return The {@link User} object corresponding to the matching profile ID, or {@code null} if no user is found.
     */
    User getMatchingUserProfile(int id);
}
