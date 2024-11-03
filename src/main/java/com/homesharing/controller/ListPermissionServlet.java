/*
 * Copyright(C) 2024, Roomify Inc.
 * Roomify: Roommate Matching and Home Sharing Service
 * Record of change:
 * DATE             Version              AUTHOR             DESCRIPTION
 * 2024-11-1        1.0                  ThangLT            First Implement
 */

package com.homesharing.controller;

import com.homesharing.model.Permission;
import com.homesharing.model.Role;
import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListPermission", value = "/dashboard/permission-list")
public class ListPermissionServlet extends HttpServlet {
    private UserManagementService userMgtService;

    @Override
    public void init() throws ServletException {
        userMgtService = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = userMgtService.getAllUsers();
        for (int i=0; i<userList.size(); i++) {
            List<Permission> permissions = userMgtService.fetchUserPermissions(userList.get(i).getId());
            userList.get(i).setPermissions(permissions);
        }
        List<Role> roleList = userMgtService.getAllRoles();
        request.setAttribute("userList", userList);
        request.setAttribute("roleList", roleList);
        request.getRequestDispatcher("/permission-list.jsp").forward(request, response);
    }
}
