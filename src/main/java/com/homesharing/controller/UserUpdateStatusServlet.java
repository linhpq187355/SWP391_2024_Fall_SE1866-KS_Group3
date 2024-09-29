package com.homesharing.controller;

import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UserUpdateStatusServlet", value = "/update-status")
public class UserUpdateStatusServlet extends HttpServlet {
    private static UserManagementService userManagementService;

    @Override
    public void init() throws ServletException {
        this.userManagementService = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        try {
            int uid = Integer.parseInt(userId);
            String status = userManagementService.getUserById(uid).getStatus();
            if (status.equalsIgnoreCase("active")) {
                status = "inactive";
                userManagementService.updateUserStatus(uid, status);
                response.sendRedirect("account-manage");
            } else {
                status = "active";
                userManagementService.updateUserStatus(uid, status);
                response.sendRedirect("account-manage");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}