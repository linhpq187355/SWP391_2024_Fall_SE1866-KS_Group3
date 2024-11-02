package com.homesharing.dao;

import com.homesharing.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDAO {
    int insert(Appointment appointment);

    List<Appointment> selectByHostId(int hostId);

    List<Appointment> selectByTenantId(int tenantId);

    int cancelAppointment(int appointmentId, String reason);

    Appointment getAppointmentById(int appointmentId);

    int update(Appointment appointment);

    int acceptAppointment(int appointmentId);

    int rejectAppointment(int appointmentId, String reason);

    List<Appointment> findExpiredAppointments(LocalDateTime now);
}
