/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.service;

import com.homesharing.model.GoogleAccount;
import com.homesharing.model.User;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;

/**
 * UserService interface defines methods for user registration and validation,
 * handling login and logout processes, updating user profiles, and retrieving user details.
 * All methods are meant to manage user-related operations in the Home Sharing System.
 *
 * @version 1.0
 * @since 2024-09-18
 * @author ManhNC
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

    int updatePhone(String phone, String userId) throws SQLException;

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
}
