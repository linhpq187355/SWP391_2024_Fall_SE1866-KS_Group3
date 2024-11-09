package com.homesharing.controller;

import com.homesharing.service.WishListService;

import com.homesharing.service.impl.WishListServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/delete-wishlist")
public class UpdateWistList extends HttpServlet{
    private WishListService wishListService;

    @Override
    public void init() throws ServletException {
        this.wishListService = new WishListServiceImpl();
    }

    // Handles POST requests to mark a wishlist item as inactive (soft delete)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve home ID from request and user ID from cookies
        String homeIdStr = request.getParameter("homeId");
        String userIdStr = CookieUtil.getCookie(request, "id");
        String redirectTo = request.getParameter("redirectTo"); // Tham số redirectTo

        // Validate if home ID or user ID is missing, return an error if so
        if (homeIdStr == null || homeIdStr.isEmpty() || userIdStr == null || userIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing home ID or user ID");
            return;
        }

        try {
            // Parse the home and user IDs as integers
            int homeId = Integer.parseInt(homeIdStr);
            int userId = Integer.parseInt(userIdStr);
            String status = "inactive"; // Set status to "inactive" for soft deletion

            wishListService.removeWishList(userId, homeId, status);
            request.getSession().setAttribute("message", "Xóa thành công khỏi danh sách yêu thích");

            // Redirect the user to their wishlist page after successful update
            response.sendRedirect("user-wishlist?page=1");

        } catch (NumberFormatException e) {
            // Handle case where home ID or user ID is not a valid number
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            // Handle general exceptions, log the error, and inform the user
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
