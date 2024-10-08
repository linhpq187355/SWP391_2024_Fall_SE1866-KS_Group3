/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class for handling common servlet operations in the Home Sharing System.
 * This class provides methods for forwarding requests to error pages and handling error responses.
 * All methods are static, and the class cannot be instantiated.
 *
 * @version 1.0
 * @since 2024-09-18
 * @author ManhNC
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

    /**
     * Helper method to forward the request to the announcement page with a message.
     * The message is displayed on the target page.
     *
     * @param request  HttpServletRequest containing the client request.
     * @param response HttpServletResponse used to send a response to the client.
     * @param message  The message to be displayed to the user.
     */
    public static void forwardWithMessage(HttpServletRequest request, HttpServletResponse response, String message) {
        request.setAttribute("notificationMessage", message);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/announce.jsp");
            dispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            logger.error("Error forwarding to announce page: {}", e.getMessage(), e); // Log the exception for debugging
        }
    }

}
