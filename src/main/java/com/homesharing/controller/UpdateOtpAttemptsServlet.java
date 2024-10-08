package com.homesharing.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/updateOtpAttempts")
public class UpdateOtpAttemptsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy số lần nhập OTP từ request
        BufferedReader reader = request.getReader();
        String json = reader.lines().collect(Collectors.joining());
        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();

        int attempts = jsonObj.get("attempts").getAsInt();

        // Cập nhật số lần nhập OTP vào session
        HttpSession session = request.getSession();
        session.setAttribute("otpAttempts", attempts);

        // Gửi phản hồi thành công
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"success\"}");
    }
}