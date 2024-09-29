package com.homesharing.model;

public class Amentity {
    private int id;
    private String name;
    private String icon;
    private String status;

    public Amentity() {
    }

    public Amentity(int id, String name, String icon, String status) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
