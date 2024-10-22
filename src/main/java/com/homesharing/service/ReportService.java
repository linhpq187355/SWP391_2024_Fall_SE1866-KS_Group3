package com.homesharing.service;

import com.homesharing.model.Report;
import com.homesharing.model.ReportType;

import java.util.List;

public interface ReportService {
    List<Report> getAllReports();
    List<ReportType> getAllReportTypes();
    void addReport(Report report);
}
