package com.homesharing.model;

import java.time.LocalDateTime;

public class Report {
    private int id;
    private String title;
    private String description;
    private LocalDateTime reportDate;
    private LocalDateTime solvedDate;
    private String status;
    private int createdBy;
    private int homeId;
    private int reportTypeId;

    public Report() {
    }

    public Report(int id, String title, String description, LocalDateTime reportedDate, LocalDateTime solvedDate, String status, int createdBy, int homeId, int reportTypeId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.reportDate = reportedDate;
        this.solvedDate = solvedDate;
        this.status = status;
        this.createdBy = createdBy;
        this.homeId = homeId;
        this.reportTypeId = reportTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public LocalDateTime getSolvedDate() {
        return solvedDate;
    }

    public void setSolvedDate(LocalDateTime solvedDate) {
        this.solvedDate = solvedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        this.reportTypeId = reportTypeId;
    }
}
