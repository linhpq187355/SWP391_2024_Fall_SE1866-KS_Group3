package com.homesharing.controller;

import com.homesharing.model.User;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ListUserServlet", value = "/accountmanage")
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
//        PrintWriter out = response.getWriter();
//        for (User user : userList) {
//            out.println(user);
//        }
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("account-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}