/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-10      1.0                 ManhNC         First Implement
 */
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

/**
 * Servlet used to update the number of OTP attempts for a user.
 * The client sends a JSON object with the number of attempts,
 * which is stored in the user's session.
 *
 * @author ManhNC
 */
@WebServlet("/updateOtpAttempts")
public class UpdateOtpAttemptsServlet extends HttpServlet {

    /**
     * Handles the HTTP POST request to update OTP attempts.
     * The request contains a JSON object with the number of OTP attempts,
     * which is then saved in the session.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get times input otp from jsp
        BufferedReader reader = request.getReader();
        String json = reader.lines().collect(Collectors.joining());
        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();

        // Parse the number of OTP attempts from the request body
        int attempts = jsonObj.get("attempts").getAsInt();

        // update into session
        HttpSession session = request.getSession();
        session.setAttribute("otpAttempts", attempts);

        // send response success
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"success\"}");
    }
}