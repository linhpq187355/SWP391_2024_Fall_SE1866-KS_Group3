package com.homesharing.model;

public class PageVisit {
    private String url;
    private String pageName;
    private int totalVisits;
    private int memberVisits;

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(int totalVisits) {
        this.totalVisits = totalVisits;
    }

    public int getMemberVisits() {
        return memberVisits;
    }

    public void setMemberVisits(int memberVisits) {
        this.memberVisits = memberVisits;
    }
}