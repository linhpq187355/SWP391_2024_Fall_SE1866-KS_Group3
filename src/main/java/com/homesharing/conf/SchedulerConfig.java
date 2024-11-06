/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */

package com.homesharing.conf;


import com.homesharing.util.UpdateExpiredAppointmentsJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SchedulerConfig is responsible for setting up and managing a Quartz Scheduler
 * to periodically execute the UpdateExpiredAppointmentsJob.
 */
public class SchedulerConfig {

    private static final Logger LOGGER = Logger.getLogger(SchedulerConfig.class.getName());

    // Scheduler instance to manage scheduled jobs
    private static Scheduler scheduler;

    /**
     * Starts the scheduler and schedules the UpdateExpiredAppointmentsJob to run
     * at regular intervals.
     *
     * @return the started Scheduler instance
     */
    public static Scheduler startScheduler() {
        try {
            // Initialize scheduler and schedule job only if scheduler is null
            if (scheduler == null) {
                // Define the job to run for checking expired appointments
                JobDetail job = JobBuilder.newJob(UpdateExpiredAppointmentsJob.class)
                        .withIdentity("expiredAppointmentJob", "group1")
                        .build();

                // Configure a trigger to start immediately and repeat every 30 minutes
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity("trigger1", "group1")
                        .startNow()
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMinutes(1)
                                .repeatForever())
                        .build();

                // Initialize and start the scheduler
                scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
                LOGGER.info("Scheduler started and job scheduled successfully.");
            }
        } catch (SchedulerException e) {
            // Log the exception with a message
            LOGGER.log(Level.SEVERE, "Error starting the scheduler", e);
        }
        return scheduler;
    }

    /**
     * Stops the scheduler and cancels all scheduled jobs.
     * Waits for all jobs to complete before shutdown.
     */
    public static void stopScheduler() {
        if (scheduler != null) {
            try {
                scheduler.shutdown(true); // True to wait for jobs to finish
                LOGGER.info("Scheduler has been shut down.");
            } catch (SchedulerException e) {
                // Log the exception with a message
                LOGGER.log(Level.SEVERE, "Error shutting down the scheduler", e);
            }
        } else {
            LOGGER.warning("Scheduler was not running when shutdown was requested.");
        }
    }
}
