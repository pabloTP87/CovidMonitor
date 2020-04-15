package com.covid.covidmonitor.Model;

import com.google.gson.annotations.SerializedName;

public class CountryUpdate {

    //@SerializedName("confirmed")
    private String confirmed;
    @SerializedName("confirmed_per_100k")
    private Float confirmed_per_100k;
    @SerializedName("day")
    private String day;
    @SerializedName("deaths")
    private String deaths;
    @SerializedName("deaths_per_100k")
    private Float deaths_per_100k;
    @SerializedName("recovered")
    private String recovered;
    @SerializedName("d_per_100k")
    private Float recovered_per_100k;

    public String getConfirmed() {
        return confirmed;
    }

    public Float getConfirmed_per_100k() {
        return confirmed_per_100k;
    }

    public String getDay() {
        return day;
    }

    public String getDeaths() {
        return deaths;
    }

    public Float getDeaths_per_100k() {
        return deaths_per_100k;
    }

    public String getRecovered() {
        return recovered;
    }

    public Float getRecovered_per_100k() {
        return recovered_per_100k;
    }
}
