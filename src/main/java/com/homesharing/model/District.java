package com.homesharing.model;

public class District {
    private int id;
    private String name;
    private String status;
    private int provinceId;

    public District(int id, String name, String status, int provinceId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.provinceId = provinceId;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public int getProvinceId() { return provinceId; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStatus(String status) { this.status = status; }
    public void setProvinceId(int provinceId) { this.provinceId = provinceId; }
}
