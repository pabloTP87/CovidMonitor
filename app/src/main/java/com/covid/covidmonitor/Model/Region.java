package com.covid.covidmonitor.Model;

import com.google.gson.annotations.SerializedName;

public class Region {
    private float area;
    private float lat;
    @SerializedName("long")
    private float longitud;
    private int population;
    private String region;

    public float getArea() {
        return area;
    }

    public float getLat() {
        return lat;
    }

    public float getLongitud() {
        return longitud;
    }

    public int getPopulation() {
        return population;
    }

    public String getRegion() {
        return region;
    }
}
