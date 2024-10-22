/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-02      1.0                 ManhNC         First Implement
 */
package com.homesharing.controller;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.GoogleAccount;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.GoogleUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The {@code GoogleSignUpServlet} class handles user registration requests via Google accounts.
 * It processes form submissions for account creation, validates inputs,
 * and communicates with the UserService to register new users.
 *
 * @version 1.0
 * @since 2024-10-02
 * @author ManhNC
 */
@WebServlet("/sign-up-google")
public class GoogleSignUpServlet extends HttpServlet {

    private transient UserService userService; // Service for managing user-related operations

    private static final Logger logger = LoggerFactory.getLogger(GoogleSignUpServlet.class); // Logger instance
    private static final String ERROR_ATTRIBUTE = "error"; // Define constant for error attribute

    /**
     * Initializes the servlet by instantiating the required DAOs and services.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        PreferenceDAO preferenceDao = new PreferenceDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        PreferenceService preferenceService = new PreferenceServiceImpl(preferenceDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,preferenceService);
    }

    /**
     * Handles GET requests for signing up with a Google account.
     * Retrieves the authorization code, exchanges it for an access token,
     * and gets user information from Google. Then attempts to register the user.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs during the request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String code = request.getParameter("code");
            if (code == null) {
                request.setAttribute("error", "Quá trình xác thực bị hủy. Vui lòng thử lại.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            String accessToken = GoogleUtil.getToken(code);
            GoogleAccount googleAccount = GoogleUtil.getUserInfo(accessToken);

            int result = userService.registerByGoogle(googleAccount, null, response);

            switch (result) {
                case 1: // Login successful
                    request.getSession().setAttribute("message", "Đăng nhập thành công.");
                    request.getSession().setAttribute("messageType", "success");
                    response.sendRedirect(request.getContextPath() + "/home-page");
                    break;
                case -1: // Account needs role assignment
                    HttpSession session = request.getSession();
                    session.setAttribute("googleAccount", googleAccount);
                    response.sendRedirect(request.getContextPath() + "/set-role");
                    break;
                default: // Registration/Login failed
                    request.setAttribute(ERROR_ATTRIBUTE, "Có lỗi xảy ra ở phía server.");
                    ServletUtils.forwardToErrorPage(request, response);
            }

        } catch (SQLException e) {
            logger.error("Error during Google sign-up/login: {}", e.getMessage(), e);
            ServletUtils.handleError(response, "Error while processing your request."); // Generic error message for the user.
        }
    }
}
