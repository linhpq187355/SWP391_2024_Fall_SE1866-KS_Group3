/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-27      1.1                 AI Assistant     Improved code and comments
 */
package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.dao.DistrictDAO;
import com.homesharing.dao.impl.DistrictDAOImpl;
import com.homesharing.model.District;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * The {@code GetDistrictsServlet} handles requests to retrieve a list of districts
 * based on a given province ID.  The servlet returns the list of districts in JSON format.
 * <p>
 * Bugs: None known.
 *
 * @author AI Assistant
 */
@WebServlet("/getDistricts")
public class GetDistrictsServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(GetDistrictsServlet.class);

    private DistrictDAO districtDAO;

    /**
     * Initializes the servlet by instantiating the {@link DistrictDAO}.
     */
    @Override
    public void init() {
        districtDAO = new DistrictDAOImpl();
    }

    /**
     * Handles GET requests to retrieve districts for a given province.
     *
     * @param request  The HttpServletRequest object containing the provinceId parameter.
     * @param response The HttpServletResponse object.
     * @throws IOException If an I/O error occurs.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            int provinceId = Integer.parseInt(request.getParameter("provinceId"));
            List<District> districts = districtDAO.getDistrictByProvinceId(provinceId);

            // Convert the list of districts to JSON using Gson
            Gson gson = new Gson();
            String json = gson.toJson(districts);

            // Set response content type and character encoding
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        } catch (NumberFormatException e) {
            // Handle invalid provinceId parameter
            logger.warn("Invalid provinceId parameter: {}", request.getParameter("provinceId"));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.getWriter().write("{\"error\": \"Invalid provinceId\"}"); // Return error JSON.
        } catch (IOException e) {  // Catching a broader Exception
            logger.error("Error retrieving districts: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error.
            response.getWriter().write("{\"error\": \"Server error\"}");
        }
    }
}

