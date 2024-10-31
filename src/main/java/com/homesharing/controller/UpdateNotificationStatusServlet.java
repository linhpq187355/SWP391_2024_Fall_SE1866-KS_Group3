package com.homesharing.controller;

import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.impl.NotificationDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateNotificationStatus")
public class UpdateNotificationStatusServlet extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        notificationDAO = new NotificationDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy ID thông báo từ request
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int notificationId = Integer.parseInt(idParam);

            // Cập nhật trạng thái thông báo
            boolean updated = false;
            try {
                updated = notificationDAO.markAsRead(notificationId);
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            // Gửi phản hồi về thành công hoặc không
            if (updated) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
