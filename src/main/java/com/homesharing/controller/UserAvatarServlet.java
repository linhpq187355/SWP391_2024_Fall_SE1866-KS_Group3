package com.homesharing.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@WebServlet("/user-avatar/*")
public class UserAvatarServlet extends HttpServlet {
    private static final String AVATAR_DIR = "D:/Java/HomeSharingWebsite/target/HomeSharingWebsite-1.0-SNAPSHOT/assets/img/user-avatar";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String avatarFileName = req.getPathInfo().substring(1);
        File avatarFile = new File(AVATAR_DIR, avatarFileName);

        if (avatarFile.exists()) {
            resp.setContentType(getServletContext().getMimeType(avatarFile.getName()));
            Files.copy(avatarFile.toPath(), resp.getOutputStream());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Avatar not found");
        }
    }
}
