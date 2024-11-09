package com.homesharing.controller;

import com.homesharing.model.WishList;
import com.homesharing.service.WishListService;
import com.homesharing.service.impl.WishListServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/add-to-wishlist")
public class AddToWishList extends HttpServlet {
    private WishListService wishListService;

    @Override
    public void init() throws ServletException {
        this.wishListService = new WishListServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy homeId và userId từ request
        String homeIdStr = request.getParameter("homeId");
        String userIdStr = CookieUtil.getCookie(request, "id");

        // Kiểm tra nếu thiếu thông tin
        if (homeIdStr == null || homeIdStr.isEmpty() || userIdStr == null || userIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Missing home ID or user ID\"}");
            return;
        }

        try {
            int homeId = Integer.parseInt(homeIdStr);
            int userId = Integer.parseInt(userIdStr);
            String message = "";

            // Kiểm tra nếu nhà đã có trong danh sách yêu thích
            if (wishListService.isAlreadyInWishlist(userId, homeId, "active")) {
                message = "Bạn đã thêm nhà này vào danh sách yêu thích rồi.";
                response.getWriter().write("{\"success\": false, \"message\": \"" + message + "\"}");
            } else {
                // Thêm nhà vào danh sách yêu thích
                WishList wishlist = new WishList();
                wishlist.setUserId(userId);
                wishlist.setHomeId(homeId);
                wishlist.setCreatedDate(LocalDateTime.now());
                wishlist.setStatus("active");
                wishListService.addWishList(wishlist);

                message = "Thêm thành công vào danh sách yêu thích";
                response.getWriter().write("{\"success\": true, \"message\": \"" + message + "\"}");
            }
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue());
                }
            } else {
                System.out.println("No cookies found");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }
}
