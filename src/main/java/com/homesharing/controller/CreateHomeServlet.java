package com.homesharing.controller;
import com.homesharing.model.Amentity;
import com.homesharing.model.FireEquipment;
import com.homesharing.model.Home;
import com.homesharing.model.HomeType;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/submit-home")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,  // 1 MB
        maxFileSize = 1024 * 1024 * 10,   // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class CreateHomeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreateHomeServlet.class);
    private SubmissonFormServiceImpl submissonFormService;

    @Override
    public void init() throws ServletException {
        submissonFormService = new SubmissonFormServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<HomeType> homeTypes = submissonFormService.getHomeTypes();
            List<Amentity> amentities = submissonFormService.getAmentities();
            List<FireEquipment> fireEquipments = submissonFormService.getFireEquipments();

            req.setAttribute("homeTypes", homeTypes);
            req.setAttribute("amentities", amentities);
            req.setAttribute("fireEquipments", fireEquipments);
            req.getRequestDispatcher("submit-home.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int homeTypeId = Integer.parseInt(req.getParameter("home-type"));
            String addressDetail = req.getParameter("address-detail");
            String name = req.getParameter("home-name");
            String homeDescription = req.getParameter("home-description");
            String tenantDescription = req.getParameter("tenant-description");
            BigDecimal area = new BigDecimal(req.getParameter("area"));
            String direction = req.getParameter("direction"); // This is the house direction
            int leaseDuration = Integer.parseInt(req.getParameter("leaseDuration"));

            String moveInDateString = req.getParameter("moveInDate");
            LocalDateTime moveInDateTime = null;

            if (moveInDateString != null && !moveInDateString.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate moveInDate = LocalDate.parse(moveInDateString, formatter);
                moveInDateTime = LocalDateTime.of(moveInDate, LocalTime.MIDNIGHT);
            }

            int numOfBedroom = Integer.parseInt(req.getParameter("numOfBedroom"));
            int numOfBath = Integer.parseInt(req.getParameter("numOfBath"));
            double price = Double.parseDouble(req.getParameter("price"));

            // Amenities and Fire Equipment
            String[] amenityIds = req.getParameterValues("amentityIds");
            String[] fireEquipIds = req.getParameterValues("fireEquipIds");

            Home home = new Home();
            home.setName(name);
            home.setHomeTypeId(homeTypeId);
            home.setAddress(addressDetail);
            home.setHomeDescription(homeDescription);
            home.setTenantDescription(tenantDescription);
            home.setArea(area);
            home.setOrientation(direction);
            home.setLeaseDuration(leaseDuration);
            home.setMoveInDate(moveInDateTime);
            home.setNumOfBedroom(numOfBedroom);
            home.setNumOfBath(numOfBath);
            home.setCreatedBy(1);

            submissonFormService.saveHome(home);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Redirect to an error page
            resp.sendRedirect("404.jsp");
        }
    }
}
