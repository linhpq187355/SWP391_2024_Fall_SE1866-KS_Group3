/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */
package com.homesharing.dao.impl;

import com.homesharing.conf.DBContext;
import com.homesharing.dao.AppointmentDAO;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the AppointmentDAO interface for database operations
 * related to appointments. It provides methods to insert a new appointment and
 * retrieve appointments by host or tenant ID.
 */
public class AppointmentDAOImpl implements AppointmentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentDAOImpl.class);

    /**
     * Inserts a new appointment record into the database.
     *
     * @param appointment the Appointment object to be inserted
     * @return the generated ID of the newly created appointment
     */
    @Override
    public int insert(Appointment appointment) {
        String sql = "INSERT INTO [dbo].[Appointments]\n" +
                "           ([startDate]\n" +
                "           ,[endDate]\n" +
                "           ,[status]\n" +
                "           ,[tenantId]\n" +
                "           ,[hostId]\n" +
                "           ,[note]\n" +
                "           ,[homeId])\n" +
                "     VALUES\n" +
                "           (?,?,?,?,?,?,?)";
        int affectedRows = 0;

        // Using try-with-resources to ensure automatic resource management
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, appointment.getStartDate());
            preparedStatement.setObject(2, appointment.getEndDate());
            preparedStatement.setString(3, appointment.getStatus());
            preparedStatement.setInt(4, appointment.getTenantId());
            preparedStatement.setInt(5, appointment.getHostId());
            preparedStatement.setString(6, appointment.getNote());
            preparedStatement.setInt(7, appointment.getHomeId());
            // Execute the update to insert the user into the database
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                // Retrieve the generated keys (ID)
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    } else {
                        throw new SQLException("Inserting appointment failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Inserting appointment failed, no rows affected.");
            }

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while inserting appointment: {}", e.getMessage());
            throw new RuntimeException("Error saving appointment to the database: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Error saving appointment to the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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

    /**
     * Retrieves a list of appointments by host ID.
     *
     * @param hostId the host ID for which appointments are retrieved
     * @return a list of appointments for the specified host
     */
    @Override
    public List<Appointment> selectByHostId(int hostId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "select *\n" +
                "from Appointments\n" +
                "where hostId = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, hostId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
                appointment.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
                appointment.setTenantId(resultSet.getInt("tenantId"));
                appointment.setHostId(resultSet.getInt("hostId"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setNote(resultSet.getString("note"));
                appointment.setHomeId(resultSet.getInt("homeId"));
                appointment.setRejectReason(resultSet.getString("RejectReason"));
                appointment.setCancelReason(resultSet.getString("cancelReason"));
                list.add(appointment);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new GeneralException("Error: ", e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
     * Retrieves a list of appointments by tenant ID.
     *
     * @param tenantId the tenant ID for which appointments are retrieved
     * @return a list of appointments for the specified tenant
     */
    @Override
    public List<Appointment> selectByTenantId(int tenantId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "select *\n" +
                "from Appointments\n" +
                "where tenantId = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tenantId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
                appointment.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
                appointment.setTenantId(resultSet.getInt("tenantId"));
                appointment.setHostId(resultSet.getInt("hostId"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setNote(resultSet.getString("note"));
                appointment.setHomeId(resultSet.getInt("homeId"));
                appointment.setRejectReason(resultSet.getString("RejectReason"));
                appointment.setCancelReason(resultSet.getString("cancelReason"));
                list.add(appointment);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new GeneralException("Error: ", e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
     * Cancels an appointment by updating its status to "cancelled" and setting a cancellation reason.
     *
     * @param appointmentId The ID of the appointment to be cancelled.
     * @param reason        The reason for cancelling the appointment.
     * @return              The number of rows affected in the database.
     * @throws RuntimeException If a SQL error or unexpected error occurs.
     */
    @Override
    public int cancelAppointment(int appointmentId, String reason) {
        String sql = "update Appointments\n" +
                "set status = 'cancelled', cancelReason = ?\n" +
                "where id = ?";
        int affectedRows = 0;

        // Using try-with-resources to ensure automatic resource management
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reason);
            preparedStatement.setObject(2, appointmentId);
            // Execute the update to insert the user into the database
            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while inserting appointment: {}", e.getMessage());
            throw new RuntimeException("Error saving appointment to the database: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Error saving appointment to the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
        return affectedRows;
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to retrieve.
     * @return              The appointment object containing all details, or an empty appointment if not found.
     * @throws GeneralException If an error occurs during retrieval or closing resources.
     */
    @Override
    public Appointment getAppointmentById(int appointmentId) {
        String sql = "select * from Appointments where id = ?";
        Appointment appointment = new Appointment();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, appointmentId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                appointment.setId(resultSet.getInt("id"));
                appointment.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
                appointment.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
                appointment.setTenantId(resultSet.getInt("tenantId"));
                appointment.setHostId(resultSet.getInt("hostId"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setNote(resultSet.getString("note"));
                appointment.setHomeId(resultSet.getInt("homeId"));
                appointment.setRejectReason(resultSet.getString("RejectReason"));
                appointment.setCancelReason(resultSet.getString("cancelReason"));
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new GeneralException("Error: ", e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
        return appointment;
    }

    /**
     * Updates an existing appointment in the database.
     *
     * @param appointment The appointment object containing updated details.
     * @return            The number of rows affected in the database.
     * @throws RuntimeException If a SQL error or unexpected error occurs.
     */
    @Override
    public int update(Appointment appointment) {
        String sql = "UPDATE [dbo].[Appointments]\n" +
                "   SET [startDate] = ?\n" +
                "      ,[endDate] = ?\n" +
                "      ,[status] = ?\n" +
                "      ,[note] = ?\n" +
                " WHERE id = ?";
        int affectedRows = 0;


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, appointment.getStartDate());
            preparedStatement.setObject(2, appointment.getEndDate());
            preparedStatement.setString(3, appointment.getStatus());
            preparedStatement.setString(4, appointment.getNote());
            preparedStatement.setInt(5, appointment.getId());
            // Execute the update to insert the user into the database
            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while update appointment: {}", e.getMessage());
            throw new RuntimeException("Error update appointment to the database: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Error update appointment to the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
        return affectedRows;
    }

    /**
     * Accepts an appointment by updating its status to 'accepted'.
     *
     * @param appointmentId the ID of the appointment to be accepted
     * @return the number of rows affected by the update
     * @throws RuntimeException if an error occurs while updating the appointment in the database
     */
    @Override
    public int acceptAppointment(int appointmentId) {
        String sql = "UPDATE [dbo].[Appointments]\n" +
                "   SET [status] = ?\n" +
                " WHERE id = ?";
        int affectedRows = 0;

        // Using try-with-resources to ensure automatic resource management
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "accepted");
            preparedStatement.setObject(2, appointmentId);
            // Execute the update to insert the user into the database
            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while update appointment: {}", e.getMessage());
            throw new RuntimeException("Error update appointment to the database: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Error update appointment to the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
        return affectedRows;
    }

    /**
     * Rejects an appointment by updating its status to 'rejected' and setting a reject reason.
     *
     * @param appointmentId the ID of the appointment to be rejected
     * @param reason the reason for rejecting the appointment
     * @return the number of rows affected by the update
     * @throws RuntimeException if an error occurs while updating the appointment in the database
     */
    @Override
    public int rejectAppointment(int appointmentId, String reason) {
        String sql = "UPDATE [dbo].[Appointments]\n" +
                "   SET [status] = ?\n" +
                "      ,[RejectReason] = ?\n" +
                " WHERE id = ?";
        int affectedRows = 0;

        // Using try-with-resources to ensure automatic resource management
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "rejected");
            preparedStatement.setString(2, reason);
            preparedStatement.setObject(3, appointmentId);
            // Execute the update to insert the user into the database
            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("SQL error occurred while update appointment: {}", e.getMessage());
            throw new RuntimeException("Error update appointment to the database: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Error update appointment to the database: " + e.getMessage(), e);
        } finally {
            // Closing resources in reverse order of opening
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
        return affectedRows;
    }

    @Override
    public int countAppointments() {
        String sql = "select count(id) total\n" +
                "from Appointments";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // If a result is found, return the total number
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error retrieving total user from the database", e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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

        return 0;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "select *\n" +
                "from Appointments\n";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
                appointment.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
                appointment.setTenantId(resultSet.getInt("tenantId"));
                appointment.setHostId(resultSet.getInt("hostId"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setNote(resultSet.getString("note"));
                appointment.setHomeId(resultSet.getInt("homeId"));
                appointment.setRejectReason(resultSet.getString("RejectReason"));
                appointment.setCancelReason(resultSet.getString("cancelReason"));
                list.add(appointment);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new GeneralException("Error: ", e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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
     * Finds appointments that have expired but have not been marked as 'expired'.
     *
     * @param now the current date and time used to identify expired appointments
     * @return a list of expired appointments
     * @throws GeneralException if an error occurs while retrieving appointments from the database
     */
    @Override
    public List<Appointment> findExpiredAppointments(LocalDateTime now) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE endDate < ? AND status <> 'expired' AND status like 'accepted'";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBContext.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(now));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("id"));
                appointment.setStartDate(resultSet.getTimestamp("startDate").toLocalDateTime());
                appointment.setEndDate(resultSet.getTimestamp("endDate").toLocalDateTime());
                appointment.setTenantId(resultSet.getInt("tenantId"));
                appointment.setHostId(resultSet.getInt("hostId"));
                appointment.setStatus(resultSet.getString("status"));
                appointment.setNote(resultSet.getString("note"));
                appointment.setHomeId(resultSet.getInt("homeId"));
                appointment.setRejectReason(resultSet.getString("RejectReason"));
                appointment.setCancelReason(resultSet.getString("cancelReason"));
                list.add(appointment);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new GeneralException("Error: ", e);
        } finally {
            // Closing resources in reverse order of opening
            try {
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



}
