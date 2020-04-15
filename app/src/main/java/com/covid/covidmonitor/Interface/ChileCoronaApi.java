package com.covid.covidmonitor.Interface;

import com.covid.covidmonitor.Model.CountryUpdate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChileCoronaApi {

    @GET("nation") // annotation
    // método con una llamada de retrofit
    Call<CountryUpdate> getPost();
}
