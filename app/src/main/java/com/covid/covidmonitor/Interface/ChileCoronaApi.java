package com.covid.covidmonitor.Interface;

import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.Model.LastDataRegion;
import com.covid.covidmonitor.Model.Region;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ChileCoronaApi {

    @GET("nation") // annotation
    // método con una llamada de retrofit
    Call<CountryUpdate> getCountryUpdate();

    @GET("nation") // referencia para obtener datos historicos del país
    Call<HashMap<String,CountryUpdate>> getHistoricalCountry();

    @GET("regions") // referencia para obtener el listado de las regiones <'id','nombre region'>
    Call<HashMap<String, Region>> getRegionName();

    @GET("regions") // referencia para obtener los datos actualizados por region
    Call<LastDataRegion> getRegionData(@Query("id") int regionId);
}
