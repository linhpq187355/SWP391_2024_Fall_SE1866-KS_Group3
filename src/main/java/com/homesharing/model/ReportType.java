package com.homesharing.model;

public class ReportType {
    private int id;
    private String reason;
    private String icon;

    public ReportType() {
    }
    public ReportType(int id, String reason, String icon) {
        this.id = id;
        this.reason = reason;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
