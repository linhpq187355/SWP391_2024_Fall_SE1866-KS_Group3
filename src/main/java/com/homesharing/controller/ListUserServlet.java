package com.homesharing.controller;

import com.homesharing.model.Role;
import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListUserServlet", value = "/account-manage")
public class ListUserServlet extends HttpServlet {
    private UserManagementService userManagementServie;

    /**
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        userManagementServie = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = userManagementServie.getAllUsers();
        List<Role> roleList = userManagementServie.getAllRoles();
        request.setAttribute("userList", userList);
        request.setAttribute("roleList", roleList);
        request.getRequestDispatcher("account-list.jsp").forward(request, response);
    }

}