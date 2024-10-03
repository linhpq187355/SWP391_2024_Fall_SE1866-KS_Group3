package com.homesharing.controller;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.service.ForgotPasswordService;
import com.homesharing.service.impl.ForgotPasswordServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet that handles forgot password requests. This servlet processes
 * POST requests from the forgot password form, sends a reset password
 * token to the user's email, and forwards the response to a JSP page with
 * an appropriate message.
 */

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    // Logger instance for logging error messages and application flow
    private static final Logger LOGGER = Logger.getLogger(ForgotPasswordServlet.class.getName());

    // Service for handling forgot password logic
    private ForgotPasswordService forgotPasswordService;

    /**
     * Initializes the ForgotPasswordServlet by setting up necessary
     * DAOs and ForgotPasswordService.
     *
     * This method is called once when the servlet is first created.
     */
    @Override
    public void init() throws ServletException {
        // Initialize UserDAO and TokenDAO, inject into ForgotPasswordService
        UserDAO userDao = new UserDAOImpl();
        TokenDAO tokenDao = new TokenDAOImpl();
        forgotPasswordService = new ForgotPasswordServiceImpl(userDao, tokenDao);
    }

    /**
     * Processes GET requests by forwarding to the forgot-password.jsp page.
     *
     * @param request  the HttpServletRequest object that contains the request
     *                 the client made to the servlet
     * @param response the HttpServletResponse object that contains the
     *                 response the servlet returns to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to forgot-password.jsp
        request.setAttribute("message", null);
        request.setAttribute("error", null);
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    /**
     * Processes POST requests for the forgot password functionality.
     * Retrieves the user's email from the request, attempts to send a
     * password reset token via email, and forwards the result to a
     * JSP page with either a success or error message.
     *
     * @param request  the HttpServletRequest object that contains the request
     *                 the client made to the servlet
     * @param response the HttpServletResponse object that contains the
     *                 response the servlet returns to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve email from request and trim leading/trailing spaces
        String email = request.getParameter("email").trim();

        try {
            // Call service to send reset password token
            boolean emailSent = forgotPasswordService.sendResetPasswordToken(email);

            if (emailSent) {
                request.setAttribute("message", "Vui lòng kiểm tra email để đặt lại mật khẩu.");
            } else {
                request.setAttribute("error", "Email không tồn tại.");
            }
        } catch (Exception e) {
            // Log exception using Logger and set error message
            LOGGER.log(Level.SEVERE, "Error during forgot password process", e);
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý.");
        }

        // Forward to forgot-password.jsp
        request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
    }
}