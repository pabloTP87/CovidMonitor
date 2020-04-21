package com.covid.covidmonitor.Model;

import java.util.Map;

public class DateHistoricalCountry {

    private Map<String,CountryUpdate> data;

    public Map<String, CountryUpdate> getData(){
        return data;
    }
}
