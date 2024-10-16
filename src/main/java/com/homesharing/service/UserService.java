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

package com.homesharing.service;

import com.homesharing.model.User;
import jakarta.servlet.http.HttpServletResponse;


import java.io.UnsupportedEncodingException;

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
    User getUser(int userId);
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
