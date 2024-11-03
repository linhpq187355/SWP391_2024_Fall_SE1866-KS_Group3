    package com.homesharing.controller;
    
    import com.homesharing.dao.*;
    import com.homesharing.dao.impl.HomeDAOImpl;
    import com.homesharing.dao.impl.PriceDAOImpl;
    import com.homesharing.dao.impl.WardDAOImpl;
    import com.homesharing.dao.impl.WishListDAOImpl;
    import com.homesharing.model.*;
    import com.homesharing.service.HomeDetailService;
    import com.homesharing.service.HomePageService;
    import com.homesharing.service.WishListService;
    import com.homesharing.service.impl.HomeDetailServiceImpl;
    import com.homesharing.service.impl.HomePageServiceImpl;
    import com.homesharing.service.impl.WishListServiceImpl;
    import com.homesharing.util.CookieUtil;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    import java.io.PrintWriter;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.io.IOException;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;
    
    @WebServlet("/home-detail") // Annotation to map this servlet to the specified URL
    public class HomeDetailServlet extends HttpServlet {
        private HomePageService homePageService;  // Service layer for home page logic
        private HomeDAO homeDAO;  // Data Access Object for accessing home data
        private PriceDAO priceDAO;
        private UserDAO userDAO;
        private WardDAO wardDAO;
        private HomeDetailService homeDetailService;
        private WishListService wishListService;

        /**
         * Initializes the HomeDetailServlet by creating an instance of the HomeDetailService.
         * This method is called once when the servlet is first loaded.
         */
        @Override
        public void init() throws ServletException {
            homeDAO = new HomeDAOImpl();
            priceDAO = new PriceDAOImpl();
            wardDAO = new WardDAOImpl();
            homePageService = new HomePageServiceImpl(homeDAO, priceDAO,userDAO,wardDAO);
            this.homeDetailService = new HomeDetailServiceImpl();
            this.wishListService = new WishListServiceImpl();
        }

        /**
         * Handles GET requests to retrieve home details based on the provided home ID.
         * Fetches associated details such as prices, creator, home types, amenities, and fire equipment.
         *
         * @param req The HttpServletRequest object containing the request data.
         * @param resp The HttpServletResponse object for sending responses to the client.
         * @throws ServletException if the request cannot be handled.
         * @throws IOException if an input or output error occurs during the handling of the request.
         */
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String homeIdParam = req.getParameter("id");
            String userIdStr = CookieUtil.getCookie(req, "id");
            int priceDifference = 1000000;

            // Check if the home ID exists
            if (homeIdParam == null || homeIdParam.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Home ID is required");
                return;
            }

            int homeId;
            try {
                homeId = Integer.parseInt(homeIdParam);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Home ID format");
                return;
            }

            // Retrieve home details
            Home home = homeDetailService.getHomeById(homeId);
            if (home == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Home not found");
                return;
            }

            // Retrieve message from request attribute if available
            String message = (String) req.getAttribute("message");
            req.setAttribute("message", message); // Pass the message to JSP
            boolean isInWishlist = false;
            // Retrieve additional home details
            List<Price> prices = homeDetailService.getHomePricesByHomeId(homeId);
            User creator = homeDetailService.getCreatorByHomeId(homeId);
            List<HomeType> homeTypes = homeDetailService.getHomeTypesByHomeId(homeId);
            List<Amentity> amenities = homeDetailService.getHomeAmenitiesByHomeId(homeId);
            List<FireEquipment> fireEquipments = homeDetailService.getHomeFireEquipmentsByHomeId(homeId);
            List<Home> similarHomes = homeDetailService.getSimilarHomess(homeId,priceDifference);
            List<Price> priceSimilarHomes = homeDetailService.getSimilarHomePrices(similarHomes);

            // Get pricing data for similar homes

            // Format the move-in date
            LocalDate moveInDate = home.getMoveInDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedMoveInDate = (moveInDate != null) ? moveInDate.format(formatter) : "No information available";
            if (userIdStr != null && !userIdStr.isEmpty()) {
                int userId;
                try {
                    userId = Integer.parseInt(userIdStr);
                    // Check if the home is in the user's wishlist
                    isInWishlist = wishListService.isAlreadyInWishlist(userId, homeId, "active");
                } catch (NumberFormatException e) {
                    // Handle invalid user ID
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID format");
                    return;
                }
            }
            PrintWriter out = resp.getWriter();
            out.println(similarHomes.size());
            // Set attributes for the request to pass to the JSP
            req.getSession().setAttribute("isInWishlist", isInWishlist);
            req.setAttribute("homeTypes", homeTypes);
            req.setAttribute("home", home);
            req.setAttribute("prices", prices);
            req.setAttribute("creator", creator);
            req.setAttribute("amenities", amenities);
            req.setAttribute("fireEquipments", fireEquipments);
            req.setAttribute("similarHomes", similarHomes);
            req.setAttribute("priceSimilarHomes", priceSimilarHomes);
            req.setAttribute("formattedMoveInDate", formattedMoveInDate);

//            // Forward the request to the JSP page
            req.getRequestDispatcher("home-detail.jsp").forward(req, resp);
        }
    }
