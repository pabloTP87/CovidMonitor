package com.covid.covidmonitor.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class LastDataRegion {
    private String region;
    private HashMap<String, RegionUpdate> regionData;
    private RegionInfo regionInfo;

    public String getRegion() {
        return region;
    }

    public HashMap<String, RegionUpdate> getRegionData() {
        return regionData;
    }

    public RegionInfo getRegionInfo() {
        return regionInfo;
    }

    public static class RegionInfo {
        private int _id;
        private float area;
        private float lat;
        @SerializedName("long")
        private float longitud;
        private int population;

        public int get_id() {
            return _id;
        }

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
    }

}


