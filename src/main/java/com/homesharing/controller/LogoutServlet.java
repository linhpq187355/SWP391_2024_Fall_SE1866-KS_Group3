package com.homesharing.controller;

import com.homesharing.dao.TokenDao;
import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.TokenDaoImpl;
import com.homesharing.dao.impl.UserDaoImpl;
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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private transient UserService userService;// Mark userService as transient
    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class); // Logger instance

    @Override
    public void init() {
        // Create instances of UserDao and TokenDao
        UserDao userDao = new UserDaoImpl();
        TokenDao tokenDao = new TokenDaoImpl();
        TokenService tokenService = new TokenServiceImpl(tokenDao);
        // Inject UserDao into UserServiceImpl
        userService = new UserServiceImpl(userDao, tokenDao,tokenService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String logoutMessage = userService.logout(resp);
            logger.info(logoutMessage);
            resp.sendRedirect("home-page");
        } catch (Exception e) {
            logger.error("Error processing logout request: {}", e.getMessage(), e);
            ServletUtils.handleError(resp, "Error while processing your logout request.");
        }

    }
}
