package com.homesharing.controller;

import com.homesharing.conf.SchedulerConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.quartz.Scheduler;

@WebListener
public class AppContextListener implements ServletContextListener {

    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Khởi tạo và lưu trữ instance của scheduler
        scheduler = SchedulerConfig.startScheduler();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Dừng scheduler khi ứng dụng ngừng
        SchedulerConfig.stopScheduler();
    }

}
