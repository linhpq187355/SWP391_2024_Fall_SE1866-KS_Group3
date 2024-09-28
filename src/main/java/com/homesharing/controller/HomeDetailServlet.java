package com.homesharing.controller;

import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.HomeDetailService;
import com.homesharing.service.impl.HomeDetailServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/homeDetail")
public class HomeDetailServlet extends HttpServlet {
    private HomeDetailService homeDetailService;

    @Override
    public void init() {
        homeDetailService = new HomeDetailServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String homeIdParam = request.getParameter("id");

        if (homeIdParam != null && !homeIdParam.isEmpty()) {
            int homeId = Integer.parseInt(homeIdParam);
            Home home = homeDetailService.getHomeById(homeId);
            List<Price> prices = homeDetailService.getHomePrice(List.of(home)); // Get prices for the specific home

            request.setAttribute("home", home);
            request.setAttribute("prices", prices);

            // Forward to JSP
            request.getRequestDispatcher("/WEB-INF/views/homeDetail.jsp").forward(request, response);
        } else {
            // Handle error case
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Home ID is required");
        }
    }
}