package com.homesharing.service.impl;

import com.homesharing.dao.ReportDAO;
import com.homesharing.dao.ReportTypeDAO;
import com.homesharing.dao.impl.ReportDAOImpl;
import com.homesharing.dao.impl.ReportTypeDAOImpl;
import com.homesharing.model.Report;
import com.homesharing.model.ReportType;
import com.homesharing.service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {
    private final ReportDAO reportDAO;
    private final ReportTypeDAO reportTypeDAO;


    public ReportServiceImpl(ReportDAO reportDAO, ReportTypeDAO reportTypeDAO) {
        this.reportDAO = new ReportDAOImpl();
        this.reportTypeDAO = new ReportTypeDAOImpl();
    }

    /**
     * @return
     */
    @Override
    public List<Report> getAllReports() {
        return this.reportDAO.getAllReports();
    }

    /**
     * @return
     */
    @Override
    public List<ReportType> getAllReportTypes() {
        return this.reportTypeDAO.getAllReportTypes();
    }

    /**
     * @param report
     */
    @Override
    public void addReport(Report report) {
        this.reportDAO.saveReport(report);
    }
}
