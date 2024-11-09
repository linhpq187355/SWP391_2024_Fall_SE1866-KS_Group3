package com.homesharing.model;

public class ProvinceHomeStat {
    private String provinceName;
    private int homeCount;
    private double homeRatio;

    // Constructor
    public ProvinceHomeStat(String provinceName, int homeCount, double homeRatio) {
        this.provinceName = provinceName;
        this.homeCount = homeCount;
        this.homeRatio = homeRatio;
    }

    // Getters
    public String getProvinceName() {
        return provinceName;
    }

    public int getHomeCount() {
        return homeCount;
    }

    public double getHomeRatio() {
        return homeRatio;
    }
}
