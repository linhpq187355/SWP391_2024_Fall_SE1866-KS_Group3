/*
 * Copyright(C) 2024, HomeSharing Project.
 * Roomify
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-11-06      1.0                 ThangLT          First Implement
 */

package com.homesharing.filter;

import jakarta.annotation.Priority;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebFilter(urlPatterns = {"/dashboard/*"})
@Priority(3)
public class PermissionFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthorizationFilter.class.getName());
    private Map<String, String> urlPermissionMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Map each URL pattern to a required permission
        urlPermissionMap.put("/dashboard/account-list", "PERMISSION_VIEW_ACCOUNTS");
        urlPermissionMap.put("/dashboard/create-account", "PERMISSION_CREATE_MEMBER");
        urlPermissionMap.put("/dashboard/account-status", "PERMISSION_TOGGLE_MEMBER_STATUS");
        urlPermissionMap.put("/dashboard/home-list", "PERMISSION_VIEW_HOMES");
        urlPermissionMap.put("/dashboard/home-status", "PERMISSION_TOGGLE_HOME_STATUS");
        urlPermissionMap.put("/dashboard/report-list", "PERMISSION_VIEW_REPORTS");
        urlPermissionMap.put("/dashboard/report-status", "PERMISSION_TOGGLE_REPORT_STATUS");
        urlPermissionMap.put("/dashboard/announcement-manage", "PERMISSION_VIEW_ANNOUNCEMENT");
        urlPermissionMap.put("/dashboard/create-announcement", "PERMISSION_SEND_ANNOUNCEMENT");
        urlPermissionMap.put("/dashboard/announce-status", "PERMISSION_TOGGLE_ANNOUNCEMENT_STATUS");
        urlPermissionMap.put("/dashboard/blog-list", "PERMISSION_VIEW_BLOG");
        urlPermissionMap.put("/dashboard/blog-status", "PERMISSION_TOGGLE_BLOG_STATUS");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        // Get required permission for this URL
        String requiredPermission = urlPermissionMap.get(requestURI);

        // If no specific permission is required, continue as usual
        if (requiredPermission == null) {
            chain.doFilter(request, response);
            return;
        }

        // Retrieve user permissions from session or database
        List<String> userPermissions = (List<String>) httpRequest.getSession().getAttribute("userPermissions");

        // Check if user has the required permission
        if (userPermissions != null && userPermissions.contains(requiredPermission)) {
            chain.doFilter(request, response); // User has permission, proceed
        } else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this resource.");
            ((HttpServletResponse) response).sendRedirect("404.jsp");
        }
    }
}
