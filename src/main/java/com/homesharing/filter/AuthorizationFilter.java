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

import com.auth0.jwt.interfaces.Claim;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.JwtUtil;
import jakarta.annotation.Priority;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Authorization filter for controlling access to the dashboard.
 * This filter checks the user's role ID stored in a cookie to determine if they have permission to access dashboard resources.
 * Users with appropriate role IDs (e.g., admin or staff) are allowed access, while others are redirected to the staff login page.
 * @author ManhNC
 */
@WebFilter(urlPatterns = {"/dashboard/*"})
@Priority(2)
public class AuthorizationFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AuthorizationFilter.class.getName());

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

        String encodedEmail = CookieUtil.getCookie(httpRequest, "email");
        String email = CookieUtil.decodeCookieValue(encodedEmail);
        String jwt = CookieUtil.getCookie(httpRequest, "authToken");
        Map<String, Claim> claims = JwtUtil.getAllClaims(jwt);
        if (claims != null) {
            String roleId = claims.get("role").asString(); // Cast to String
            String jwtMail = claims.get("email").asString();
            if(jwtMail.equals(email)) {
                if ("1".equals(roleId) || "2".equals(roleId)) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
                }
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
            }
        } else {
            // User does not have permission, redirect to staff login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
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
