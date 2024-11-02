package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.exception.GeneralException;
import com.homesharing.dao.ReportDAO;
import com.homesharing.model.Report;
import com.homesharing.model.ReportType;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDAOImpl implements ReportDAO {
    private static final Logger logger = Logger.getLogger(ReportTypeDAOImpl.class.getName());
    /**
     * @return
     */
    @Override
    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT [id]\n" +
                "      ,[reportDate]\n" +
                "      ,[title]\n" +
                "      ,[description]\n" +
                "      ,[solvedDate]\n" +
                "      ,[status]\n" +
                "      ,[createdBy]\n" +
                "      ,[homeId]\n" +
                "      ,[reportTypeId]\n" +
                "  FROM [dbo].[Reports]";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Report report = new Report();
                report.setId(resultSet.getInt("id"));
                Timestamp reportDateTimestamp = resultSet.getTimestamp("reportDate");
                if (reportDateTimestamp != null) {
                    report.setReportDate(reportDateTimestamp.toLocalDateTime());
                } else {
                    report.setReportDate(null);
                }
                report.setTitle(resultSet.getString("title"));
                report.setDescription(resultSet.getString("description"));
                Timestamp solvedDateTimestamp = resultSet.getTimestamp("solvedDate");
                if (solvedDateTimestamp != null) {
                    report.setSolvedDate(solvedDateTimestamp.toLocalDateTime());
                } else {
                    report.setSolvedDate(null);
                }
                report.setStatus(resultSet.getString("status"));
                report.setCreatedBy(resultSet.getInt("createdBy"));
                report.setHomeId(resultSet.getInt("homeId"));
                report.setReportTypeId(resultSet.getInt("reportTypeId"));
                reports.add(report);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error getting report from database", e);
            throw new GeneralException("Error getting report from database" + e.getMessage());
        } finally {
            try{
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new GeneralException("Error closing database resources: " + e.getMessage(), e);
            }
        }
        return reports;
    }

    /**
     * @param report
     */
    @Override
    public void saveReport(Report report) {
        String sql = "INSERT INTO [dbo].[Reports]\n" +
                "           ([reportDate]\n" +
                "           ,[title]\n" +
                "           ,[description]\n" +
                "           ,[solvedDate]\n" +
                "           ,[status]\n" +
                "           ,[createdBy]\n" +
                "           ,[homeId]\n" +
                "           ,[reportTypeId])\n" +
                "     VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection= DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            //add information
            preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(report.getReportDate()));
            preparedStatement.setString(2, report.getTitle());
            preparedStatement.setString(3, report.getDescription());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(report.getSolvedDate()));
            preparedStatement.setString(5, report.getStatus());
            preparedStatement.setInt(6, report.getCreatedBy());
            preparedStatement.setInt(7, report.getHomeId());
            preparedStatement.setInt(8, report.getReportTypeId());

            preparedStatement.executeUpdate();
//
//            if (affectedRows > 0) {
//                // Retrieve the generated Report ID
//                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        return generatedKeys.getInt(1);  // Return the generated report ID
//                    } else {
//                        throw new SQLException("Creating report failed, no ID obtained.");
//                    }
//                }
//            } else {
//                throw new SQLException("Creating report failed, no rows affected.");
//            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error while saving report: " + e.getMessage(), e);
        }
    }


}
