package com.homesharing.service.impl;

import com.homesharing.dao.TokenDao;
import com.homesharing.dao.UserDao;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.UserService;
import com.homesharing.util.PasswordUtil;
import com.homesharing.util.SecureRandomCode;
import com.homesharing.util.SendingEmail;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of UserService interface, handling user-related business logic.
 * This class manages user registration and input validation.
 */
public class UserServiceImpl implements UserService {

    // Logger for logging test execution
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());


    private final UserDao userDao;
    private final TokenDao tokenDao;

    /**
     * Constructor for UserServiceImpl, initializing the UserDao instance.
     *
     * @param userDao The UserDao instance for database operations.
     */
    public UserServiceImpl(UserDao userDao, TokenDao tokenDao) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
    }

    /**
     * Registers a new user by validating input, checking if the email already exists,
     * hashing the password, and saving the user to the database.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param role The role of the user (findRoommate or postRoom).
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
            String tokenCode = SecureRandomCode.generateCode();
            LocalDateTime requestedTime = LocalDateTime.now();
            Token token = new Token(userId, tokenCode, requestedTime, false);
            tokenDao.insertToken(token);

            String subject = "Xác nhận email";
            String content = "Click vào link sau để xác thực email: "
                    + "http://localhost:9999/homeSharing/verify?code=" + tokenCode
                    + "&userId=" + userId;
            SendingEmail.sendMail(email, subject, content);
            return "success";
        } catch (RuntimeException | MessagingException e) {
            // Handle runtime exceptions thrown by the UserDao
            return "Error during registration: " + e.getMessage();
        }
    }

    /**
     * Validates user input for registration.
     *
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @param confirmPassword The confirmation password.
     * @param role The role of the user.
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
}
