package com.homesharing.model;

public class Ward {
    private int id;
    private String name;
    private String status;
    private int districtId;

    public Ward() {
    }
    public Ward(int id, String name, String status, int districtId) { this.id = id; this.name = name; this.status = status; this.districtId = districtId; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getDistrictId() { return districtId; }
    public void setDistrictId(int id) { this.districtId = id; }
}
