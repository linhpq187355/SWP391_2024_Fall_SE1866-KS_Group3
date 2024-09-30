package com.homesharing.controller;

import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.impl.HomePageServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.homesharing.service.HomePageService;

import java.util.List;

import java.io.IOException;


@WebServlet("/home-page")
public class HomePageServlet extends HttpServlet {

    private HomePageService homePageService;

    @Override
    public void init() throws ServletException {
        homePageService = new HomePageServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get message
        String message = (String) req.getAttribute("message");
        List<Home> homes = homePageService.getNewHomes();
        List<Price> prices = homePageService.getHomePrice(homes);
        req.setAttribute("homes", homes);
        req.setAttribute("prices", prices);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = (String) req.getSession().getAttribute("message");
        if (message != null) {
            req.setAttribute("message", message);
            req.getSession().removeAttribute("message"); // Xoá message sau khi lấy ra
        }
        String messageType = (String) req.getSession().getAttribute("messageType");
        if (messageType != null) {
            req.setAttribute("messageType", messageType);
            req.getSession().removeAttribute("messageType"); // Xoá messageType sau khi lấy ra
        }
        List<Home> homes = homePageService.getNewHomes();
        List<Price> prices = homePageService.getHomePrice(homes);
        req.setAttribute("homes", homes);
        req.setAttribute("prices", prices);
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

}
