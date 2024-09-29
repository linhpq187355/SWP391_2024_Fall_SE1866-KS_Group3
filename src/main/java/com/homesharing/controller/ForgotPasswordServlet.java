package com.homesharing.controller;

import com.homesharing.dao.TokenDao;
import com.homesharing.dao.UserDao;
import com.homesharing.dao.impl.TokenDaoImpl;
import com.homesharing.dao.impl.UserDaoImpl;
import com.homesharing.model.User;
import com.homesharing.service.ForgotPasswordService;
import com.homesharing.service.TokenService;
import com.homesharing.service.impl.ForgotPasswordServiceImpl;
import com.homesharing.service.impl.TokenServiceImpl;
import com.homesharing.service.impl.UserProfileServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private ForgotPasswordService forgotPasswordService;

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDaoImpl();  // Khởi tạo UserDao
        TokenDao tokenDao = new TokenDaoImpl();  // Khởi tạo TokenDao
        forgotPasswordService = new ForgotPasswordServiceImpl(userDao, tokenDao); // Sử dụng lớp triển khai
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try {
            // Gọi hàm sendResetPasswordToken từ ForgotPasswordService
            boolean emailSent = forgotPasswordService.sendResetPasswordToken(email);

            if (emailSent) {
                request.setAttribute("message", "Vui lòng kiểm tra email để đặt lại mật khẩu.");
            } else {
                request.setAttribute("error", "Email không tồn tại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý.");
        }

        // Forward về trang JSP quên mật khẩu
        request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
    }
}
