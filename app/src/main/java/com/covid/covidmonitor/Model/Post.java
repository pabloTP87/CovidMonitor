package com.covid.covidmonitor.Model;

public class Post {
    private int confirmed;
    private int deaths;
    private String last_updated;
    private int new_daily_cases;
    private String region;
    private Object regionInfo;

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public int getNew_daily_cases() {
        return new_daily_cases;
    }

    public String getRegion() {
        return region;
    }

    public Object getRegionInfo() {
        return regionInfo;
    }
}
