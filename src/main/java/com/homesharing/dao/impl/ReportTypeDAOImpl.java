package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.ReportTypeDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.ReportType;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportTypeDAOImpl implements ReportTypeDAO {
    private static final Logger logger = Logger.getLogger(ReportTypeDAOImpl.class.getName());
    /**
     * @return
     */
    @Override
    public List<ReportType> getAllReportTypes() {
        List<ReportType> reportTypes = new ArrayList<ReportType>();
        String sql = "SELECT [id]\n" +
                "      ,[reason]\n" +
                "      ,[icon]\n" +
                "  FROM [dbo].[ReportTypes]";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReportType reportType = new ReportType();
                reportType.setId(resultSet.getInt("id"));
                reportType.setReason(resultSet.getString("reason"));
                reportType.setIcon(resultSet.getString("icon"));
                reportTypes.add(reportType);
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error getting report type from database", e);
            throw new GeneralException("Error getting report type from database" + e.getMessage());
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
        return reportTypes;
    }
}
