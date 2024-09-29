package com.homesharing.controller;


import com.homesharing.model.Home;
import com.homesharing.model.Price;
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
import java.sql.SQLException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");

        List<Home> homes = new ArrayList<>();
        int minPrice = 0;
        int maxPrice;

        try {
            if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
                minPrice = Integer.parseInt(minPriceStr);
            }

            if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                maxPrice = Integer.parseInt(maxPriceStr);
            } else {
                maxPrice = searchService.getMaxPrice();
            }

            if (name != null && !name.trim().isEmpty()) {
                homes = searchService.searchHomesByName(name);
            } else {
                // Nếu không nhập tên, chỉ tìm kiếm theo khoảng giá
                homes = searchService.searchByPriceRange(minPrice, maxPrice);
            }
        } catch (NumberFormatException e) {
            // Xử lý nếu giá trị nhập vào không phải số
            req.setAttribute("error", "Giá trị không hợp lệ cho khoảng giá. Vui lòng nhập số.");
            homes = HomePageService.getHomes(); // Hoặc có thể lấy danh sách nhà mặc định
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi trong quá trình tìm kiếm: " + e.getMessage(), e);
        }

        req.setAttribute("homes", homes);
        req.setAttribute("searchName", name);
        req.setAttribute("minPrice", minPriceStr);
        req.setAttribute("maxPrice", maxPriceStr);

        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}

