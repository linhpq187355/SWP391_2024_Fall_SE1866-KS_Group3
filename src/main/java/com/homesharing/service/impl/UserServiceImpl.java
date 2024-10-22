/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.service.impl;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.GoogleAccount;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.PasswordUtil;
import com.homesharing.util.SecureRandomCode;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the {@link UserService} interface. This class manages
 * user-related operations such as registration, login, profile updates,
 * and password management.
 *
 * @author ManhNC
 */
public class UserServiceImpl implements UserService {

    private final UserDAO userDao;
    private final TokenDAO tokenDao;
    private final TokenService tokenService;
    private final PreferenceService preferenceService;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    /**
     * Constructs a new UserServiceImpl with the required dependencies.
     *
     * @param userDao            The data access object for user operations.
     * @param tokenDao           The data access object for token operations.
     * @param tokenService        The service for managing tokens.
     * @param preferenceService The service for managing user preferences.
     */
    public UserServiceImpl(UserDAO userDao, TokenDAO tokenDao, TokenService tokenService, PreferenceService preferenceService) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
        this.tokenService = tokenService;
        this.preferenceService = preferenceService;
    }

    /**
     * Registers a new user by validating input, checking if the email already exists,
     * hashing the password, and saving the user to the database.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The password of the user.
     * @param role      The role of the user (findRoommate or postRoom).
     * @return A success message or an error message.
     */
    @Override
    public int registerUser(String firstName, String lastName, String email, String password, String role) throws SQLException {
        // Validate user input
        if (!validateUserInput(firstName, lastName, email, password, password, role)) {
            return -1;
        }

        try {
            // Check if the email already exists
            if (userDao.emailExists(email)) {
                return 0;
            }

            // Create a new user and set its properties
            User user = new User();
            String hashedPassword = PasswordUtil.hashPassword(password);
            user.setHashedPassword(hashedPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setStatus("active");
            user.setGoogleId(null);

            // Determine role value based on input role
            int roleValue;
            if ("findRoommate".equals(role)) {
                roleValue = 3;
            } else if ("postRoom".equals(role)) {
                roleValue = 4;
            } else {
                return -1;
            }
            user.setRolesId(roleValue);
            // Insert new user into the database
            int userId = userDao.saveUser(user);
            tokenService.sendToken(email, userId);
            preferenceService.addPreference(userId);
            return userId;
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        }
    }

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
    @Override
    public int registerByGoogle(GoogleAccount googleAccount, String role, HttpServletResponse response) throws SQLException {
        // Check if the email already exists in the database
        if(userDao.emailExists(googleAccount.getEmail())) {
            String ggID = userDao.getGoogleId(googleAccount.getEmail());

            // Update Google ID if it's different or empty
            if(ggID == null || ggID.isEmpty() || !ggID.equalsIgnoreCase(googleAccount.getId())) {
                if(userDao.updateGoogleId(googleAccount.getId(), googleAccount.getEmail()) == 0){
                    throw new GeneralException("Error when updating google account");
                }
            }

            // identity max age
            int cookieAge = 7 * 24 * 60 * 60; // 1 week
            User user = userDao.findUserByEmail(googleAccount.getEmail());

            // Save user's information to cookies
            CookieUtil.addCookie(response, "id", String.valueOf(user.getId()), cookieAge);
            CookieUtil.addCookie(response, "firstName", user.getFirstName(), cookieAge);
            CookieUtil.addCookie(response, "lastName", user.getLastName(), cookieAge);
            CookieUtil.addCookie(response, "email", user.getEmail(), cookieAge);
            CookieUtil.addCookie(response, "roleId", String.valueOf(user.getRolesId()), cookieAge);
            return 1; // User successfully logged in
        }

        // Check if the role is null
        if(role == null) {
            return -1; // Role is null
        }

        // Create a new user object
        User user = new User();
        user.setGoogleId(googleAccount.getId());
        user.setEmail(googleAccount.getEmail());
        user.setStatus("active");
        user.setHashedPassword(null); // No password needed for Google sign up
        user.setFirstName(googleAccount.getFamily_name());
        user.setLastName(googleAccount.getGiven_name());

        // Determine role value based on input role
        int roleValue;
        if ("findRoommate".equals(role)) {
            roleValue = 3; // Role ID for finding a roommate
        } else if ("postRoom".equals(role)) {
            roleValue = 4; // Role ID for posting a room
        } else {
            return 0; // Invalid role
        }
        user.setRolesId(roleValue);

        // Insert new user into the database
        int userId = userDao.saveUser(user);

        // Create a new token
        String tokenCode = SecureRandomCode.generateCode();
        LocalDateTime requestedTime = LocalDateTime.now();
        Token newToken = new Token(userId, tokenCode, requestedTime, true);
        tokenDao.insertToken(newToken);
        preferenceService.addPreference(userId);

        // identity max age
        int cookieAge = 7 * 24 * 60 * 60; // 1 week or 1 month
        // Save user's information to cookies
        CookieUtil.addCookie(response, "id", String.valueOf(userId), cookieAge);
        CookieUtil.addCookie(response, "firstName", user.getFirstName(), cookieAge);
        CookieUtil.addCookie(response, "lastName", user.getLastName(), cookieAge);
        CookieUtil.addCookie(response, "email", user.getEmail(), cookieAge);
        CookieUtil.addCookie(response, "roleId", String.valueOf(user.getRolesId()), cookieAge);

        return 2; // New user successfully registered
    }

    /**
     * Validates user input for registration.
     *
     * @param firstName       The first name of the user.
     * @param lastName        The last name of the user.
     * @param email           The email address of the user.
     * @param password        The password of the user.
     * @param confirmPassword The confirmation password.
     * @param role            The role of the user.
     * @return True if all input is valid, false otherwise.
     */
    @Override
    public boolean validateUserInput(String firstName, String lastName, String email, String password, String confirmPassword, String role) {
        // Check if names contain only letters and spaces
        if (!firstName.matches("[\\p{L}\\s]+") || !lastName.matches("[\\p{L}\\s]+")) {
            return false;
        }

        // Check if the email is valid
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        if (!email.matches(emailRegex)) {
            return false;
        }

        // Check password length and confirmation
        if (password.length() < 8 || !password.equals(confirmPassword)) {
            return false;
        }

        // Validate the role
        return role != null && (role.equals("findRoommate") || role.equals("postRoom"));
    }

    /**
     * Validates the provided account information.
     *
     * @param firstName      The first name of the user.
     * @param lastName       The last name of the user.
     * @param email         The email address of the user.
     * @param password      The password chosen by the user.
     * @param confirmPassword The confirmed password entered by the user.
     * @param role          The role ID of the user.
     * @param gender        The gender of the user ("male" or "female").
     * @param phone         The phone number of the user.
     * @param dob           The date of birth of the user (YYYY-MM-DD format).
     * @return {@code true} if the account information is valid, {@code false} otherwise.
     */
    @Override
    public boolean validateAccount(String firstName, String lastName, String email, String password, String confirmPassword, int role, String gender, String phone, String dob) {
        // Check if names contain only letters and spaces
        if (!firstName.matches("[\\p{L}\\s]+") || !lastName.matches("[\\p{L}\\s]+")) {
            return false;
        }

        // Check if the email is valid
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        if (!email.matches(emailRegex)) {
            return false;
        }

        // Check password length and confirmation
        if (password.length() < 8 || !password.equals(confirmPassword)) {
            return false;
        }

        // Validate the new fields (dob and gender)
        if (dob == null || dob.isEmpty()) {
            return false;
        }

        if (gender == null || (!gender.equals("male") && !gender.equals("female"))) {
            return false;
        }

        return true;
    }

    /**
     * Validates the provided email address.
     *
     * @param email The email address to validate.
     * @return {@code true} if the email is valid, {@code false} otherwise.
     */
    @Override
    public boolean validateEmail(String email) {
        // Check if the email is valid
        String emailRegex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        if (!email.matches(emailRegex)) {
            return false;
        }
        return true;
    }

    /**
     * Creates a new user account.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The password chosen by the user.
     * @param role      The role ID of the user.
     * @param gender    The gender of the user.
     * @param phone     The phone number of the user.
     * @param dob       The date of birth of the user (YYYY-MM-DD format).
     * @return The ID of the newly created user, -1 if the phone number already exists, -2 if the email already exists.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public int createAccount(String firstName, String lastName, String email, String password, int role, String gender, String phone, String dob) throws SQLException {
        try {
            // Check if the email already exists
            if (userDao.emailExists(email)) {
                return -2;
            }

            // Check if the phone number already exists
            if (phone != null && !phone.isEmpty() && userDao.phoneExists(phone)) {
                return -1;
            }

            // Create a new user and set its properties
            User user = new User();
            String hashedPassword = PasswordUtil.hashPassword(password);
            user.setHashedPassword(hashedPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setStatus("active");
            user.setDob(dob.isEmpty() ? null : LocalDate.parse(dob));
            user.setGoogleId(null);
            user.setPhoneNumber(phone);
            user.setGender(gender);
            user.setRolesId(role);
            // Insert new user into the database
            int userId = userDao.saveUser(user);
            String code = SecureRandomCode.generateCode();
            Token token = new Token(userId, code, LocalDateTime.now(), true);
            tokenDao.insertToken(token);
            preferenceService.addPreference(userId);
            return userId;
        } catch (GeneralException e) {
            throw new GeneralException(e.getMessage());
        }
    }

    /**
     * Stores the user's information in cookies.
     *
     * @param userId   The ID of the user.
     * @param response The HttpServletResponse object to add cookies to.
     * @throws SQLException  If a database error occurs.
     * @throws GeneralException If the user is not found.
     */
    @Override
    public void putAccountOnCookie(int userId, HttpServletResponse response) throws SQLException {
        User user = userDao.getUser(userId);
        if(user == null) {
            throw new GeneralException("User not found");
        }
        // identity max age
        int cookieAge = 7 * 24 * 60 * 60; // 1 week
        // Save user's information to cookies
        CookieUtil.addCookie(response, "id", String.valueOf(user.getId()), cookieAge);
        CookieUtil.addCookie(response, "firstName", user.getFirstName(), cookieAge);
        CookieUtil.addCookie(response, "lastName", user.getLastName(), cookieAge);
        CookieUtil.addCookie(response, "email", user.getEmail(), cookieAge);
        CookieUtil.addCookie(response, "roleId", String.valueOf(user.getRolesId()), cookieAge);
    }

    /**
     * Logs a user into the system.
     *
     * @param email the email address of the user
     * @param password the password of the user
     * @param rememberMe a boolean indicating whether to remember the user
     * @param response the HttpServletResponse object used to set cookies
     * @return a message indicating the result of the login attempt, either success or an error message
     */
    @Override
    public String login(String email, String password, boolean rememberMe, HttpServletResponse response) throws SQLException {
        try{
        // Attempt to find the user by their email address
        User user = userDao.findUserByEmail(email);

        // If the user does not exist, return false
        if (user == null) {
            // User does not exist
            return "Email hoặc mật khẩu không đúng";
        }

        if(user.getHashedPassword() == null) {
            return "Tài khoản này chưa có mật khẩu, vui lòng đăng nhập bằng Google và cập nhật mật khẩu.";
        }

        // Check if the provided password matches the user's hashed password
        if (!user.getHashedPassword().equals(PasswordUtil.hashPassword(password))) {
            // Password is incorrect
            return "Email hoặc mật khẩu không đúng";
        }

        // Check if the user's status is active
        if (!user.getStatus().equals("active")) {
            // User's status is not active
            return "Tài khoản này đã bị khóa";
        }

        int roleValue = user.getRolesId();
        //check if user is not a host or tenant
        if (roleValue == 1 || roleValue == 2) {
            return "Bạn không có quyền đăng nhập ở đây.";
        }

        // Find token for user
        Token token = tokenDao.findToken(user.getId());

        // If the token does not exist, create a new one
        if (token == null || !token.isVerified()) {
            tokenService.sendToken(email, user.getId());
            return "not-verify";
        }

        // identity max age
        int cookieAge = rememberMe ? 30 * 24 * 60 * 60 : 7 * 24 * 60 * 60; // 1 week or 1 month
        // Save user's information to cookies
        CookieUtil.addCookie(response, "id", String.valueOf(user.getId()), cookieAge);
        CookieUtil.addCookie(response, "firstName", user.getFirstName(), cookieAge);
        CookieUtil.addCookie(response, "lastName", user.getLastName(), cookieAge);
        CookieUtil.addCookie(response, "email", user.getEmail(), cookieAge);
        CookieUtil.addCookie(response, "roleId", String.valueOf(user.getRolesId()), cookieAge);

        // Return true to indicate a successful login
        return "success";
        } catch (GeneralException e) {
            throw new GeneralException("Error:" ,e);
        }
    }

    /**
     * Updates the password for a user.
     *
     * @param userId   The ID of the user.
     * @param hadPass  Indicates whether the user has a previous password (1 if yes, 0 if no).
     * @param oldPass  The old password of the user (required if hadPass is 0).
     * @param password The new password for the user.
     * @return An integer indicating the result of the operation:
     *         <ul>
     *         <li> 1: Password updated successfully.
     *         <li>-1: Incorrect old password provided.
     *         <li>-2: Invalid input (empty password or missing old password when required).
     *         </ul>
     * @throws SQLException If a database error occurs.
     */
    @Override
    public int updatePassword(int userId, int hadPass, String oldPass, String password) throws SQLException {
        if(password == null || password.isEmpty()) {
            return -2;
        }
        if(hadPass == 0) {
            if(oldPass == null || oldPass.isEmpty()) {
                return -2;
            }
            User user = userDao.getUserById(userId);
            if(user.getHashedPassword() == null || user.getHashedPassword().isEmpty()) {
                return -2;
            }
            if(!user.getHashedPassword().equals(PasswordUtil.hashPassword(oldPass))) {
                return -1;
            }
        }
        return userDao.resetPassword(password, userId);
    }

    /**
     * Logs an admin into the system.
     *
     * @param email the email address of the admin
     * @param password the password of the admin
     * @param response the HttpServletResponse object used to set cookies
     * @return a message indicating the result of the login attempt, either success or an error message
     */
    @Override
    public String loginStaff(String email, String password, HttpServletResponse response) throws SQLException {
        try{
        // Attempt to find the user by their email address
        User user = userDao.findUserByEmail(email);

        // If the user does not exist, return false
        if (user == null) {
            // User does not exist
            return "Email hoặc mật khẩu không đúng";
        }

        // Check if the provided password matches the user's hashed password
        if (!user.getHashedPassword().equals(PasswordUtil.hashPassword(password))) {
            // Password is incorrect
            return "Email hoặc mật khẩu không đúng";
        }

        // Check if the user's status is active
        if (!user.getStatus().equals("active")) {
            // User's status is not active
            return "Tài khoản này đã bị khóa";
        }

        int roleValue = user.getRolesId();
        //check if user is not an admin or moderator
        if (roleValue == 3 || roleValue == 4) {
            return "Bạn không có quyền đăng nhập ở đây.";
        }

        // Find token for user
        Token token = tokenDao.findToken(user.getId());

        // If the token does not exist, create a new one
        if (token == null || !token.isVerified()) {
            tokenService.sendToken(email, user.getId());
            return "Tài khoản này chưa được xác thực, hãy click vào đường link trong email để xác thực tài khoản.";
        }

        // identity max age
        int cookieAge = 24 * 60 * 60; // 1 day
        // Save user's information to cookies
        CookieUtil.addCookie(response, "id", String.valueOf(user.getId()), cookieAge);
        CookieUtil.addCookie(response, "firstName", user.getFirstName(), cookieAge);
        CookieUtil.addCookie(response, "lastName", user.getLastName(), cookieAge);
        CookieUtil.addCookie(response, "email", user.getEmail(), cookieAge);
        CookieUtil.addCookie(response, "roleId", String.valueOf(user.getRolesId()), cookieAge);

        // Return true to indicate a successful login
        return "success";
        } catch (GeneralException e) {
            throw new GeneralException("Error",e);
        }
    }

    /**
     * Logs the user out of the system.
     *
     * @param response the HttpServletResponse object used to remove cookies
     * @return a message indicating the result of the logout attempt
     */
    @Override
    public String logout(HttpServletResponse response) {
        //delete all cookie
        CookieUtil.removeCookie(response, "id");
        CookieUtil.removeCookie(response, "firstName");
        CookieUtil.removeCookie(response, "lastName");
        CookieUtil.removeCookie(response, "email");
        CookieUtil.removeCookie(response, "roleId");
        return "logout success";
    }

    /**
     * Updates the profile information of a user.
     *
     * @param userId          The unique ID of the user to update.
     * @param firstName       The new first name of the user.
     * @param lastName        The new last name of the user.
     * @param address         The new address of the user.
     * @param gender          The new gender of the user.
     * @param dob             The new date of birth of the user in string format.
     * @param avatarFileName  The new avatar file name of the user.
     * @return The number of rows affected by the update operation.
     */
    @Override
    public int updateUserProfile(String userId, String firstName, String lastName, String address, String gender, String dob, String avatarFileName) {
        try {
            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(address);
            user.setGender(gender);
            user.setDob(dob.isEmpty() ? null : LocalDate.parse(dob));

            // Set avatar if available
            if (avatarFileName != null) {
                user.setAvatar(avatarFileName);
            } else {
                user.setAvatar(userDao.getUserAvatar(Integer.parseInt(userId)));
            }

            return userDao.updateUserProfile(user);

        } catch (Exception e) {
            throw new GeneralException("Failed to update user profile", e);
        }
    }

    /**
     * Retrieves a User object from the database based on the provided userId.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The User object associated with the given userId, or throws a GeneralException if the user is not found or an error occurs during retrieval.
     * @throws GeneralException if an error occurs while accessing the database or if the user is not found.
     */
    @Override
    public User getUser(int userId) throws SQLException {
        try {
            // Attempt to retrieve the user from the DAO using the provided userId
            return userDao.getUser(userId);
        } catch (GeneralException e) {
            // Handle the exception
            throw new GeneralException("Could not retrieve user with ID: " + userId, e);
        }
    }

    /**
     * Resets the password for a user by delegating the task to UserDAO.
     *
     * @param userId      The ID of the user whose password is to be reset.
     * @param newPassword The new password to set for the user.
     * @return The number of rows affected by the update (1 if successful, 0 otherwise).
     */
    @Override
    public int resetUserPassword(int userId, String newPassword) {
        try {
            return userDao.resetPassword(newPassword, userId);
        } catch (GeneralException e) {
            logger.log(Level.SEVERE, "Failed to reset password for user ID: " + userId, e);
            throw new GeneralException("Error resetting password for user ID: " + userId, e);
        }
    }

}
