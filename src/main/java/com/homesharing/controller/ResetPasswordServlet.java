package com.homesharing.controller;

import com.homesharing.dao.TokenDAO;
import com.homesharing.dao.impl.TokenDAOImpl;
import com.homesharing.service.ResetPasswordService;
import com.homesharing.service.TokenService;
import com.homesharing.service.impl.ResetPasswordServiceImpl;
import com.homesharing.service.impl.TokenServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private transient TokenService tokenService;
    private transient ResetPasswordService resetPasswordService;

    public void init(){
        TokenDAO tokenDao = new TokenDAOImpl();
        tokenService = new TokenServiceImpl(tokenDao);
        resetPasswordService = new ResetPasswordServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String verificationCode = req.getParameter("code");
        String userIDString = req.getParameter("userId");

        try {
            boolean check = tokenService.checkToken(verificationCode, Integer.parseInt(userIDString));
            if (check) {
                req.setAttribute("userId",userIDString);
                req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
            } else {
                req.setAttribute("error","Xác thực không thành công.");
                req.getRequestDispatcher("forgot-password.jsp").forward(req, resp);
            }
        } catch (RuntimeException e) {
            // Handle any errors that occur during token verification

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("id"));
        String newPassword = req.getParameter("pass");
        String re_Password = req.getParameter("re_pass");

        if(newPassword.equals(re_Password)){
            int check = resetPasswordService.resetPassword(userId, newPassword);
            if (check>0) {
                req.setAttribute("message", "Mật khẩu đã được đặt lại thành công.");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Có lỗi xảy ra khi đặt lại mật khẩu.");
                req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error","Mật khẩu không khớp.");
            req.getRequestDispatcher("reset-password.jsp").forward(req, resp);
        }

    }
}
