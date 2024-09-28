package com.homesharing.controller;


import com.homesharing.model.Home;
import com.homesharing.service.SearchSevice;
import com.homesharing.service.impl.SearchServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchHomes")
public class SearchServlet extends HttpServlet {

    private SearchSevice searchService;

    @Override
    public void init() throws ServletException {
        searchService = new SearchServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        List<Home> homes = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            homes = searchService.searchHomesByName(name);
        }
       request.setAttribute("homes", homes);

     request.setAttribute("searchName", name);

      request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}

