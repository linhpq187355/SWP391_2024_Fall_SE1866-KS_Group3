/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */
package com.homesharing.service.impl;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.NotificationDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.dao.impl.NotificationDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Appointment;
import com.homesharing.service.AppointmentService;
import com.homesharing.util.AddNotificationUtil;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The AppointmentServiceImpl class provides implementation of AppointmentService interface
 * for managing appointments between hosts and tenants, including functionalities such as
 * inserting, checking for overlapping appointments, and retrieving appointments by host or tenant.
 */
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentDAO appointmentDAO;
    private static final Logger LOGGER = Logger.getLogger(AppointmentServiceImpl.class.getName());

    /**
     * Constructs an AppointmentServiceImpl with a specified AppointmentDAO.
     *
     * @param appointmentDAO the data access object for appointment data
     */
    public AppointmentServiceImpl(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }


    /**
     * Inserts a new appointment with the specified parameters.
     *
     * @param day         the day of the appointment
     * @param month       the month of the appointment
     * @param year        the year of the appointment
     * @param time        the time range for the appointment in "HH:mm - HH:mm" format
     * @param tenantId    the ID of the tenant
     * @param hostId      the ID of the host
     * @param note        additional notes for the appointment
     * @param homeId      the ID of the home
     * @return            the ID of the newly created appointment
     * @throws IllegalArgumentException if any date/time format is invalid
     * @throws RuntimeException         if an error occurs during database interaction
     */
    @Override
    public int insertAppointment(String day, String month, String year, String time, String tenantId, String hostId, String note, String homeId) {
        String[] times;
        LocalTime localStartTime = null;
        LocalTime localEndTime = null;

        try {
            times = time.split(" - ");

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            if (times[0].length() == 4) {
                times[0] = "0".concat(times[0]);
            }
            if (times[1].length() == 4) {
                times[1] = "0".concat(times[1]);
            }

            localStartTime = LocalTime.parse(times[0], timeFormatter);
            localEndTime = LocalTime.parse(times[1], timeFormatter);
        } catch (DateTimeParseException e) {
            LOGGER.log(Level.SEVERE,"Invalid time format: {}", e.getMessage());
            throw new IllegalArgumentException("Time format is invalid: " + e.getMessage(), e);
        }

        Appointment appointment = new Appointment();
        try {
            appointment.setStartDate(LocalDateTime.of(
                    Integer.parseInt(year),
                    Integer.parseInt(month)+1,
                    Integer.parseInt(day),
                    localStartTime.getHour(),
                    localStartTime.getMinute()));

            appointment.setEndDate(LocalDateTime.of(
                    Integer.parseInt(year),
                    Integer.parseInt(month)+1,
                    Integer.parseInt(day),
                    localEndTime.getHour(),
                    localEndTime.getMinute()));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE,"Invalid date format for day, month, or year: {}", e);
            throw new IllegalArgumentException("Date format is invalid: ", e);
        } catch (DateTimeException e) {
            LOGGER.log(Level.SEVERE,"Invalid date or time: ", e);
            throw new IllegalArgumentException("Date or time is invalid: ", e);
        }



        try {
            appointment.setTenantId(Integer.parseInt(tenantId));
            appointment.setHostId(Integer.parseInt(hostId));
            appointment.setHomeId(Integer.parseInt(homeId));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE,"Invalid tenantId or hostId format: {}", e.getMessage());
            throw new IllegalArgumentException("tenantId or hostId format is invalid: " + e.getMessage(), e);
        }

        appointment.setNote(note);
        appointment.setStatus("hostPending");

        try {
            int newId = appointmentDAO.insert(appointment);
            if(newId>0){
                AddNotificationUtil.getInstance().addNotification(appointment.getHostId(),"Bạn có lịch hẹn xem phòng mới vào: "+time+" | "+day+"/"+month+"/"+year+"!", "Bạn có lịch hẹn xem phòng mới!","Appointment", "appointment-host-manage?appointmentId="+newId);
            }
            return newId;
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE,"Error inserting appointment into the database: {}", e.getMessage());
            throw new RuntimeException("Error saving appointment to the database: " + e.getMessage(), e);
        } catch (SQLException s) {
            LOGGER.log(Level.SEVERE,"Error add notification: {}", s.getMessage());
            throw new RuntimeException("Error add notification: " + s.getMessage(), s);
        }
    }

    /**
     * Checks if an appointment overlaps with existing appointments for both host and tenant.
     *
     * @param day                the day of the appointment
     * @param month              the month of the appointment
     * @param year               the year of the appointment
     * @param time               the time range for the appointment in "HH:mm - HH:mm" format
     * @param hostAppointments   list of existing appointments for the host
     * @param tenantAppointments list of existing appointments for the tenant
     * @return                   true if overlapping, false otherwise
     */
    @Override
    public boolean checkOverlapping(String day, String month, String year,String time, List<Appointment> hostAppointments, List<Appointment> tenantAppointments) {
        if (tenantAppointments == null || tenantAppointments.isEmpty()) {
            return false;
        }

        if (hostAppointments == null || hostAppointments.isEmpty()) {
            return false;
        }

        String[] times;
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        try{
            times = time.split(" - ");

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            if (times[0].length() == 4) {
                times[0] = "0".concat(times[0]);
            }
            if (times[1].length() == 4) {
                times[1] = "0".concat(times[1]);
            }

            LocalTime localStartTime = LocalTime.parse(times[0], timeFormatter);
            LocalTime localEndTime = LocalTime.parse(times[1], timeFormatter);

            startDate = LocalDateTime.of(Integer.parseInt(year),
                    Integer.parseInt(month)+1,
                    Integer.parseInt(day),
                    localStartTime.getHour(),
                    localStartTime.getMinute());
            endDate = LocalDateTime.of(
                    Integer.parseInt(year),
                    Integer.parseInt(month)+1,
                    Integer.parseInt(day),
                    localEndTime.getHour(),
                    localEndTime.getMinute());

            for(int i=0; i<hostAppointments.size(); i++){
                LocalDateTime existingStart = hostAppointments.get(i).getStartDate();
                LocalDateTime existingEnd = hostAppointments.get(i).getEndDate();

                if (startDate.isBefore(existingEnd) && endDate.isAfter(existingStart) && hostAppointments.get(i).getStatus().equals("accepted")) {
                    return true;
                }
            }

            for(int i=0; i<tenantAppointments.size(); i++){
                LocalDateTime existingStart = tenantAppointments.get(i).getStartDate();
                LocalDateTime existingEnd = tenantAppointments.get(i).getEndDate();

                if (startDate.isBefore(existingEnd) && endDate.isAfter(existingStart) && tenantAppointments.get(i).getStatus().equals("accepted")) {
                    return true;
                }
            }
        } catch(GeneralException e){
            LOGGER.log(Level.SEVERE,"Error check overlapping", e.getMessage());
            throw new GeneralException("Error check overlapping" + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Retrieves a list of appointments by host ID.
     *
     * @param hostId the ID of the host
     * @return       list of appointments associated with the host
     */
    @Override
    public List<Appointment> getAppointments(String hostId) {
        try{
            int hostID = Integer.parseInt(hostId);
            return appointmentDAO.selectByHostId(hostID);

        } catch(GeneralException e){
            LOGGER.log(Level.SEVERE,"Error get appointments by hostId", e.getMessage());
            throw new GeneralException("Error get appointments by hostId" + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a list of appointments by tenant ID.
     *
     * @param tenantId the ID of the tenant
     * @return         list of appointments associated with the tenant
     */
    @Override
    public List<Appointment> getAppointmentsByTenant(String tenantId) {
        try{
            int tenantID = Integer.parseInt(tenantId);
            return appointmentDAO.selectByTenantId(tenantID);

        } catch(GeneralException e){
            LOGGER.log(Level.SEVERE,"Error get appointments by hostId", e.getMessage());
            throw new GeneralException("Error get appointments by hostId" + e.getMessage(), e);
        }
    }

    /**
     * Cancels an appointment and sends a notification to the host or tenant about the cancellation.
     *
     * @param appointmentId The ID of the appointment to cancel.
     * @param reason        The reason for the cancellation.
     * @param receiver      Specifies if the notification is sent to the host or tenant.
     * @return The number of affected rows in the database.
     * @throws GeneralException If an error occurs while cancelling the appointment.
     * @throws RuntimeException If an error occurs while adding the notification.
     */
    @Override
    public int cancelAppointment(String appointmentId, String reason, String receiver) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            Appointment appointment = getAppointmentById(appointmentId);
            int effectedRow = appointmentDAO.cancelAppointment(appointmentID, reason);
            if(effectedRow>0){
                if(receiver.equals("host")){
                    AddNotificationUtil.getInstance().addNotification(appointment.getHostId(),"Lịch hẹn ngày "+appointment.getStartDate().getDayOfMonth()+"/"+appointment.getStartDate().getMonthValue()+"/"+appointment.getStartDate().getYear()+" đã bị hủy với lý do: "+reason, "Lịch hẹn đã bị hủy!","Appointment", "appointment-host-manage?appointmentId="+appointmentId);
                } else {
                    AddNotificationUtil.getInstance().addNotification(appointment.getTenantId(),"Lịch hẹn ngày "+appointment.getStartDate().getDayOfMonth()+"/"+appointment.getStartDate().getMonthValue()+"/"+appointment.getStartDate().getYear()+" đã bị hủy với lý do: "+reason, "Lịch hẹn đã bị hủy!","Appointment", "appointment-tenant-list?appointmentId="+appointmentId);
                }

            }
            return effectedRow;
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error cancel appointments", e.getMessage());
            throw new GeneralException("Error cancel appointments" + e.getMessage(), e);
        } catch (SQLException s) {
            LOGGER.log(Level.SEVERE,"Error add notification: {}", s.getMessage());
            throw new RuntimeException("Error add notification: " + s.getMessage(), s);
        }
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment to retrieve.
     * @return The Appointment object associated with the given ID.
     * @throws GeneralException If an error occurs while retrieving the appointment.
     */
    @Override
    public Appointment getAppointmentById(String appointmentId) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            return appointmentDAO.getAppointmentById(appointmentID);
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error get appointments", e.getMessage());
            throw new GeneralException("Error get appointments" + e.getMessage(), e);
        }
    }

    /**
     * Updates the details of an existing appointment, such as date, time, and status,
     * and notifies the tenant if the appointment is updated by the host.
     *
     * @param day    The day of the appointment.
     * @param month  The month of the appointment.
     * @param year   The year of the appointment.
     * @param time   The time of the appointment in "HH:mm - HH:mm" format.
     * @param note   A note or message associated with the appointment.
     * @param status The status of the appointment.
     * @param id     The ID of the appointment.
     * @param host   Indicates if the host is updating the appointment.
     * @return The number of affected rows in the database.
     * @throws IllegalArgumentException If the date or time format is invalid.
     * @throws RuntimeException         If an error occurs while updating the appointment or adding the notification.
     */
    @Override
    public int updateAppointment(String day, String month, String year, String time, String note,String status,String id, String host) {
        String[] times;
        LocalTime localStartTime = null;
        LocalTime localEndTime = null;

        try {
            times = time.split(" - ");

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            if (times[0].length() == 4) {
                times[0] = "0".concat(times[0]);
            }
            if (times[1].length() == 4) {
                times[1] = "0".concat(times[1]);
            }

            localStartTime = LocalTime.parse(times[0], timeFormatter);
            localEndTime = LocalTime.parse(times[1], timeFormatter);
        } catch (DateTimeParseException e) {
            LOGGER.log(Level.SEVERE,"Invalid time format: {}", e.getMessage());
            throw new IllegalArgumentException("Time format is invalid: " + e.getMessage(), e);
        }

        Appointment appointment = new Appointment();
        try {
            appointment.setStartDate(LocalDateTime.of(
                    Integer.parseInt(year),
                    Integer.parseInt(month)+1,
                    Integer.parseInt(day),
                    localStartTime.getHour(),
                    localStartTime.getMinute()));

            appointment.setEndDate(LocalDateTime.of(
                    Integer.parseInt(year),
                    Integer.parseInt(month)+1,
                    Integer.parseInt(day),
                    localEndTime.getHour(),
                    localEndTime.getMinute()));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE,"Invalid date format for day, month, or year: {}", e);
            throw new IllegalArgumentException("Date format is invalid: ", e);
        } catch (DateTimeException e) {
            LOGGER.log(Level.SEVERE,"Invalid date or time: ", e);
            throw new IllegalArgumentException("Date or time is invalid: ", e);
        }

        try {
            appointment.setId(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE,"Invalid id format: {}", e.getMessage());
            throw new IllegalArgumentException("id format is invalid: " + e.getMessage(), e);
        }

        appointment.setNote(note);
        appointment.setStatus(status);

        try {
            Appointment appointment1 = getAppointmentById(id);
            int affectedRow = appointmentDAO.update(appointment);
            if(affectedRow>0){
                if(host.equals("1")) {
                    AddNotificationUtil.getInstance().addNotification(appointment1.getTenantId(),"Lịch hẹn ngày "+appointment1.getStartDate().getDayOfMonth()+"/"+appointment1.getStartDate().getMonthValue()+"/"+appointment1.getStartDate().getYear()+" đã được đổi sang "+ time+" | "+day+"/"+month+"/"+year+" bởi chủ phòng, vui lòng xem và xác nhận.", "Lịch hẹn đã được thay đổi!","Appointment", "appointment-tenant-list?appointmentId="+id);
                }
            }
            return affectedRow;
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE,"Error updating appointment into the database: {}", e.getMessage());
            throw new RuntimeException("Error updating appointment to the database: " + e.getMessage(), e);
        } catch (SQLException s) {
            LOGGER.log(Level.SEVERE,"Error add notification: {}", s.getMessage());
            throw new RuntimeException("Error add notification: " + s.getMessage(), s);
        }
    }

    /**
     * Accepts an appointment and sends a notification to the host or tenant about the acceptance.
     *
     * @param appointmentId The ID of the appointment to accept.
     * @param receiver      Specifies if the notification is sent to the host or tenant.
     * @return The number of affected rows in the database.
     * @throws GeneralException If an error occurs while accepting the appointment.
     * @throws RuntimeException If an error occurs while adding the notification.
     */
    @Override
    public int acceptAppointment(String appointmentId, String receiver) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            Appointment appointment = getAppointmentById(appointmentId);
            int effectedRow = appointmentDAO.acceptAppointment(appointmentID);
            if(effectedRow >0){
                if(receiver.equals("host")){
                    AddNotificationUtil.getInstance().addNotification(appointment.getHostId(),"Yêu cầu lịch hẹn ngày "+appointment.getStartDate().getDayOfMonth()+"/"+appointment.getStartDate().getMonthValue()+"/"+appointment.getStartDate().getYear()+" của bạn đã được chấp nhận!", "Lịch hẹn được chấp nhận.","Appointment", "appointment-host-manage?appointmentId="+appointmentId);
                } else {
                    AddNotificationUtil.getInstance().addNotification(appointment.getTenantId(),"Yêu cầu lịch hẹn ngày "+appointment.getStartDate().getDayOfMonth()+"/"+appointment.getStartDate().getMonthValue()+"/"+appointment.getStartDate().getYear()+" của bạn đã được chấp nhận!","Lịch hẹn được chấp nhận.", "Appointment", "appointment-tenant-list?appointmentId="+appointmentId);
                }

            }
            return effectedRow;
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error accept appointments", e.getMessage());
            throw new GeneralException("Error accept appointments" + e.getMessage(), e);
        } catch (SQLException s) {
            LOGGER.log(Level.SEVERE,"Error add notification: {}", s.getMessage());
            throw new RuntimeException("Error add notification: " + s.getMessage(), s);
        }
    }

    /**
     * Rejects an appointment with a given reason and notifies the tenant of the rejection.
     *
     * @param appointmentId The ID of the appointment to reject.
     * @param reason        The reason for the rejection.
     * @return The number of affected rows in the database.
     * @throws GeneralException If an error occurs while rejecting the appointment.
     * @throws RuntimeException If an error occurs while adding the notification.
     */
    @Override
    public int rejectAppointment(String appointmentId, String reason) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            Appointment appointment = getAppointmentById(appointmentId);
            int effectedRow = appointmentDAO.rejectAppointment(appointmentID, reason);
            if(effectedRow >0){
                AddNotificationUtil.getInstance().addNotification(appointment.getTenantId(),"Yêu cầu lịch hẹn ngày "+appointment.getStartDate().getDayOfMonth()+"/"+appointment.getStartDate().getMonthValue()+"/"+appointment.getStartDate().getYear()+" của bạn đã bị từ chối với lí do: "+reason+"!", "Lịch hẹn bị từ chối.","Appointment", "appointment-tenant-list?appointmentId="+appointmentId);
            }
            return effectedRow;
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error reject appointments", e.getMessage());
            throw new GeneralException("Error reject appointments" + e.getMessage(), e);
        } catch (SQLException s) {
            LOGGER.log(Level.SEVERE,"Error add notification: {}", s.getMessage());
            throw new RuntimeException("Error add notification: " + s.getMessage(), s);
        }
    }

}

