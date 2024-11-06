/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */

package com.homesharing.dao;

import com.homesharing.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for Appointment Data Access Object (DAO).
 * Provides methods to interact with appointment data in the system.
 */
public interface AppointmentDAO {

    /**
     * Inserts a new appointment into the database.
     *
     * @param appointment the Appointment object to be inserted
     * @return the ID of the newly created appointment
     */
    int insert(Appointment appointment);

    /**
     * Retrieves a list of appointments associated with a specific host.
     *
     * @param hostId the ID of the host whose appointments are to be retrieved
     * @return a list of Appointment objects for the specified host
     */
    List<Appointment> selectByHostId(int hostId);

    /**
     * Retrieves a list of appointments associated with a specific tenant.
     *
     * @param tenantId the ID of the tenant whose appointments are to be retrieved
     * @return a list of Appointment objects for the specified tenant
     */
    List<Appointment> selectByTenantId(int tenantId);

    /**
     * Cancels an existing appointment with a specified reason.
     *
     * @param appointmentId the ID of the appointment to be canceled
     * @param reason the reason for canceling the appointment
     * @return the number of rows affected by the cancellation
     */
    int cancelAppointment(int appointmentId, String reason);

    /**
     * Retrieves a specific appointment by its ID.
     *
     * @param appointmentId the ID of the appointment to be retrieved
     * @return the Appointment object associated with the specified ID
     */
    Appointment getAppointmentById(int appointmentId);

    /**
     * Updates an existing appointment in the database.
     *
     * @param appointment the Appointment object containing updated data
     * @return the number of rows affected by the update
     */
    int update(Appointment appointment);

    /**
     * Accepts an appointment, marking it as confirmed.
     *
     * @param appointmentId the ID of the appointment to be accepted
     * @return the number of rows affected by the acceptance
     */
    int acceptAppointment(int appointmentId);

    /**
     * Rejects an appointment with a specified reason.
     *
     * @param appointmentId the ID of the appointment to be rejected
     * @param reason the reason for rejecting the appointment
     * @return the number of rows affected by the rejection
     */
    int rejectAppointment(int appointmentId, String reason);

    /**
     * Finds expired appointments based on the current time.
     *
     * @param now the current date and time used to check for expired appointments
     * @return a list of expired Appointment objects
     */
    List<Appointment> findExpiredAppointments(LocalDateTime now);
}