package com.homesharing.controller;

import com.homesharing.model.WishList;
import com.homesharing.service.WishListService;
import com.homesharing.service.impl.WishListServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
//add wishlist method
@WebServlet("/add-wishlist")
public class AddWistList extends HttpServlet {
    private WishListService wishListService;

    // Initialization method to instantiate the wishlist service
    @Override
    public void init() throws ServletException {
        this.wishListService = new WishListServiceImpl();
    }

    // Handles POST requests to add an item to the wishlist
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve home ID from the request and user ID from cookies
        String homeIdStr = request.getParameter("homeId");
        String userIdStr = CookieUtil.getCookie(request, "id");

        // Check if home ID or user ID is missing, return an error response if so
        if (homeIdStr == null || homeIdStr.isEmpty() || userIdStr == null || userIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing home ID or user ID");
            return;
        }

        try {
            // Parse the home and user IDs as integers
            int homeId = Integer.parseInt(homeIdStr);
            int userId = Integer.parseInt(userIdStr);
            String message = ""; // Variable to hold the response message
            boolean isInWishlist = wishListService.isAlreadyInWishlist(userId, homeId, "active");

            // Check if the home is already in the user's wishlist with status "active"
            if (wishListService.isAlreadyInWishlist(userId, homeId, "active")) {
                message = "Bạn đã thêm nhà này vào danh sách yêu thích rồi.";
            } else {
                // Check if the home was previously in the wishlist with status "inactive"
                if (wishListService.isAlreadyInWishlist(userId, homeId, "inactive")) {
                    // Re-activate the wishlist item by changing the status to "active"
                    wishListService.removeWishList(userId, homeId, "active");
                    message = "Thêm thành công vào danh sách yêu thích";
                } else {
                    // Create a new wishlist entry for the user and home
                    WishList wishlist = new WishList();
                    wishlist.setUserId(userId);
                    wishlist.setHomeId(homeId);
                    wishlist.setCreatedDate(LocalDateTime.now());
                    wishlist.setStatus("active");
                    wishListService.addWishList(wishlist); // Add the wishlist entry
                    message = "Thêm thành công vào danh sách yêu thích";
                }
            }

            // Set the success message in session and redirect to the home detail page
            request.getSession().setAttribute("message", message);
            request.getSession().setAttribute("isInWishlist", isInWishlist);
            response.sendRedirect("home-detail?id=" + homeId);
        } catch (NumberFormatException e) {
            // Handle case where home ID or user ID is not a valid number
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            // Handle general exceptions, log the error and redirect with error message
            e.printStackTrace();
            int homeId = Integer.parseInt(request.getParameter("homeId"));
            String message = "An error occurred: " + e.getMessage();
            request.getSession().setAttribute("message", message);
            response.sendRedirect("home-detail?id=" + homeId);
        }
    }
}
