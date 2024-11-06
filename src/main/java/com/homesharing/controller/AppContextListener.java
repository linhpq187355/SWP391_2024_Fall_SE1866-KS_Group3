/*
 * Copyright(C) 2024, Homesharing Inc.
 * Homesharing:
 *  Roommate Matching and Home Sharing Service
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-10-25      1.0              Pham Quang Linh     First Implement
 */

package com.homesharing.controller;

import com.homesharing.conf.SchedulerConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.quartz.Scheduler;

/**
 * AppContextListener is a servlet context listener that manages the lifecycle
 * of the application context. It initializes and shuts down the Quartz Scheduler
 * when the application context is created and destroyed, respectively.
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    // Scheduler instance to manage scheduled jobs
    private Scheduler scheduler;

    /**
     * Called when the servlet context is initialized. This method starts the
     * Quartz Scheduler and stores its instance.
     *
     * @param sce the ServletContextEvent containing the context information
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize and store the instance of the scheduler
        scheduler = SchedulerConfig.startScheduler();
    }

    /**
     * Called when the servlet context is destroyed. This method stops the
     * Quartz Scheduler when the application is shutting down.
     *
     * @param sce the ServletContextEvent containing the context information
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Stop the scheduler when the application stops
        SchedulerConfig.stopScheduler();
    }

}
