package com.homesharing.service.impl;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.PasswordUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of UserService interface, handling user-related business logic.
 * This class manages user registration and input validation.
 */
public class UserServiceImpl implements UserService {

    private final UserDAO userDao;
    private final TokenDAO tokenDao;
    private final TokenService tokenService;
    private final PreferenceService preferenceService;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    /**
     * Constructor for UserServiceImpl, initializing the UserDao instance.
     *
     * @param userDao The UserDao instance for database operations.
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
    public String registerUser(String firstName, String lastName, String email, String password, String role) {
        // Validate user input
        if (!validateUserInput(firstName, lastName, email, password, password, role)) {
            return "Invalid input!";
        }

        try {
            // Check if the email already exists
            if (userDao.emailExists(email)) {
                return "Email đã được sử dụng!";
            }

            // Create a new user and set its properties
            User user = new User();
            String hashedPassword = PasswordUtil.hashPassword(password);
            user.setHashedPassword(hashedPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setStatus("active");

            // Determine role value based on input role
            int roleValue;
            if ("findRoommate".equals(role)) {
                roleValue = 4;
            } else if ("postRoom".equals(role)) {
                roleValue = 3;
            } else {
                return "Invalid role: " + role;
            }
            user.setRolesId(roleValue);
            // Insert new user into the database
            int userId = userDao.saveUser(user);
            tokenService.sendToken(email, userId);
            preferenceService.addPreference(userId);
            return "success";
        } catch (GeneralException e) {
            // Handle runtime exceptions thrown by the UserDao
            return "Error during registration: " + e.getMessage();
        }
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
     * Logs a user into the system.
     *
     * @param email the email address of the user
     * @param password the password of the user
     * @param rememberMe a boolean indicating whether to remember the user
     * @param response the HttpServletResponse object used to set cookies
     * @return a message indicating the result of the login attempt, either success or an error message
     */
    @Override
    public String login(String email, String password, boolean rememberMe, HttpServletResponse response) {
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
        //check if user is not a host or tenant
        if (roleValue == 1 || roleValue == 2) {
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
        int cookieAge = rememberMe ? 30 * 24 * 60 * 60 : 7 * 24 * 60 * 60; // 1 week or 1 month
        // Save user's information to cookies
        CookieUtil.addCookie(response, "id", String.valueOf(user.getId()), cookieAge);
        CookieUtil.addCookie(response, "firstName", user.getFirstName(), cookieAge);
        CookieUtil.addCookie(response, "lastName", user.getLastName(), cookieAge);
        CookieUtil.addCookie(response, "email", user.getEmail(), cookieAge);
        CookieUtil.addCookie(response, "roleId", String.valueOf(user.getRolesId()), cookieAge);

        // Return true to indicate a successful login
        return "success";
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
    public String loginStaff(String email, String password, HttpServletResponse response){
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
    public User getUser(int userId) {
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
