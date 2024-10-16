/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0                 ManhNC         First Implement
 */
package com.homesharing.filter;

import com.homesharing.util.CookieUtil;
import jakarta.annotation.Priority;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authorization filter for controlling access to the dashboard.
 * This filter checks the user's role ID stored in a cookie to determine if they have permission to access dashboard resources.
 * Users with appropriate role IDs (e.g., admin or staff) are allowed access, while others are redirected to the staff login page.
 * @author ManhNC
 */
@WebFilter(urlPatterns = {"/dashboard/*"})
@Priority(2)
public class AuthorizationFilter implements Filter {

    /**
     * Initializes the filter. This method is called by the servlet container only once upon filter creation.
     *
     * @param filterConfig The FilterConfig object containing configuration information.
     * @throws ServletException If an error occurs during initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No specific initialization required for this filter.
    }

    /**
     * Performs the authorization filtering logic. Checks the user's role ID from the cookie.
     * If the user has the required role, the request is allowed to proceed.
     * Otherwise, the user is redirected to the staff login page.
     *
     * @param request  The ServletRequest object.
     * @param response The ServletResponse object.
     * @param chain    The FilterChain object for invoking the next filter or the resource.
     * @throws IOException      If an I/O error occurs during processing.
     * @throws ServletException If a servlet error occurs during processing.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String roleId = CookieUtil.getCookie(httpRequest, "roleId");

        // Check if the user has a valid role ID (1 for admin, 2 for staff)
        if (roleId != null && ((roleId.equals("1")) || roleId.equals("2"))) {
            // User has permission, continue request processing
            chain.doFilter(request, response);
        } else {
            // User does not have permission, redirect to staff login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/staff-login");
        }
    }

    /**
     * Called by the servlet container to indicate that the filter is being taken out of service.
     * This method is called only once, after all threads within the filter's doFilter method have exited
     * or after a timeout period has passed.
     */
    @Override
    public void destroy() {
        // No specific cleanup required for this filter.
    }
}
