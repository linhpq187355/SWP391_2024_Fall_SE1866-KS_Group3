package com.homesharing.controller;

import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UserActivateServlet", value = "/account-status")
public class UserActivateServlet extends HttpServlet {
    private static UserManagementService userManagementService;

    @Override
    public void init() throws ServletException {
        userManagementService = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        try {
            int uid = Integer.parseInt(userId);
            userManagementService.updateUserStatus(uid, "active");
            response.sendRedirect("account-list");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}