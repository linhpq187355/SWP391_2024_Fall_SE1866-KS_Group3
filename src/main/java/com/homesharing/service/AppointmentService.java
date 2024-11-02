package com.homesharing.service;

import com.homesharing.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    int insertAppointment(String day, String month, String year, String time, String tenantId, String hostId, String note, String homeId);

    boolean checkOverlapping(String day, String month, String year,String time, List<Appointment> hostAppointments,List<Appointment> tenantAppointments);

    List<Appointment> getAppointments(String hostId);

    List<Appointment> getAppointmentsByTenant(String tenantId);

    int cancelAppointment(String appointmentId, String reason);

    Appointment getAppointmentById(String appointmentId);

    int updateAppointment(String day, String month, String year, String time, String note,String status, String id);

    int acceptAppointment(String appointmentId);

    int rejectAppointment(String appointmentId, String reason);
}
