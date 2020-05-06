package com.covid.covidmonitor.Interface;

import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.Model.Region;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ChileCoronaApi {

    @GET("nation") // annotation
    // método con una llamada de retrofit
    Call<CountryUpdate> getCountryUpdate();

    @GET("nation") // referencia para obtener datos historicos del país
    Call<HashMap<String,CountryUpdate>> getHistoricalCountry();

    @GET("regions")
    Call<HashMap<String, Region>> getRegionName();
}
