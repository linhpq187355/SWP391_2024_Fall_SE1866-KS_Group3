package com.homesharing.controller;


import com.homesharing.model.Home;
import com.homesharing.service.HomePageService;
import com.homesharing.service.SearchSevice;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.SearchServiceImpl;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.homesharing.service.HomePageService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchHomes")
public class SearchServlet extends HttpServlet {

    private SearchSevice searchService;
    private HomePageService HomePageService;

    @Override
    public void init() throws ServletException {
        searchService = new SearchServiceImpl();
        HomePageService = new HomePageServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        List<Home> homes = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            homes = searchService.searchHomesByName(name);
        }else{
            homes = HomePageService.getHomes();
        }
        req.setAttribute("homes", homes);

        req.setAttribute("searchName", name);

        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}

