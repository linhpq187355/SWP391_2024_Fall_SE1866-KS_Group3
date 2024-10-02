package com.homesharing.controller;

import com.google.gson.Gson;
import com.homesharing.model.District;
import com.homesharing.model.Ward;
import com.homesharing.service.impl.SubmissonFormServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

@WebServlet("/LocationServlet")
public class LocationServlet extends HttpServlet {
    private SubmissonFormServiceImpl submissonFormService;

    @Override
    public void init() throws ServletException {
        submissonFormService = new SubmissonFormServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        if(action == null) {
            out.print(gson.toJson(Collections.emptyList()));
            out.flush();
            return;
        }

        int parentId;
        try {
             parentId = Integer.parseInt(req.getParameter("parentId"));
        } catch(NumberFormatException e) {
            out.print(gson.toJson(Collections.emptyList()));
            out.flush();
            return;
        }

        if ("getDistricts".equals(action)) {
            List<District> districts = submissonFormService.getDistrictByWard(parentId);
            out.print(gson.toJson(districts));
        } else if ("getWards".equals(action)) {
            List<Ward> wards = submissonFormService.getWardByDistrict(parentId);
            out.print(gson.toJson(wards));
        } else {
            out.print(gson.toJson(Collections.emptyList()));
        }

        out.flush();
    }
}
