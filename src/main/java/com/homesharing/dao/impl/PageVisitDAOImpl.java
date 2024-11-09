/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing: Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version              AUTHOR           DESCRIPTION
 * 2024-10-06      1.0                  ThangLT         First Implement
 */

package com.homesharing.dao.impl;


import com.homesharing.conf.DBContext;
import com.homesharing.dao.PageVisitDAO;
import com.homesharing.model.PageVisit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PageVisitDAOImpl extends DBContext implements PageVisitDAO {
    private static final Logger logger = Logger.getLogger(HomeDAOImpl.class.getName());

    @Override
    public void incrementTotalVisit(String url) {
        String sql = "MERGE INTO PageVisits AS target " +
                "USING (SELECT ?) AS source (url) " +
                "ON target.url = source.url " +
                "WHEN MATCHED THEN " +
                "    UPDATE SET totalVisits = totalVisits + 1 " +
                "WHEN NOT MATCHED THEN " +
                "    INSERT (url, totalVisits) VALUES (source.url, 1);";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, url);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void incrementMemberVisit(String url) {
        String sql = "MERGE INTO PageVisits AS target " +
                "USING (SELECT ?) AS source (url) " +
                "ON target.url = source.url " +
                "WHEN MATCHED THEN " +
                "    UPDATE SET memberVisits = memberVisits + 1 " +
                "WHEN NOT MATCHED THEN " +
                "    INSERT (url, memberVisits) VALUES (source.url, 1);";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, url);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public List<PageVisit> getPageVisits() {
        List<PageVisit> visits = new ArrayList<>();
        String sql = "SELECT url, pageName, totalVisits, memberVisits FROM PageVisits";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                PageVisit visit = new PageVisit();
                visit.setUrl(resultSet.getString("url"));
                visit.setPageName(resultSet.getString("pageName"));
                visit.setTotalVisits(resultSet.getInt("totalVisits"));
                visit.setMemberVisits(resultSet.getInt("memberVisits"));
                visits.add(visit);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.severe(e.getMessage());
        }
        return visits;
    }

    public static void main(String[] args) {
        PageVisitDAOImpl pgVisit = new PageVisitDAOImpl();
        List<PageVisit> pgs = pgVisit.getPageVisits();
        System.out.println(pgs);
    }
}
