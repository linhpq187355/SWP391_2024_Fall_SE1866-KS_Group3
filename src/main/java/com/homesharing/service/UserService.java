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
 */

package com.homesharing.service;

import com.homesharing.model.GoogleAccount;
import com.homesharing.model.User;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * UserService interface defines methods for user registration and validation.
 * It contains methods to register a user and validate user input data.
 */
public interface UserService {

    /**
     * Registers a new user with the provided details.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @param email     The user's email address.
     * @param password  The user's password.
     * @param role      The role of the user (e.g., findRoommate, postRoom).
     * @return An integer indicate userID of new user.
     */
    int registerUser(String firstName, String lastName, String email, String password, String role) throws SQLException;

    /**
     * Registers a new user using their Google account information.
     * If the user already exists, updates their Google ID and sets the cookie values accordingly.
     *
     * @param googleAccount The GoogleAccount object containing user information.
     * @param role The role to assign to the user.
     *             If the role is null, it indicates that the user needs to set their role again.
     * @param response The HttpServletResponse object used to set cookies for the user.
     * @return An integer indicating the result of the registration process:
     *         1 if the user was successfully logged in,
     *         2 if a new user was registered successfully,
     *         -1 if the role is null, and
     *         0 if the role is invalid.
     * @throws SQLException if a database access error occurs.
     */
    int registerByGoogle(GoogleAccount googleAccount, String role, HttpServletResponse response) throws SQLException;

    /**
     * Validates the user input for registration.
     * Checks the first name and last name for valid characters, validates the email format,
     * ensures the password meets the length requirement and matches the confirm password,
     * and verifies the role is one of the accepted values.
     *
     * @param firstName       The user's first name.
     * @param lastName        The user's last name.
     * @param email           The user's email address.
     * @param password        The user's password.
     * @param confirmPassword The confirmation of the user's password.
     * @param role            The user's role (e.g., findRoommate, postRoom).
     * @return True if all inputs are valid, false otherwise.
     */
    boolean validateUserInput(String firstName, String lastName, String email, String password, String confirmPassword, String role);

    /**
     * Validates user account information.
     *
     * @param firstName      The user's first name.
     * @param lastName       The user's last name.
     * @param email           The user's email address.
     * @param password        The user's password.
     * @param confirmPassword The confirmed password.
     * @param role            The user's role ID.
     * @param gender          The user's gender.
     * @param phone           The user's phone number.
     * @param dob             The user's date of birth (YYYY-MM-DD).
     * @return {@code true} if the account information is valid, {@code false} otherwise.
     */
    boolean validateAccount(String firstName, String lastName, String email, String password, String confirmPassword, int role, String gender, String phone, String dob);

    /**
     * Validates an email address.
     *
     * @param email The email address to validate.
     * @return {@code true} if the email is valid, {@code false} otherwise.
     */
    boolean validateEmail(String email);

    /**
     * Creates a new user account.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @param email     The user's email address.
     * @param password  The user's password.
     * @param role      The user's role ID.
     * @param gender    The user's gender.
     * @param phone     The user's phone number.
     * @param dob       The user's date of birth (YYYY-MM-DD).
     * @return The ID of the newly created user, or a negative value if an error occurred (e.g., -1 for duplicate phone, -2 for duplicate email).
     * @throws SQLException If a database error occurs.
     */
    int createAccount(String firstName, String lastName, String email, String password, int role, String gender, String phone, String dob) throws SQLException;

    /**
     * Stores user information in cookies.
     *
     * @param userId   The ID of the user.
     * @param response The HttpServletResponse to add cookies to.
     * @throws SQLException If a database error occurs.
     */
    void putAccountOnCookie(int userId, HttpServletResponse response) throws SQLException;

    /**
     * Handles user login by verifying credentials and optionally remembering the user.
     *
     * @param email      The user's email address.
     * @param password   The user's password.
     * @param rememberMe Whether the user should remain logged in across sessions.
     * @param response   The HttpServletResponse used for managing cookies (if rememberMe is true).
     * @return A string indicating the result of the login attempt (e.g., success, error).
     */
    String login(String email, String password, boolean rememberMe, HttpServletResponse response) throws SQLException;

    /**
     * Updates a user's password.
     *
     * @param userId   The ID of the user.
     * @param hadPass  Indicates if the user previously had a password (1 if yes, 0 if no).
     * @param oldPass  The user's old password (required if hadPass is 0).
     * @param password The new password.
     * @return An integer indicating the result:
     *         1: Password updated successfully.
     *         -1: Incorrect old password.
     *         -2: Invalid input (e.g., null or empty password).
     * @throws SQLException If a database error occurs.
     */
    int updatePassword(int userId, int hadPass, String oldPass, String password) throws SQLException;

    /**
     * Handles staff member login by verifying credentials.
     *
     * @param email    The staff member's email address.
     * @param password The staff member's password.
     * @param response The HttpServletResponse used for handling cookies or redirects.
     * @return A string indicating the result of the login attempt (e.g., success, error).
     */
    String loginStaff(String email, String password, HttpServletResponse response) throws SQLException;

    /**
     * Logs the user out by clearing session and relevant cookies.
     *
     * @param response The HttpServletResponse used to clear cookies or manage the session.
     * @return A string indicating the result of the logout process (e.g., success, error).
     */
    String logout(HttpServletResponse response);
    /**
     * Updates the user's profile with the provided details.
     *
     * @param userId          The user's ID.
     * @param firstName       The updated first name of the user.
     * @param lastName        The updated last name of the user.
     * @param address         The updated address of the user.
     * @param gender          The updated gender of the user.
     * @param dob             The updated date of birth of the user.
     * @param avatarFileName  The name of the updated avatar file.
     * @return An integer indicating the result of the update operation (e.g., number of rows affected).
     */
    int updateUserProfile(String userId, String firstName, String lastName, String address, String gender, String dob, String avatarFileName);
    /**
     * Retrieves the user details based on the provided user ID.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The User object containing the user's details, or null if not found.
     */
    User getUser(int userId) throws SQLException;

    /**
     * Resets the password for a user with the given ID.
     *
     * @param userId The ID of the user whose password is to be reset.
     * @param newPassword The new password to be set for the user.
     * @return The number of rows affected by the password reset (1 if successful, 0 if unsuccessful).
     */
    int resetUserPassword(int userId, String newPassword);

    int getNumberOfUsers();

    /**
     * Updates the matching profile for a user based on the provided criteria.
     *
     * @param dob              The date of birth to be updated.
     * @param gender           The gender to be updated.
     * @param rawHowLong      The duration of how long the user has lived at their current residence.
     * @param emvdate         The move-in date.
     * @param lmvdate         The last move-out date.
     * @param rawMinBudget    The minimum budget for housing.
     * @param rawMaxBudget    The maximum budget for housing.
     * @param userId          The ID of the user whose profile is to be updated.
     * @return An integer indicating the result of the update operation (e.g., number of rows affected).
     */
    int updateMatchingProfile(String dob, String gender, String rawHowLong, String emvdate, String lmvdate, String rawMinBudget, String rawMaxBudget, String userId);
}
