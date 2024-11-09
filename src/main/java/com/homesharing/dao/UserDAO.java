/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 * 2024-10-01      1.0              Pham Quang Linh     First Implement
 * 2024-10-10      2.0              Pham Quang Linh     Second Implement
 * 2024-10-30      2.0              Pham Quang Linh     Add functions
 */

package com.homesharing.dao;

import com.homesharing.model.Appointment;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.User;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import java.util.Map;

/**
 * UserDao interface defines the data access methods for user-related operations.
 * It includes saving a user to the database and checking if an email already exists.
 */
public interface UserDAO {

    /**
     * Updates the Google ID associated with a user's account.
     *
     * @param googleId The new Google ID.
     * @param email    The email address of the user whose Google ID needs to be updated.
     * @return The number of rows affected by the update operation (should be 1 for success).
     * @throws SQLException If a database error occurs.
     */
    int updateGoogleId(String googleId, String email)throws SQLException;

    /**
     * Retrieves the Google ID associated with a given email address.
     *
     * @param email The email address to look up.
     * @return The Google ID associated with the email, or null if no such user exists.
     * @throws SQLException If a database error occurs.
     */
    String getGoogleId(String email) throws SQLException;

    /**
     * Saves a new user to the database.
     *
     * @param user The User object to be saved.
     * @return The ID of the newly created user, generated by the database.
     * @throws SQLException If a database error occurs.
     */
    int saveUser(User user) throws SQLException;

    /**
     * Checks if a user with the given email address already exists in the database.
     *
     * @param email The email address to check.
     * @return True if a user with the given email exists, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    boolean emailExists(String email) throws SQLException;

    /**
     * Checks if a user with the given phone number already exists in the database.
     *
     * @param phone The phone number to check.
     * @return True if a user with the given phone number exists, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    boolean phoneExists(String phone) throws SQLException;

    /**
     * Updates the email address for a user.
     *
     * @param email The new email address.
     * @param id    The ID of the user to update.
     * @return The number of rows affected by the update.
     * @throws SQLException If a database error occurs.
     */
    int updateEmail(String email, int id) throws SQLException;

    /**
     * Checks if a password exists for the given user ID.  This is useful for
     * determining if a user has set up a password or is using social login.
     *
     * @param userId The ID of the user to check.
     * @return 1 if the password is NULL (no password set), 0 if not NULL (password exists),
     *         -1 if an error occurs or the user is not found.
     * @throws SQLException If a database error occurs.
     */
    int passWordExists(int userId) throws SQLException;

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
    User findUserByEmail(String email) throws SQLException, GeneralException;

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

    /**
     * Retrieves the total number of users in the database.
     *
     * @return The total count of users.
     */
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

    /**
     * Retrieves a list of host users associated with specific appointments.
     *
     * @param appointments A list of {@link Appointment} objects.
     * @return A list of {@link User} objects representing the hosts associated with the given appointments.
     */
    List<User> getHostByAppointment(List<Appointment> appointments);

    /**
     * Retrieves a list of tenant users associated with specific appointments.
     *
     * @param appointments A list of {@link Appointment} objects.
     * @return A list of {@link User} objects representing the tenants associated with the given appointments.
     */
    List<User> getTenantByAppointment(List<Appointment> appointments);


    List<User> getFilteredUsers(Map<String, Object> searchParams)  throws SQLException, IOException, ClassNotFoundException;
    int numOfUser(Map<String, Object> searchParams) throws SQLException, IOException, ClassNotFoundException;

    int getMinCleanliness();
    int getMaxCleanliness();

    int getMinSmoking();
    int getMaxSmoking();

    int getMinDrinking();
    int getMaxDrinking();

    int getMinInteraction();
    int getMaxInteraction();

    int getMinGuests();
    int getMaxGuests();

    int getMinCooking();
    int getMaxCooking();

    int getMinPet();
    int getMaxPet();

    Map<String, Double> calculateAveragePreferences(String role);
    List<User> getLatestUser();
}
