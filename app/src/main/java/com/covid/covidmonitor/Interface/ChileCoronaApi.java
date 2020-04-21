package com.covid.covidmonitor.Interface;

import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.Model.DateHistoricalCountry;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChileCoronaApi {

    @GET("nation") // annotation
    // método con una llamada de retrofit
    Call<CountryUpdate> getCountryUpdate();

    @GET("nation") // referencia para obtener datos historicos del país
    Call<HashMap<String,CountryUpdate>> getHistoricalCountry();
}
