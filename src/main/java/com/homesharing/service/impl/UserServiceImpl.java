package com.homesharing.service.impl;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Token;
import com.homesharing.model.User;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.PasswordUtil;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Implementation of UserService interface, handling user-related business logic.
 * This class manages user registration and input validation.
 */
public class UserServiceImpl implements UserService {

    private final UserDAO userDao;
    private final TokenDAO tokenDao;
    private final TokenService tokenService;

    /**
     * Constructor for UserServiceImpl, initializing the UserDao instance.
     *
     * @param userDao The UserDao instance for database operations.
     */
    public UserServiceImpl(UserDAO userDao, TokenDAO tokenDao, TokenService tokenService) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
        this.tokenService = tokenService;
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
            return "Tài khoản này chưa được xác thực,  hãy click vào đường link trong email để xác thực tài khoản.";
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

    @Override
    public String loginAdmin(String email, String password, HttpServletResponse response) {
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
            return "Tài khoản này chưa được xác thực,  hãy click vào đường link trong email để xác thực tài khoản.";
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
}
