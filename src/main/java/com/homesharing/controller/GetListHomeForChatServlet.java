package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.PreferenceDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.PreferenceDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Preference;
import com.homesharing.model.Price;
import com.homesharing.model.User;
import com.homesharing.service.HomePageService;
import com.homesharing.service.PreferenceService;
import com.homesharing.service.UserService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.PreferenceServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/get-list-home-for-chat")
public class GetListHomeForChatServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GetListHomeForChatServlet.class);
    private HomeDAO homeDAO;  // Data Access Object for accessing home data

    /**
     * Initializes the servlet by creating an instance of HomeDAO.
     * The init method is called once when the servlet is first loaded into memory.
     *
     * @throws ServletException if servlet initialization fails
     */
    @Override
    public void init() throws ServletException {
        // Initialize the DAOs
        homeDAO = new HomeDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Assume userId is passed as a parameter
            String userIdStr = request.getParameter("userId");
            int userId = Integer.parseInt(userIdStr);

            // Use homePageService to fetch homes by user
            List<Home> userHomes = homeDAO.getByCreatedBy(userId);
            for (Home home : userHomes) {
                List<String> images = new ArrayList<>();
                images.add(homeDAO.fetchFirstImage(home.getId()));
                home.setImages(images);
            }
            // Chuyển đổi danh sách căn nhà thành JSON
            List<Map<String, Object>> homeList = new ArrayList<>();
            for (Home home : userHomes) {
                Map<String, Object> homeData = new HashMap<>();
                homeData.put("id", home.getId());
                homeData.put("name", home.getName());
                homeData.put("address", home.getAddress());
                homeData.put("area", home.getArea());
                homeData.put("createdDate", home.getCreatedDate().toString());
                homeData.put("price", home.getPrice());
                homeData.put("images", home.getImages());
                homeList.add(homeData);
            }

            // Set loại trả về là JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Sử dụng thư viện JSON (ví dụ Gson hoặc Jackson) để chuyển đối tượng thành JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(homeList);

            // Gửi phản hồi JSON về phía client
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            // Nếu có lỗi xảy ra, trả về lỗi HTTP
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Có lỗi xảy ra khi lấy dữ liệu\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Assume userId is passed as a parameter
            String userIdStr = request.getParameter("userId");
            int userId = Integer.parseInt(userIdStr);

            // Use homePageService to fetch homes by user
            List<Home> userHomes = homeDAO.getByCreatedBy(userId);
            for (Home home : userHomes) {
                List<String> images = new ArrayList<>();
                images.add(homeDAO.fetchFirstImage(home.getId()));
                home.setImages(images);
            }
            // Chuyển đổi danh sách căn nhà thành JSON
            List<Map<String, Object>> homeList = new ArrayList<>();
            for (Home home : userHomes) {
                Map<String, Object> homeData = new HashMap<>();
                homeData.put("id", home.getId());
                homeData.put("name", home.getName());
                homeData.put("address", home.getAddress());
                homeData.put("area", home.getArea());
                homeData.put("createdDate", home.getCreatedDate().toString());
                homeData.put("price", home.getPrice());
                homeData.put("images", home.getImages());
                homeList.add(homeData);
            }

            // Set loại trả về là JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Sử dụng thư viện JSON (ví dụ Gson hoặc Jackson) để chuyển đối tượng thành JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(homeList);
            // Gửi phản hồi JSON về phía client
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            // Nếu có lỗi xảy ra, trả về lỗi HTTP
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Có lỗi xảy ra khi lấy dữ liệu\"}");
        }
    }
}
