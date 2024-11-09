package com.homesharing.controller;

import com.homesharing.dao.AnnouncementDAO;
import com.homesharing.dao.impl.AnnouncementDAOImpl;
import com.homesharing.model.AnnouncementType;
import com.homesharing.service.AnnouncementService;
import com.homesharing.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AnnouncementServlet", value = "/announcement")
public class AnnouncementServlet extends HttpServlet {
    private AnnouncementDAO announcementDAO;
    private AnnouncementService announcementService;
    public void init() throws ServletException {
        announcementDAO = new AnnouncementDAOImpl();
        this.announcementService = new AnnouncementServiceImpl(announcementDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AnnouncementType> announcementTypes = announcementService.getAllAnnouncementTypes();
        request.setAttribute("announcementTypes", announcementTypes);
        request.getRequestDispatcher("create-announcement.jsp").forward(request, response);
    }

}