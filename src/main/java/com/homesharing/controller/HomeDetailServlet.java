package com.homesharing.controller;

import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.model.User;
import com.homesharing.service.HomeDetailService;
import com.homesharing.service.impl.HomeDetailServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet( "/home-detail")
public class HomeDetailServlet extends HttpServlet {

    private HomeDetailService homeDetailService;

    @Override
    public void init() throws ServletException {
        this.homeDetailService = new HomeDetailServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String homeIdParam = req.getParameter("id");

        if (homeIdParam == null || homeIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Home ID is required");
            return;
        }

        int homeId;
        try {
            homeId = Integer.parseInt(homeIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Home ID format");
            return;
        }

        // Fetch home details
        Home home = homeDetailService.getHomeById(homeId);
        if (home == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Home not found");
            return;
        }

        // Fetch prices and creator details
        List<Price> prices = homeDetailService.getHomePricesByHomeId(homeId);
        User creator = homeDetailService.getCreatorByHomeId(homeId);

        // Set attributes to request to forward them to the JSP
        req.setAttribute("home", home);
        req.setAttribute("prices", prices);
        req.setAttribute("creator", creator);

        // Forward the request to the JSP
        req.getRequestDispatcher("home-detail.jsp").forward(req, resp);
    }
}
