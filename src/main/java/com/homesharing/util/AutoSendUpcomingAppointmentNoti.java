package com.homesharing.util;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.model.Appointment;
import com.homesharing.service.impl.AppointmentServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoSendUpcomingAppointmentNoti implements Job {
    private static final Logger LOGGER = Logger.getLogger(AutoSendUpcomingAppointmentNoti.class.getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        LocalDate today = LocalDate.now();

        List<Appointment> appointments = appointmentDAO.getAllAppointments();


        for (Appointment appointment : appointments) {
            LocalDate startDate = appointment.getStartDate().toLocalDate();
            long daysBetween = ChronoUnit.DAYS.between(today, startDate);

            if (daysBetween >= 0 && daysBetween <= 1 && appointment.getStatus().equals("accepted")) {
                try{
                    AddNotificationUtil.getInstance().addNotification(appointment.getHostId(),"Lịch hẹn ngày "+appointment.getStartDate().getDayOfMonth()+"/"+appointment.getStartDate().getMonthValue()+"/"+appointment.getStartDate().getYear()+" sắp đến, chú ý lịch để tham gia nhé!", "Lịch hẹn sắp đến!","Appointment", "appointment-host-manage?appointmentId="+appointment.getId());
                    AddNotificationUtil.getInstance().addNotification(appointment.getTenantId(),"Lịch hẹn ngày "+appointment.getStartDate().getDayOfMonth()+"/"+appointment.getStartDate().getMonthValue()+"/"+appointment.getStartDate().getYear()+" sắp đến, chú ý lịch để tham gia nhé!", "Lịch hẹn sắp đến!","Appointment", "appointment-tenant-list?appointmentId="+appointment.getId());
                } catch (SQLException s) {
                    LOGGER.log(Level.SEVERE,"Error add notification: {}", s.getMessage());
                    throw new RuntimeException("Error add notification: " + s.getMessage(), s);
                }
            }
        }
    }
}
