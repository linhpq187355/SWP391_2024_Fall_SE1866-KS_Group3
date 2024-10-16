package com.homesharing.controller;

import com.homesharing.dao.ReportDAO;
import com.homesharing.dao.ReportTypeDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.ReportDAOImpl;
import com.homesharing.dao.impl.ReportTypeDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.*;
import com.homesharing.service.ReportService;
import com.homesharing.service.impl.ReportServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import com.homesharing.util.ImageUtil;
import com.homesharing.util.ServletUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "ReportServlet", value = "/report")
public class CreateReportServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreateReportServlet.class);
    private ReportService reportService;
    private ReportDAO reportDAO;
    private ReportTypeDAO reportTypeDAO;
    private UserServiceImpl userService;


    public void init() throws ServletException {
        reportDAO = new ReportDAOImpl();
        reportTypeDAO = new ReportTypeDAOImpl();
        reportService = new ReportServiceImpl(reportDAO, reportTypeDAO);
        UserDAO userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO,null,null,null);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String cookieValue = CookieUtil.getCookie(request, "id");
            if (cookieValue != null) {
                //Fetch user info from cookie
                User user = userService.getUser(Integer.parseInt(cookieValue));

                // Process the submission form data
                String homeId = request.getParameter("homeId");
                int homeId_num = Integer.parseInt(homeId);
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String type = request.getParameter("reportTypeId");
                int type_num = Integer.parseInt(type);
                Report report = new Report();
                report.setTitle(title);
                report.setDescription(description);
                report.setReportTypeId(type_num);
                report.setReportDate(LocalDateTime.now());
                report.setHomeId(homeId_num);
                report.setStatus("active");
                report.setSolvedDate(LocalDateTime.now());
                report.setCreatedBy(user.getId());
                // Attempt to save the report
                reportService.addReport(report);
            }
        } catch (Exception e) {
            logger.error("An error occurred during the request: " + e.getMessage(), e);
            e.printStackTrace();
            System.out.println("Error inserting data into database: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}