package com.covid.covidmonitor.Model;

public class RegionUpdate {

    private int confirmed;
    private float confirmed_per_100k;
    private int deaths;
    private float deaths_per_100k;

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public float getConfirmed_per_100k() {
        return confirmed_per_100k;
    }

    public float getDeaths_per_100k() {
        return deaths_per_100k;
    }
}
