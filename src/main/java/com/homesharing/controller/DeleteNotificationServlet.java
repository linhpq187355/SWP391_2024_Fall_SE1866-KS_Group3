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

@WebServlet("/deleteNotificationServlet")
public class DeleteNotificationServlet extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo DAO
        notificationDAO = new NotificationDAOImpl(); // Giả định bạn có DAO để quản lý thông báo
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy ID thông báo từ request
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int notificationId = Integer.parseInt(idParam);

                // Gọi phương thức xóa thông báo từ DAO
                boolean deleted = notificationDAO.deleteNotification(notificationId);

                // Phản hồi cho client
                if (deleted) {
                    response.setStatus(HttpServletResponse.SC_OK); // Xóa thành công
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Không tìm thấy thông báo để xóa
                }
            } catch (NumberFormatException | SQLException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // ID không hợp lệ
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // ID không được cung cấp
        }
    }
}