package com.homesharing.controller;

import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.UserDaoImpl;
import com.homesharing.model.Preference;
import com.homesharing.model.User;
import com.homesharing.service.UserProfileService;
import com.homesharing.service.impl.UserProfileServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user-profile")
public class UserProfileServlet extends HttpServlet {
    private UserProfileService userProfileService;

    @Override
    public void init() throws ServletException {
        this.userProfileService = new UserProfileServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userProfileService.getUser(1);
        Preference preference = userProfileService.getPreference(1);

        req.setAttribute("user", user);
        req.setAttribute("preference", preference);

        req.getRequestDispatcher("/user-profile.jsp").forward(req, resp);

    }
}