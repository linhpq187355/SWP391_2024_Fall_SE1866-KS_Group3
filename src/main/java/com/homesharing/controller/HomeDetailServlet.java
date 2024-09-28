package com.homesharing.controller;

import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.dao.impl.HomeDetailDAOImpl;
import com.homesharing.model.Home;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/home-detail")
public class HomeDetailServlet extends HttpServlet {

    private final HomeDetailDAO homeDetailDAO = new HomeDetailDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get homeId from the request
        String homeIdParam = request.getParameter("id");
        if (homeIdParam != null) {
            try {
                int homeId = Integer.parseInt(homeIdParam);

                // Fetch the home details
                Home home = homeDetailDAO.getHomeById(homeId);

                if (home != null) {
                    // Set the home object as a request attribute
                    request.setAttribute("home", home);

                    // Forward the request to the JSP page for rendering
                    request.getRequestDispatcher("/Hometst.jsp").forward(request, response);
                } else {
                    // If no home found, redirect or show an error
                    response.sendRedirect("/error.jsp");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("/error.jsp");  // Invalid homeId
            }
        } else {
            response.sendRedirect("/error.jsp");  // No homeId provided
        }
    }
}
