package com.homesharing.controller;

import com.homesharing.dao.AnnouncementDAO;
import com.homesharing.dao.UserDAO;
import com.homesharing.dao.impl.AnnouncementDAOImpl;
import com.homesharing.dao.impl.ReportDAOImpl;
import com.homesharing.dao.impl.ReportTypeDAOImpl;
import com.homesharing.dao.impl.UserDAOImpl;
import com.homesharing.model.Announcement;
import com.homesharing.model.Report;
import com.homesharing.model.User;
import com.homesharing.service.AnnouncementService;
import com.homesharing.service.impl.AnnouncementServiceImpl;
import com.homesharing.service.impl.ReportServiceImpl;
import com.homesharing.service.impl.UserServiceImpl;
import com.homesharing.util.CookieUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "CreateAnnouncementServlet", value = "/create-announcement")
public class CreateAnnouncementServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreateReportServlet.class);
    private AnnouncementDAO announcementDAO;
    private AnnouncementService announcementService;
    private UserServiceImpl userService;

    public void init() throws ServletException {
        announcementDAO = new AnnouncementDAOImpl();
        announcementService = new AnnouncementServiceImpl(announcementDAO);
        UserDAO userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO,null,null,null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String cookieValue = CookieUtil.getCookie(request, "id");
            if (cookieValue != null) {
                //Fetch user info from cookie
                User user = userService.getUser(Integer.parseInt(cookieValue));

                // Process the submission form data
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String type = request.getParameter("announcementTypeId");
                int type_num = Integer.parseInt(type);
                String status = request.getParameter("announcementStatus");
                Announcement announcement = new Announcement();
                announcement.setTitle(title);
                announcement.setContent(content);
                announcement.setAnnouncementTypeId(type_num);
                announcement.setCreatedDate(LocalDateTime.now());
                announcement.setModifiedDate(LocalDateTime.now());
                announcement.setStatus(status);
                announcement.setCreatedBy(user.getId());
                // Attempt to save the report
                announcementService.saveAnnouncement(announcement);
                response.sendRedirect("/HomeSharingWebsite_war_exploded/announcement");
            }
        } catch (Exception e) {
            logger.error("An error occurred during the request: " + e.getMessage(), e);
            e.printStackTrace();
            System.out.println("Error inserting data into database: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}