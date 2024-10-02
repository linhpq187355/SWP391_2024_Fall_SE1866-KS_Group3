package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.model.Location;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class GeocodingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int provinceId = Integer.parseInt(req.getParameter("provinceId"));
        int districtId = Integer.parseInt(req.getParameter("districtId"));
        int wardId = Integer.parseInt(req.getParameter("wardId"));

        Location location = getLocationCoordinates(provinceId, districtId, wardId);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        out.println(gson.toJson(location));
        out.flush();
    }

    private Location getLocationCoordinates(int provinceId, int districtId, int wardId) {
        return new Location(21.0278, 105.8342); // default coordinate: Hanoi coordinates
    }
}
