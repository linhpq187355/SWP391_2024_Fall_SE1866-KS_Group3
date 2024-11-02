package com.homesharing.service.impl;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.exception.GeneralException;
import com.homesharing.model.Appointment;
import com.homesharing.service.AppointmentService;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentDAO appointmentDAO;
    private static final Logger LOGGER = Logger.getLogger(AppointmentServiceImpl.class.getName());

    public AppointmentServiceImpl(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

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
            return appointmentDAO.insert(appointment);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE,"Error inserting appointment into the database: {}", e.getMessage());
            throw new RuntimeException("Error saving appointment to the database: " + e.getMessage(), e);
        }
    }

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

    @Override
    public int cancelAppointment(String appointmentId, String reason) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            return appointmentDAO.cancelAppointment(appointmentID, reason);
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error cancel appointments", e.getMessage());
            throw new GeneralException("Error cancel appointments" + e.getMessage(), e);
        }
    }

    @Override
    public Appointment getAppointmentById(String appointmentId) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            return appointmentDAO.getAppointmentById(appointmentID);
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error cancel appointments", e.getMessage());
            throw new GeneralException("Error cancel appointments" + e.getMessage(), e);
        }
    }

    @Override
    public int updateAppointment(String day, String month, String year, String time, String note,String status,String id) {
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
            return appointmentDAO.update(appointment);
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE,"Error updating appointment into the database: {}", e.getMessage());
            throw new RuntimeException("Error updating appointment to the database: " + e.getMessage(), e);
        }
    }

    @Override
    public int acceptAppointment(String appointmentId) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            return appointmentDAO.acceptAppointment(appointmentID);
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error accept appointments", e.getMessage());
            throw new GeneralException("Error accept appointments" + e.getMessage(), e);
        }
    }

    @Override
    public int rejectAppointment(String appointmentId, String reason) {
        try{
            int appointmentID = Integer.parseInt(appointmentId);
            return appointmentDAO.rejectAppointment(appointmentID, reason);
        } catch (GeneralException e){
            LOGGER.log(Level.SEVERE,"Error reject appointments", e.getMessage());
            throw new GeneralException("Error reject appointments" + e.getMessage(), e);
        }
    }

}

