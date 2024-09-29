package com.homesharing.controller;

import com.homesharing.model.Preference;
import com.homesharing.model.User;
import com.homesharing.service.UserProfileService;
import com.homesharing.service.impl.UserProfileServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/user-profile")
public class UserProfileServlet extends HttpServlet {
    private UserProfileService userProfileService;

    @Override
    public void init() throws ServletException {
        this.userProfileService = new UserProfileServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = userProfileService.getUser(Integer.parseInt(Objects.requireNonNull(CookieUtil.getCookie(req, "id"))));
        Preference preference = userProfileService.getPreference(Integer.parseInt(Objects.requireNonNull(CookieUtil.getCookie(req, "id"))));

        req.setAttribute("user", user);
        req.setAttribute("preference", preference);

        req.getRequestDispatcher("/user-profile.jsp").forward(req, resp);

    }
}
