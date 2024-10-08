package com.homesharing.controller;

import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.User;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("/user-update-password")
public class UpdatePasswordServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdatePasswordServlet.class.getName());
    private UserService userService;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO, null, null, null);
    }

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
        try {
            int hadPass = userDAO.passWordExists(userId);
            if (hadPass == -1) {
                LOGGER.warning("User ID cookie is missing.");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID cookie is missing.");
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("hadPass", hadPass);
            user = userService.getUser(userId);
        } catch (SQLException e) {
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
        }
        // Get message and error from request parameters
        String message = req.getParameter("message");
        String error = req.getParameter("error");

        // Set attributes for the JSP page
        req.setAttribute("user", user);
        req.setAttribute("message", message);
        req.setAttribute("error", error);

        // Forward the request to user-profile.jsp
        req.getRequestDispatcher("/update-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve user ID from the cookie
        String userIdCookie = CookieUtil.getCookie(req, "id");
        if (userIdCookie == null) {
            LOGGER.warning("User ID cookie is missing.");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID cookie is missing.");
            return;
        }

        int userId = Integer.parseInt(userIdCookie);
        String hadPassRaw = req.getSession().getAttribute("hadPass").toString();
        if (hadPassRaw == null) {
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
            return;
        }
        int hadPass = Integer.parseInt(hadPassRaw);
        String password = req.getParameter("password");
        String oldPassword = req.getParameter("old-password");
        try {
            int result = userService.updatePassword(userId, hadPass, oldPassword, password);
            if(result == 1) {
                req.setAttribute("message", "Cập nhật mật khẩu thành công.");
                req.getRequestDispatcher("/user-security.jsp").forward(req, resp);
            } else if (result == -1) {
                req.setAttribute("error", "Mật khẩu cũ không khớp.");
                req.getRequestDispatcher("/update-password.jsp").forward(req, resp);
            } else {
                ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
            }
        } catch (SQLException e) {
            ServletUtils.forwardWithMessage(req,resp,"Có lỗi xảy ra, vui lòng đăng nhập lại.");
        }
    }

}
