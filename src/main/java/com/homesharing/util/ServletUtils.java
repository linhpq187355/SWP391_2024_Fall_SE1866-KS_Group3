package com.homesharing.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class for handling servlet-related operations such as
 * redirection and forwarding requests.
 * This class provides static methods to redirect to the home page
 * and forward to an error page.
 */
public class ServletUtils {

    // Private constructor to prevent instantiation
    private ServletUtils() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);
    private static final String ERROR_PAGE = "/404.jsp";
    private static final String REDIRECT_ERROR_MESSAGE = "Error redirecting to home page.";
    private static final String FORWARD_ERROR_MESSAGE = "Error forwarding to error page.";

    /**
     * Redirects the response to the home page.
     *
     * @param resp The HttpServletResponse object that contains the response the servlet
     *             returns to the client.
     */
    public static void redirectToHomePage( HttpServletResponse resp) {
        try {
            resp.sendRedirect("home-page");
        } catch (IOException e) {
            handleError(resp, REDIRECT_ERROR_MESSAGE);
        }
    }

    /**
     * Forwards the request to the error page.
     *
     * @param req The HttpServletRequest object that contains the request the client made
     *            of the servlet.
     * @param resp The HttpServletResponse object that contains the response the servlet
     *             returns to the client.
     */
    public static void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            handleError(resp, FORWARD_ERROR_MESSAGE);
        }
    }

    /**
     * Handles sending an error response to the client.
     *
     * @param resp The HttpServletResponse object that contains the response the servlet
     *             returns to the client.
     * @param message The error message to be sent in the response.
     */
    public static void handleError(HttpServletResponse resp, String message) {
        try {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException e) {
            // Log the error (assuming logger is available)
            logger.error("An error occurred while sending error response: {}", e.getMessage(), e);
        }
    }

    /**
     * Forwards the request to the announcement page with a notification message.
     *
     * @param request  the HttpServletRequest object that contains the request
     * @param response the HttpServletResponse object that contains the response
     * @param message  the message to be displayed on the announcement page
     */
    public static void forwardWithMessage(HttpServletRequest request, HttpServletResponse response, String message) {
        request.setAttribute("notificationMessage", message);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/announce.jsp");
            dispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            // Log the exception for debugging
            logger.error("Error forwarding to announce page: {}", e.getMessage(), e); // Log the exception for debugging
        }
    }
}
