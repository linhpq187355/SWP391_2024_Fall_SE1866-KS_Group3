package com.homesharing.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * UserService interface defines methods for handling user-related operations,
 * such as registration, validation, login, and logout.
 * It supports user interactions for both regular users and staff members.
 *
 * @version 1.0
 * @since 2024-10-02
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
     * @return A string indicating the result of the registration (e.g., success message, error).
     */
    String registerUser(String firstName, String lastName, String email, String password, String role);

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
     * Handles user login by verifying credentials and optionally remembering the user.
     *
     * @param email      The user's email address.
     * @param password   The user's password.
     * @param rememberMe Whether the user should remain logged in across sessions.
     * @param response   The HttpServletResponse used for managing cookies (if rememberMe is true).
     * @return A string indicating the result of the login attempt (e.g., success, error).
     */
    String login(String email, String password, boolean rememberMe, HttpServletResponse response);

    /**
     * Handles staff member login by verifying credentials.
     *
     * @param email    The staff member's email address.
     * @param password The staff member's password.
     * @param response The HttpServletResponse used for handling cookies or redirects.
     * @return A string indicating the result of the login attempt (e.g., success, error).
     */
    String loginStaff(String email, String password, HttpServletResponse response);

    /**
     * Logs the user out by clearing session and relevant cookies.
     *
     * @param response The HttpServletResponse used to clear cookies or manage the session.
     * @return A string indicating the result of the logout process (e.g., success, error).
     */
    String logout(HttpServletResponse response);

}
