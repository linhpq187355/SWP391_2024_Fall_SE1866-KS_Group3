package com.homesharing.util;

import com.homesharing.dao.AppointmentDAO;
import com.homesharing.dao.impl.AppointmentDAOImpl;
import com.homesharing.model.Appointment;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateExpiredAppointmentsJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        LocalDateTime now = LocalDateTime.now();


        List<Appointment> expiredAppointments = appointmentDAO.findExpiredAppointments(now);


        for (Appointment appointment : expiredAppointments) {
            appointment.setStatus("expired");
            appointmentDAO.update(appointment);
        }
    }
}
