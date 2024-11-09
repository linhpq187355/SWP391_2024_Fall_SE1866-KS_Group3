package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.AnnouncementDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Announcement;
import com.homesharing.model.AnnouncementType;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnnouncementDAOImpl implements AnnouncementDAO {
    private static final Logger logger = Logger.getLogger(ReportTypeDAOImpl.class.getName());

    /**
     * @return
     */
    @Override
    public List<AnnouncementType> allAnnouncementTypes() {
        String sql = "select * from AnnouncementType";
        List<AnnouncementType> list = new ArrayList<AnnouncementType>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                AnnouncementType announcementType = new AnnouncementType();
                announcementType.setId(resultSet.getInt("id"));
                announcementType.setTypeName(resultSet.getString("typeName"));
                announcementType.setStatus(resultSet.getString("status"));
                list.add(announcementType);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.log(Level.SEVERE, "Error getting announcement from database", e);
            throw new GeneralException("Error getting announcement from database" + e.getMessage());
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
        return list;
    }

    /**
     * @return
     */
    @Override
    public List<Announcement> allAnnouncements() {
        String sql = "select * from Announcements";
        List<Announcement> list = new ArrayList<Announcement>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Announcement announcement = new Announcement();
                announcement.setId(resultSet.getInt("id"));
                announcement.setTitle(resultSet.getString("title"));
                announcement.setContent(resultSet.getString("content"));
                Timestamp announcementDateTimestamp = resultSet.getTimestamp("createdDate");
                if (announcementDateTimestamp != null) {
                    announcement.setCreatedDate(announcementDateTimestamp.toLocalDateTime());
                } else {
                    announcement.setCreatedDate(null);
                }
                Timestamp modifiedDateTimestamp = resultSet.getTimestamp("modifiedDate");
                if (modifiedDateTimestamp != null) {
                    announcement.setModifiedDate(modifiedDateTimestamp.toLocalDateTime());
                } else {
                    announcement.setModifiedDate(null);
                }
                announcement.setAnnouncementTypeId(resultSet.getInt("announcementTypeId"));
                announcement.setStatus(resultSet.getString("status"));
                announcement.setCreatedBy(resultSet.getInt("createdBy"));

                list.add(announcement);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.log(Level.SEVERE, "Error getting announcement from database", e);
            throw new GeneralException("Error getting announcement from database" + e.getMessage());
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
        return list;
    }

    /**
     * @param announcement
     */
    @Override
    public void saveAnnouncement(Announcement announcement) {
        String sql =
                "INSERT INTO [dbo].[Announcements]\n" +
                        "           ([title]\n" +
                        "           ,[content]\n" +
                        "           ,[createdDate]\n" +
                        "           ,[modifiedDate]\n" +
                        "           ,[status]\n" +
                        "           ,[createdBy]\n" +
                        "           ,[announcementTypeId])\n" +
                        "     VALUES (?,?,?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            //add information
            preparedStatement.setString(1, announcement.getTitle());
            preparedStatement.setString(2, announcement.getContent());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(announcement.getCreatedDate()));
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(announcement.getModifiedDate()));
            preparedStatement.setString(5, announcement.getStatus());
            preparedStatement.setInt(6, announcement.getCreatedBy());
            preparedStatement.setInt(7, announcement.getAnnouncementTypeId());
            //execute sql
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GeneralException("Error while saving announcement: " + e.getMessage(), e);
        } finally {
            try {
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
    }

    public static void main(String[] args) {
        List<Announcement> list = new AnnouncementDAOImpl().allAnnouncements();
        List<AnnouncementType> list1 = new AnnouncementDAOImpl().allAnnouncementTypes();
        System.out.println(list1.size());
    }
}
