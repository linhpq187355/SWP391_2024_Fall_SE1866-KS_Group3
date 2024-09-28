package com.homesharing.controller;

import com.homesharing.dao.SearchDAO;
import com.homesharing.dao.impl.SearchDAOimpl;
import com.homesharing.model.Home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")  // Specify the URL pattern
public class SearchServlet extends HttpServlet {

    private SearchDAO searchDAO;

    @Override
    public void init() throws ServletException {
        // Initialize the DAO implementation when the servlet is created
        searchDAO = new SearchDAOimpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the 'name' parameter from the HTTP request
        String name = request.getParameter("name");

        // Use the DAO to search for homes by name
        List<Home> homes = searchDAO.searchHomesByName(name);

        // Set the results as a request attribute to forward to JSP
        request.setAttribute("homes", homes);

        // Forward the request to the JSP page to display the results
        request.getRequestDispatcher("/search-results.jsp").forward(request, response);
    }
}
