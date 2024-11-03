package com.homesharing.conf;

import com.homesharing.util.UpdateExpiredAppointmentsJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


public class SchedulerConfig {
    private static Scheduler scheduler;

    public static Scheduler startScheduler() {
        try {
            if (scheduler == null) {
                JobDetail job = JobBuilder.newJob(UpdateExpiredAppointmentsJob.class)
                        .withIdentity("expiredAppointmentJob", "group1")
                        .build();

                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity("trigger1", "group1")
                        .startNow()
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMinutes(1) // Kiểm tra mỗi 30 phút
                                .repeatForever())
                        .build();

                scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }

    public static void stopScheduler() {
        if (scheduler != null) {
            try {
                scheduler.shutdown(true);
                System.out.println("Scheduler has been shut down.");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
}
