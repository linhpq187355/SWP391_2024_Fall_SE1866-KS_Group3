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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Authentication filter for intercepting requests and enforcing authentication.
 * This filter checks for the presence of user ID and email in cookies to determine if a user is logged in.
 * If not logged in, the user is redirected to the login page.
 * @author ManhNC
 */
@WebFilter(urlPatterns = {"/*"})
@Priority(1)
public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final List<String> excludedUrls = Arrays.asList(
            "/login.jsp", "/assets", "/bootstrap", "/register.jsp", "/css/", "/js/", "/images/","/set-role","/getDistricts","/getWards",
            "/home-page", "/login", "/logout", "/signup", "/staff-login", "/verify", "/sign-up-google","/500.jsp","/403.jsp", "/400.jsp",
            "/sign-up.jsp", "/home.jsp", "/header.jsp", "/footer.jsp", "/staff-login.jsp","/error.jsp","/home-list", "/401.jsp",
            "/terms.jsp", "/announce.jsp", "/about-us.jsp", "/404.jsp", "/input-otp.jsp","/input-otp-2.jsp");

    /**
     * Initializes the filter.  This method is called by the servlet container only once upon filter creation.
     *
     * @param filterConfig The FilterConfig object containing configuration information.
     * @throws ServletException If an error occurs during initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No specific initialization needed for this filter.
    }

    /**
     * Performs the filtering logic for each request. Checks for user authentication based on cookies.
     * If the user is not authenticated and the request is not for an excluded URL,
     * the user is redirected to the login page.
     *
     * @param request  The ServletRequest object.
     * @param response The ServletResponse object.
     * @param chain    The FilterChain object to continue the request processing.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet error occurs.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        LOGGER.info("Requested URL Path: " + path);

        // Check if the URL should be excluded from authentication checks
        if (isUrlExcluded(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is logged in (based on cookies)
        String userId = CookieUtil.getCookie(httpRequest, "id");
        String email = CookieUtil.getCookie(httpRequest, "email");

        if (userId != null && email != null) {
            // User is logged in, continue request processing
            chain.doFilter(request, response);
        } else {
            // User is not logged in, redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    /**
     * Called by the servlet container to indicate to a filter that it is being taken out of service.
     * This method is only called once all threads within the filter's doFilter method have exited
     * or after a timeout period has passed.
     */
    @Override
    public void destroy() {
        // No specific cleanup needed for this filter.
    }

    /**
     * Checks if the given URL path should be excluded from authentication filtering.
     * @param path The URL path to check.
     * @return True if the URL should be excluded, false otherwise.
     */
    private boolean isUrlExcluded(String path) {
        return excludedUrls.stream().anyMatch(path::startsWith);
    }
}
