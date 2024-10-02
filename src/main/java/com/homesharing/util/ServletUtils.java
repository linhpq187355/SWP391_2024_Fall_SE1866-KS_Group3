package com.homesharing.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class for handling common servlet operations.
 * This class cannot be instantiated.
 */
public class ServletUtils {

    // Private constructor to prevent instantiation
    private ServletUtils() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);
    private static final String ERROR_PAGE = "/404.jsp";
    private static final String FORWARD_ERROR_MESSAGE = "Error forwarding to error page.";

    /**
     * Forwards the request to the error page.
     *
     * @param req the HttpServletRequest object that contains the request
     * @param resp the HttpServletResponse object for the response
     */
    public static void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        } catch (ServletException | IOException e) {
            handleError(resp, FORWARD_ERROR_MESSAGE);
        }
    }

    /**
     * Sends an error response to the client with the specified message.
     *
     * @param resp the HttpServletResponse object for the response
     * @param message the error message to be sent
     */
    public static void handleError(HttpServletResponse resp, String message) {
        try {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        } catch (IOException e) {
            // Log the error (assuming logger is available)
            logger.error("An error occurred while sending error response: {}", e.getMessage(), e);
        }
    }
}
