/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */
package com.homesharing.service;

import com.homesharing.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for handling operations related to Appointments.
 * Provides methods to insert, check, retrieve, update, and cancel appointments.
 */
public interface AppointmentService {

    /**
     * Inserts a new appointment.
     *
     * @param day        The day of the appointment.
     * @param month      The month of the appointment.
     * @param year       The year of the appointment.
     * @param time       The time of the appointment.
     * @param tenantId   The ID of the tenant.
     * @param hostId     The ID of the host.
     * @param note       Any additional notes for the appointment.
     * @param homeId     The ID of the home for the appointment.
     * @return The ID of the inserted appointment.
     */
    int insertAppointment(String day, String month, String year, String time, String tenantId, String hostId, String note, String homeId);

    /**
     * Retrieves all appointments for a given host.
     *
     * @param hostId The ID of the host.
     * @return The list of appointments for the host.
     */
    List<Appointment> getAppointments(String hostId);

    /**
     * Retrieves all appointments for a given tenant.
     *
     * @param tenantId The ID of the tenant.
     * @return The list of appointments for the tenant.
     */
    List<Appointment> getAppointmentsByTenant(String tenantId);

    /**
     * Cancels an appointment.
     *
     * @param appointmentId The ID of the appointment to cancel.
     * @param reason        The reason for cancellation.
     * @param receiver      The receiver of the cancellation notification.
     * @return The result of the cancellation operation.
     */
    int cancelAppointment(String appointmentId, String reason, String receiver);

    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to retrieve.
     * @return The appointment with the specified ID, or null if not found.
     */
    Appointment getAppointmentById(String appointmentId);

    /**
     * Updates an existing appointment.
     *
     * @param day      The day of the updated appointment.
     * @param month    The month of the updated appointment.
     * @param year     The year of the updated appointment.
     * @param time     The time of the updated appointment.
     * @param note     The updated note for the appointment.
     * @param status   The new status of the appointment.
     * @param id       The ID of the appointment to update.
     * @param host     The ID of the host for the appointment.
     * @return The result of the update operation.
     */
    int updateAppointment(String day, String month, String year, String time, String note,String status, String id, String host,String explain);

    /**
     * Accepts an appointment.
     *
     * @param appointmentId The ID of the appointment to accept.
     * @param receiver      The receiver of the acceptance notification.
     * @return The result of the acceptance operation.
     */
    int acceptAppointment(String appointmentId, String receiver);

    int countAppointments();

    /**
     * Rejects an appointment.
     *
     * @param appointmentId The ID of the appointment to reject.
     * @param reason        The reason for rejection.
     * @return The result of the rejection operation.
     */
    int rejectAppointment(String appointmentId, String reason);
}
