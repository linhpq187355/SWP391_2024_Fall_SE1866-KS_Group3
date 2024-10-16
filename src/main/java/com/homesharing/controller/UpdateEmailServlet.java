/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0                 ManhNC         First Implement
 */
package com.homesharing.controller;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.User;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Servlet responsible for handling email update requests.
 * It allows users to update their email addresses and verifies
 * the new email through a token sent to the provided email address.
 *
 * <p>This servlet handles both GET and POST requests for updating the user's email.
 * On GET, it displays the update form, and on POST, it processes the email update.</p>
 *
 * @author ManhNC
 */
@WebServlet("/user-update-email")
public class UpdateEmailServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateEmailServlet.class.getName());
    private UserService userService;
    private UserDAO userDAO;
    private TokenDAO tokenDAO;
    private TokenService tokenService;

    /**
     * Initializes the servlet by setting up required services and DAOs.
     * This method is called once when the servlet is first loaded into memory.
     *
     * @throws ServletException if initialization fails
     */
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        tokenDAO = new TokenDAOImpl();
        this.tokenService = new TokenServiceImpl(tokenDAO);
        this.userService = new UserServiceImpl(userDAO, null, tokenService, null);
    }

    /**
     * Handles GET requests by displaying the email update page.
     * Fetches user information and preferences from the database
     * and forwards the request to the JSP page for updating the email.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Retrieve user ID from the cookie
        String userIdCookie = CookieUtil.getCookie(req, "id");
        if (userIdCookie == null) {
            LOGGER.warning("User ID cookie is missing.");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID cookie is missing.");
            return;
        }

        int userId = Integer.parseInt(userIdCookie);

        // Fetch user information and preferences using the services
        User user = null;

            user = userService.getUser(userId);
            if (user == null) {
                ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
                return;
            }
            req.getSession().setAttribute("userId", user.getId());
            req.getSession().setAttribute("oldEmail", user.getEmail());


        // Get message and error from request parameters
        String message = req.getParameter("message");
        String error = req.getParameter("error");

        // Set attributes for the JSP page

        req.setAttribute("message", message);
        req.setAttribute("error", error);

        // Forward the request to user-profile.jsp
        req.getRequestDispatcher("/update-email.jsp").forward(req, resp);
    }

    /**
     * Handles POST requests by processing the user's email update.
     * It validates the new email and sends a verification token to the provided address.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userIdObj = req.getSession().getAttribute("userId");
        Object oldEmailObj = req.getSession().getAttribute("oldEmail");

        if (userIdObj != null && oldEmailObj != null) {
            try {
                int userId = Integer.parseInt(userIdObj.toString());
                String oldEmail = oldEmailObj.toString();
                String email = req.getParameter("email");

                // Check if the new email is different from the old one
                if(oldEmail.equals(email)) {
                    String error = "Email mới phải khác email cũ!";
                    req.setAttribute("error", error);
                    req.getRequestDispatcher("/update-email.jsp").forward(req, resp);
                    return;
                }

                // Check if the new email already exists
                if(userDAO.emailExists(email)) {
                    String error = "Email này đã được sử dụng, vui lòng dùng email khác!";
                    req.setAttribute("error", error);
                    req.getRequestDispatcher("/update-email.jsp").forward(req, resp);
                    return;
                }

                // Validate the new email
                if(!userService.validateEmail(email)){
                    String error = "Email này không hợp lệ!";
                    req.setAttribute("error", error);
                    req.getRequestDispatcher("/update-email.jsp").forward(req, resp);
                    return;
                }

                // Send a verification token to the new email
                tokenService.sendToken(email,userId);
                req.getSession().setAttribute("userId", userId);
                req.getSession().setAttribute("email", email);
                resp.sendRedirect(req.getContextPath() + "/verify-new");
            } catch (NumberFormatException | SQLException e) {
                ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
            }
        } else {
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
        }
    }

}
