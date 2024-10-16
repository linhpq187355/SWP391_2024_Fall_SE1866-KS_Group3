package com.homesharing.dao;

import com.homesharing.model.Report;

import java.util.List;

public interface ReportDAO {
    List<Report> getAllReports();
    void saveReport(Report report);
}
