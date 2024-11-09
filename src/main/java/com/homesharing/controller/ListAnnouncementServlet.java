package com.homesharing.controller;

import com.homesharing.dao.AnnouncementDAO;
import com.homesharing.dao.impl.AnnouncementDAOImpl;
import com.homesharing.model.Announcement;
import com.homesharing.model.AnnouncementType;
import com.homesharing.model.User;
import com.homesharing.service.AnnouncementService;
import com.homesharing.service.UserManagementService;
import com.homesharing.service.impl.AnnouncementServiceImpl;
import com.homesharing.service.impl.UserManagementServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListAnnouncementServlet", value = "/dashboard/announcement-list")
public class ListAnnouncementServlet extends HttpServlet {
    private AnnouncementDAO announcementDAO;
    private AnnouncementService announcementService;
    private UserManagementService userManagementService;
    public void init() throws ServletException {
        this.announcementDAO = new AnnouncementDAOImpl();
        this.announcementService = new AnnouncementServiceImpl(announcementDAO);
        userManagementService = new UserManagementServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        List<AnnouncementType> announcementTypes = announcementService.getAllAnnouncementTypes();
        List<User> users = userManagementService.getAllUsers();
        request.setAttribute("announcements", announcements);
        request.setAttribute("announcementTypes", announcementTypes);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/announcement-list.jsp").forward(request, response);
    }
}