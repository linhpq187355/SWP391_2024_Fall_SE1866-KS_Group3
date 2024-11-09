package com.homesharing.controller;

import com.homesharing.dao.HomeDAO;
import com.homesharing.dao.HomeDetailDAO;
import com.homesharing.dao.PriceDAO;
import com.homesharing.dao.impl.HomeDAOImpl;
import com.homesharing.dao.impl.HomeDetailDAOImpl;
import com.homesharing.dao.impl.PriceDAOImpl;
import com.homesharing.model.Home;
import com.homesharing.model.Price;
import com.homesharing.service.HomePageService;
import com.homesharing.service.WishListService;
import com.homesharing.service.impl.HomePageServiceImpl;
import com.homesharing.service.impl.WishListServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user-wishlist") // Annotation to map this servlet to the specified URL
public class WishListServlet extends HttpServlet {

    private HomePageService homePageService;  // Service layer for home page logic
    private PriceDAO priceDAO;  // Data Access Object for accessing price data
    private HomeDetailDAO homeDAO;
    private WishListService wishListService;

    /**
     * Initializes the WishListServlet by creating an instance of the WishListService.
     * This method is called once when the servlet is first loaded.
     */
    @Override
    public void init() throws ServletException {
        this.wishListService = new WishListServiceImpl();
        homeDAO = new HomeDetailDAOImpl();
        priceDAO = new PriceDAOImpl();
        // Initialize the home page service with the required DAOs
    }
    /**
     * Handles GET requests to display the user's wishlist.
     * Retrieves the user ID from the cookies and fetches their wishlist.
     * Supports pagination to display a limited number of homes per page.
     *
     * @param request  The HttpServletRequest object containing the request data.
     * @param response The HttpServletResponse object for sending responses to the client.
     * @throws ServletException if the request cannot be handled.
     * @throws IOException      if an input or output error occurs during the handling of the request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the user ID from the cookies
        String userId = CookieUtil.getCookie(request, "id");

        // Validate if the user ID is missing or empty
        if (userId == null || userId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user ID");
            return;
        }

        try {
            int userIdInt = Integer.parseInt(userId); // Parse user ID to an integer
            List<Home> wishlist = wishListService.getWishlistByUserId(userIdInt); // Fetch the wishlist by user ID

            // Pagination logic: 5 items per page, starting from page 1
            int pageSize = 5;
            int pageNumber = 1;

            // Check if the request contains a page parameter, and parse it
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                pageNumber = Integer.parseInt(pageParam);
            }

            // Calculate total items and total pages for pagination
            int totalItems = wishlist.size();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            int startItem = (pageNumber - 1) * pageSize;

            // Sublist the wishlist based on the current page and page size
            List<Home> paginatedWishlist = wishlist.subList(
                    Math.min(startItem, totalItems),
                    Math.min(startItem + pageSize, totalItems));
            List<Price> prices = priceDAO.getPrices(wishlist);
            // Set attributes for the paginated wishlist and pagination details
            request.setAttribute("wishlist", paginatedWishlist);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("prices", prices);


            // Forward the request to the "user-wishlist.jsp" page to display the wishlist
            request.getRequestDispatcher("user-wishlist.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Handle invalid user ID format
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
        } catch (Exception e) {
            // Catch and handle other exceptions
            e.printStackTrace();
            response.getWriter().write("An error occurred: " + e.getMessage());
        }
    }
}
/**
 * Handles POST requests to add a home to the wishlist.
 * Retrieves home ID and user ID from the request, and calls the service layer to add the home to the wishlist.
 *
 * @param request The HttpServletRequest object containing the request data.
 * @param response The HttpServletResponse object for sending responses to the client.
 * @throws ServletException if the request cannot be handled.
 * @throws IOException if an input or output error occurs during the handling of the request.
 */

