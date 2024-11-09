/*
 * Copyright(C) 2024, Roomify Inc.
 * Roomify: Roommate Matching and Home Sharing Service
 * Record of change:
 * DATE             Version              AUTHOR             DESCRIPTION
 * 2024-11-1        1.0                  ThangLT            First Implement
 */

package com.homesharing.controller;

import com.homesharing.model.Permission;
import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GrantPermissionList", value = "/dashboard/permission-update")
public class GrantPermissionServlet extends HttpServlet {
    private UserManagementService userMgtService;

    @Override
    public void init() throws ServletException {
        userMgtService = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                List<Permission> userPermission = userMgtService.fetchUserPermissions(userId);
                List<Permission> permissionList = userMgtService.fetchAllPermisison();
                User selectedUser = userMgtService.getUserById(userId);
                request.setAttribute("userPermission", userPermission);
                request.setAttribute("permissionList", permissionList);
                request.setAttribute("selectedUser", selectedUser);
                request.getRequestDispatcher("/permission-update.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                List<Permission> allowedPermissions = new ArrayList<>();
                for (String paramName : request.getParameterMap().keySet()) {
                    String paramValue = request.getParameter(paramName);
                    if ("allow".equals(paramValue)) {
                        Permission perm = userMgtService.fetchPermissionByName(paramName);
                        allowedPermissions.add(perm);
                    }
                }
                userMgtService.updateUserPermission(allowedPermissions, userId);
                request.getRequestDispatcher("permission-list.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
        }
    }
}
