package com.covid.covidmonitor.Model;

public class RegionList {
    private String id;
    private String regionName;

    public RegionList(String id, String regionName){
        this.id = id;
        this.regionName = regionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
