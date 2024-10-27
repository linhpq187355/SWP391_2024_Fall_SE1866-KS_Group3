package com.homesharing.controller;


import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.HomePageService;
import com.homesharing.service.SearchSevice;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.SearchServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.List;

// URL pattern mapped to this servlet
@WebServlet("/searchHomes")
public class SearchServlet extends HttpServlet {

    private SearchSevice searchService;
    private HomePageService homePageService;
    private HomeDAO homeDAO;  // Data Access Object for accessing home data
    private PriceDAO priceDAO;  // Data Access Object for accessing price data
    private UserDAO userDAO;

    /**
     * Initializes the SearchServlet by creating instances of necessary DAOs and services.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() throws ServletException {
        homeDAO = new HomeDAOImpl();
        priceDAO = new PriceDAOImpl();
        userDAO = new UserDAOImpl();
        try {
            searchService = new SearchServiceImpl();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        homePageService = new HomePageServiceImpl(homeDAO, priceDAO, userDAO);
    }

    /**
     * Handles GET requests to search for homes based on provided parameters.
     * Retrieves search parameters, performs search, and forwards results to the JSP.
     *
     * @param req  The HttpServletRequest object containing the request data.
     * @param resp The HttpServletResponse object for sending responses to the client.
     * @throws ServletException if the request cannot be handled.
     * @throws IOException      if an input or output error occurs during the handling of the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy tham số tìm kiếm từ request
        String name = req.getParameter("name") != null ? req.getParameter("name").trim().replaceAll("\\s+", " ").replaceAll("[^a-zA-Z0-9\\s]", "") : null;

        // Lấy giá trị minPrice và maxPrice từ thanh trượt giá
        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");

        // Giá tối thiểu và tối đa mặc định
        int minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Integer.parseInt(minPriceStr) : 0;
        int maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Integer.parseInt(maxPriceStr) : 100000;

        if (minPriceStr != null && !minPriceStr.isEmpty()) {
            minPrice = Integer.parseInt(minPriceStr);
        }
        if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
            maxPrice = Integer.parseInt(maxPriceStr);
        }

        List<Home> homes = homePageService.getNewHomes();

        try {
            // Tìm kiếm theo tên hoặc khoảng giá
            if (name != null && !name.trim().isEmpty()) {
                homes = searchService.searchHomesByAdress(name); // Tìm kiếm theo tên
            } else {
                homes = searchService.searchByPriceRange(minPrice, maxPrice); // Tìm kiếm theo khoảng giá
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Lỗi định dạng giá!"); // Thông báo lỗi khi định dạng số không hợp lệ
            homes = homePageService.getNewHomes(); // Trả về danh sách tất cả nhà nếu xảy ra lỗi
            throw new RuntimeException("Lỗi: " + e.getMessage(), e);
        }

        // Đặt các thuộc tính cho request để chuyển tới JSP
        req.setAttribute("homes", homes);
        req.setAttribute("searchName", name);
        req.setAttribute("minPrice", minPrice);  // Gửi giá trị minPrice lên JSP
        req.setAttribute("maxPrice", maxPrice);  // Gửi giá trị maxPrice lên JSP

        // Chuyển tiếp request tới trang JSP để hiển thị kết quả
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

}

