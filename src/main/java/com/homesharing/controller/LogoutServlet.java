package com.homesharing.controller;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.TokenService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * LogoutServlet handles user logout requests by interacting with the
 * UserService to perform logout operations. It processes the logout action
 * and redirects the user to the home page.
 *
 * @version 1.0
 * @since 2024-10-02
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private transient UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class); // Logger instance

    /**
     * Initializes the LogoutServlet by creating instances of necessary services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao, tokenService,null);
    }

    /**
     * Handles GET requests for the logout action. This method calls the
     * UserService to log out the user, logs the logout action, and redirects
     * the user to the home page with a success message.
     *
     * @param req  HttpServletRequest object that contains the request
     *             the client has made of the servlet
     * @param resp HttpServletResponse object that contains the response
     *             the servlet sends to the client
     * @throws ServletException If an input or output error occurs
     * @throws IOException      If the request could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String logoutMessage = userService.logout(resp);
            logger.info(logoutMessage);
            // Redirect to home page on success
            req.getSession().setAttribute("message", "Đăng xuất thành công.");
            req.getSession().setAttribute("messageType", "success");
            resp.sendRedirect(req.getContextPath() + "/home-page");

        } catch (Exception e) {
            logger.error("Error processing logout request: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your logout request.");
        }

    }
}
