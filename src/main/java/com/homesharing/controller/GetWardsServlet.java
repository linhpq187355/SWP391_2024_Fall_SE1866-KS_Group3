package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.dao.WardDAO;
import com.homesharing.dao.impl.WardDAOImpl;
import com.homesharing.model.Ward;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * The {@code GetWardsServlet} handles requests to retrieve a list of wards
 * based on a given district ID.  The servlet returns the list of wards in JSON format.
 * <p>
 * Bugs: None known.
 *
 * @author AI Assistant
 */
@WebServlet("/getWards")
public class GetWardsServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(GetWardsServlet.class);

    private WardDAO wardDAO;

    /**
     * Initializes the servlet by instantiating the {@link WardDAO}.
     */
    @Override
    public void init() throws ServletException {
        wardDAO = new WardDAOImpl();
    }

    /**
     * Handles GET requests to retrieve wards for a given district.
     *
     * @param request  The HttpServletRequest object containing the districtId parameter.
     * @param response The HttpServletResponse object.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Retrieve districtId parameter and parse it as an integer
            int districtId = Integer.parseInt(request.getParameter("districtId"));

            // Retrieve the list of wards from the database
            List<Ward> wards = wardDAO.getWardsByDistrictId(districtId);

            // Convert the list of wards to JSON
            Gson gson = new Gson();
            String json = gson.toJson(wards);

            // Set response content type and character encoding
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Write the JSON string to the response
            response.getWriter().write(json);

        } catch (NumberFormatException e) {
            // Handle invalid districtId parameter
            logger.warn("Invalid districtId parameter: {}", request.getParameter("districtId"));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid districtId\"}");
        } catch (Exception e) {
            // Handle other exceptions during database access or JSON processing
            logger.error("Error retrieving wards: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Server error\"}");
        }
    }
}